'use strict'

const { LONG_NAME, DEFAULT_DELIMITER } = require('./defs')

import vorpal from 'vorpal'
const Vorpal = vorpal()

import vorpalLogger from 'vorpal-log'

Vorpal.use(vorpalLogger)
  .delimiter(DEFAULT_DELIMITER)

const Log = Vorpal.logger

const exit = Vorpal.find('exit')
if (exit) {
  exit.description('Exits ' + LONG_NAME)
}

Vorpal._cleanlyExitMode = function (args) {
  let ret = Vorpal._baseExitMode(args)

  Vorpal._exitMode = Vorpal._baseExitMode
  delete Vorpal._baseExitMode

  return ret
}

module.exports = { Vorpal, Log }
