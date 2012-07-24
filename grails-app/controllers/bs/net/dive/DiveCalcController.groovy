package bs.net.dive

import bs.net.dive.tables.PadiNitrox21
import bs.net.dive.tables.PadiNitrox32
import bs.net.dive.tables.PadiNitrox36

class DiveCalcController {

  def index = {}

  def calcDives = {DiveCommand cmd ->
    def errors = []
    def messages = []
    def warnings = []
    def dives = [:]
    if (cmd.hasErrors()) {
      cmd.errors.each { errors << it }
    } else {
      def table = new PadiNitrox21()
      if (params.table == "Nitrox 32%") {
        println "Using PadiNitrox32"
        table = new PadiNitrox32()
      }else if(params.table == "Nitrox 36%"){
        println "Using PadiNitrox36"
        table = new PadiNitrox36()
      }
      def p = params

      dives[1] = table.computeDiveWithChecks(intVal(p.dive1_depth), intVal(p.dive1_time), null, p.dive1_cold, 1)
      if (dives[1].EG != "ERR") dives[2] = table.computeDiveWithChecks(intVal(p.dive2_depth), intVal(p.dive2_time), intVal(p.dive2_si), p.dive2_cold, 2)
      if (dives[2] && dives[2].EG != "ERR") dives[3] = table.computeDiveWithChecks(intVal(p.dive3_depth), intVal(p.dive3_time), intVal(p.dive3_si), p.dive3_cold, 3)
      if (dives[3] && dives[3].EG != "ERR") dives[4] = table.computeDiveWithChecks(intVal(p.dive4_depth), intVal(p.dive4_time), intVal(p.dive4_si), p.dive4_cold, 4)
      if (dives[4] && dives[4].EG != "ERR") dives[5] = table.computeDiveWithChecks(intVal(p.dive5_depth), intVal(p.dive5_time), intVal(p.dive5_si), p.dive5_cold, 5)

      for (dive in dives) {
        dive.value.MSGS.each { messages << it }
        dive.value.WARN.each { warnings << it }
      }
    }
    render(view: "index", model: [params: params, dives: dives, messages: messages, errors: errors, cmd: cmd,warnings: warnings])
  }

  def intVal(num) {
    if (num) {
      return Integer.valueOf(num)
    } else {
      return null
    }
  }
}

class DiveCommand {
  String dive1_depth
  String dive2_depth
  String dive3_depth
  String dive4_depth
  String dive5_depth
  String dive1_time
  String dive2_time
  String dive3_time
  String dive4_time
  String dive5_time
  String dive2_si
  String dive3_si
  String dive4_si
  String dive5_si
  static constraints = {
    dive1_depth(validator: {val, obj -> return obj.depthValidator(val) })
    dive2_depth(validator: {val, obj -> return obj.depthValidator(val) })
    dive3_depth(validator: {val, obj -> return obj.depthValidator(val) })
    dive4_depth(validator: {val, obj -> return obj.depthValidator(val) })
    dive5_depth(validator: {val, obj -> return obj.depthValidator(val) })
    dive1_time(validator: {val, obj -> return obj.timeValidator(val) })
    dive2_time(validator: {val, obj -> return obj.timeValidator(val) })
    dive3_time(validator: {val, obj -> return obj.timeValidator(val) })
    dive4_time(validator: {val, obj -> return obj.timeValidator(val) })
    dive5_time(validator: {val, obj -> return obj.timeValidator(val) })
    dive2_si(validator: {val, obj -> return obj.siValidator(val) })
    dive3_si(validator: {val, obj -> return obj.siValidator(val) })
    dive4_si(validator: {val, obj -> return obj.siValidator(val) })
    dive5_si(validator: {val, obj -> return obj.siValidator(val) })
  }

  def depthValidator(val) {
    try {
      if (val == '') {
        return true
      }
      def x = Integer.valueOf(val)
      if (x > 140) {
        return "tooLarge"
      } else {
        return true
      }
    } catch (NumberFormatException nfe) {
      return "invalidNumber"
    }
  }

  def timeValidator(val) {
    try {
      if (val == '') {
        return true
      }
      def x = Integer.valueOf(val)
      if (x > 205) {
        return "tooLarge"
      } else {
        return true
      }
    } catch (NumberFormatException nfe) {
      return "invalidNumber"
    }
  }

  def siValidator(val) {
    try {
      if (val == '') {
        return true
      }
      def x = Integer.valueOf(val)
      if (x > 1500) {
        return "tooLarge"
      } else {
        return true
      }
    } catch (NumberFormatException nfe) {
      return "invalidNumber"
    }
  }
}