'use strict'

const chalk = require('chalk')

const Palette = {
  getColor: function (colorname) {
    let obj = chalk
    colorname.split('.').forEach(call => { obj = obj[call] })
    return obj
  }
}

module.exports = Palette
