'use strict'

/** SECTION: libs */
import net from 'net'

const Palette = require('./palette')
const { Vorpal, Log } = require('./vorpal')
const { LONG_NAME, DEFAULT_DELIMITER, CLIENT_MESSAGE, SERVICE_MESSAGE } = require('./defs')

/** SECTION: defs */
var ADDRESS = {}
var Vars = { commands: [] }

/** SECTION: code */
let server

Vorpal
  .mode('connect <port> [host]')
  .delimiter('connected:')
  .init(function (args, callback) {
    Vorpal._baseExitMode = Vorpal._exitMode
    Vorpal._exitMode = function (args) {
      Vorpal.delimiter(DEFAULT_DELIMITER)

      return Vorpal._cleanlyExitMode(args) // see below for the custom func
    }

    server = net.createConnection(args, () => {
      ADDRESS = server.address()
      Log.log(`connected to server ${ADDRESS.address}:${ADDRESS.port}`)

      server.write(SERVICE_MESSAGE + '/list\n')

      callback()
    })

    server.on('data', (data) => {
      if (data.toString().match(/^\*(?:.*)\*(?:.*)\*(?:.*)$/)) {
        let arr = /^\*(.*)\*(.*)\*(.*)$/.exec(data.toString())
        let colorname = arr[1]
        let messagetype = arr[2]
        let message = arr[3]

        let output = message
        if (colorname !== 'plain') {
          output = Palette.getColor(colorname)(message)
        }

        if (messagetype.length > 0) {
          let display = true

          switch (messagetype) {
            case 'authenticate':
              display = false
              Vars._authenticatingDelimiter = output + '$ '
              Vorpal.ui.delimiter(Vars._authenticatingDelimiter)
              Vars.authenticating = true
              break
            case 'username':
              let arr = /Username set to: (.*)$/.exec(data.toString())
              Vars.username = arr[1]
              Vorpal.delimiter(arr[1] + '@' + ADDRESS.address)
              Vorpal.ui.delimiter(arr[1] + '@' + ADDRESS.address + ' connected: ')
              break
            case 'serviceCommands':
              display = false
              message = message.replace(/^\[|\]$/g, '')
              Vars.commands = message.split(/\s*,\s*/)
              break
            case 'disconnect':
              Vorpal.execSync('exit')
              break
          }

          if (display) {
            Log.info(output)
          }
        } else {
          Log.log(output)
        }
      } else {
        Log.log(data.toString())
      }
    })

    server.on('end', () => {
      Log.info('server disconnected')
    })

    server.on('error', args => {
      let err = args.err
      Log.error(err)
    })
  })
  .action(function (command, callback) {
    if (Vars.authenticating) {
      if (command.match(/^\s*$/) || command.match(/^\*/) || Vars.commands.indexOf(command) > -1) {
        Vorpal.ui.delimiter(Vars._authenticatingDelimiter)
      } else {
        server.write(CLIENT_MESSAGE + command + '\n')
        Vars.authenticating = false
      }
    } else {
      server.write(CLIENT_MESSAGE + command + '\n')
    }
    callback()
  })

export default Vorpal
