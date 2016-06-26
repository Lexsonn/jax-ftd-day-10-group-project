'use strict'

const chalk = require('chalk')

const Palette = {
  getColor: function (colorname) {
    let obj = chalk
    let call = colorname
    colorname.split('.').forEach(a => { obj = obj[a] })
    return obj
  }
}

module.exports = Palette
