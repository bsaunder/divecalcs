package bs.net.dive.tables
abstract class PadiDiveTable {

  def currentGroup = ''
  def previousGroup = ''
  def currentSI = 0

  def limitsTable = [:]
  def intervalTable = [:]
  def residualTable = [:]
  def decoTable = [:]
  def oxygenPPTable = [:]
  def maxDepth = 0
  def maxActualDepth = 0

  def charMap = [0:'A',
      1:'B',
      2:'C',
      3:'D',
      4:'E',
      5:'F',
      6:'G',
      7:'H',
      8:'I',
      9:'J',
      10:'K',
      11:'L',
      12:'M',
      13:'N',
      14:'O',
      15:'P',
      16:'Q',
      17:'R',
      18:'S',
      19:'T',
      20:'U',
      21:'V',
      22:'W',
      23:'X',
      24:'Y',
      25:'Z']

  abstract setTable1()

  abstract setTable2()

  abstract setTable3()

  abstract setDecoTable()
  
  abstract setOxygenPP()
  
  abstract getOxygenPP(diveDepth)

  def reset() {
    currentGroup = ''
    previousGroup = ''
    currentSI = 0
  }

  def dive(int diveDepth, int diveTime, group) {
    def tableDepth
    def tableTime
    def rnt = 0
    // Is this a Repetitive Dive?
    if (group != '') {
      rnt = getRNT(diveDepth, group)
    }
    def abt = diveTime + rnt
    def letterGroup = 'XX'
    for (depth in limitsTable.keySet()) {
      if (diveDepth <= depth) {
        tableDepth = depth
        for (time in limitsTable[tableDepth].keySet()) {
          if (abt <= time) {
            tableTime = time
            letterGroup = limitsTable[tableDepth].get(time)
            break
          }
        }
        break
      }
    }
    previousGroup = currentGroup
    currentGroup = letterGroup
    return currentGroup
  }

  def dive(int diveDepth, int diveTime) {
    return dive(diveDepth, diveTime, currentGroup)
  }

  def addSurfaceInterval(siTime) {
    currentSI += siTime
    return currentSI
  }

  def updateGroupAfterSurfaceInterval() {
    if (currentSI == 0 && currentGroup == '') {
      return ''
    }
    for (time in intervalTable[currentGroup].keySet()) {
      if (currentSI <= time) {
        previousGroup = currentGroup
        currentGroup = intervalTable[currentGroup].get(time)
        break
      }
    }
    currentSI = 0
    return currentGroup
  }

  def addSurfaceIntervalAndUpdateGroup(int siTime) {
    currentSI += siTime
    return updateGroupAfterSurfaceInterval()
  }

  def getRNT(diveDepth) {
    return getRNT(diveDepth, currentGroup)
  }

  def getRNT(diveDepth, group) {
    def tableDepth
    def rnt = -1
    for (depth in residualTable.keySet()) {
      if (diveDepth <= depth) {
        tableDepth = depth
        for (cell in residualTable[tableDepth]) {
          if (cell.getValue() == group) {
            rnt = cell.getKey()
          }
        }
        break
      }
    }
    return rnt
  }

  def getMaxTableDepth(group) {
    def tableDepth = -1
    for (depth in limitsTable.keySet()) {
      if (limitsTable[depth].containsValue(group)) {
        tableDepth = depth
      } else {
        break
      }
    }
    return tableDepth
  }

  // Says X Diver to 70ft has Max 40m DT - Dive not Possible
  def getMaxTableTime(tableDepth, group, useRNT) {
    def tableTime = -1
    def rnt = 0
    if (useRNT == true) {
      rnt = getRNT(tableDepth, group)
    }
    if (rnt == -1) {
      return tableTime
    }
    for (time in (0..225)) {
      def abt = time + rnt
      for (row in limitsTable[tableDepth]) {
        if (abt <= row.getKey()) {
          tableTime = time
        }
      }
    }
    return tableTime
  }

  def getMaxTableTime(tableDepth, useRNT) {
    return getMaxTableTime(tableDepth, currentGroup, useRNT)
  }

  def isDecoStopRequired(depth, time) {
    def decoReq = true
    def tableDepth = getTableDepth(depth)
    def decoLimit = decoTable[tableDepth]
    if (time < decoLimit) {
      decoReq = false
    }
    return decoReq
  }

  def getTableDepth(diveDepth) {
    def tableDepth = -1
    println "LimitsTable ${limitsTable}"
    for (depth in limitsTable.keySet()) {
      println "GTD: Depth ${depth} DiveDepth ${diveDepth}"
      if (diveDepth <= depth) {
        tableDepth = depth
        break
      }
    }
    return tableDepth
  }

  // Computes Max SI Needed for Safety
  def getSINeededForDive(diveDepth, diveTime) {
    def newLetter = ''
    def siNeeded = -1
    def tableDepth = getTableDepth(diveDepth)
    for (group in (0..getIntFromChar(currentGroup))) {
      def maxTableTime = getMaxTableTime(tableDepth, getChar(group), true)
      println "MTT: ${maxTableTime}"
      if (diveTime < maxTableTime) {
        newLetter = getChar(group)
      }
    }
    println "NL: ${newLetter}"
    if (newLetter != '') {
      for (row in intervalTable[currentGroup]) {
        if (row.getValue() == newLetter) {
          siNeeded = row.getKey()
          break
        }
      }
    }
    return siNeeded
  }

  def computeDive(Integer depth, Integer time, Integer si) {
    println "Computing Dive..."
    def sg = addSurfaceIntervalAndUpdateGroup(si)
    def rnt = getRNT(depth)
    if (rnt == -1) rnt = 0
    def tbt = time + rnt
    def eg = dive(depth, time)
    def tableDepth = getTableDepth(depth)
    def deco = isDecoStopRequired(tableDepth, tbt)

    return ['SI': si,
            'SG': sg,
            'DP': depth,
            'ABT': time,
            'RNT': rnt,
            'TBT': tbt,
            'EG': eg,
            'DECO': deco]
  }

  def computeDiveWithChecks(Integer depth, Integer time, Integer si, diveCold, Integer diveNumber) {
    def messages = []
    def warnings = []
    def dives = [:]
    
    // Coldwater Rule
    if(diveCold){
      def d = depth
      depth = depth + 10
      messages << "Dive ${diveNumber} Depth was changed from ${d} to ${depth} for Cold Water."
    }
    
    // Max Depth Checks
    if(depth){
      if(depth > maxActualDepth){
        depth = maxActualDepth
        messages << "Dive ${diveNumber} Depth exceeded Max Depth of ${maxActualDepth}, Adjusted to ${maxActualDepth}"
      }
      if(depth > maxDepth){
        warnings << "Dive ${diveNumber} Depth exceeds Max Diveable Depth of ${maxDepth}"
      }
    }
    
    // Table Depth
    if(depth){
      def diveDepth = depth
      depth = getTableDepth(depth)
      if(depth != diveDepth){
      	messages << "Dive ${diveNumber} Depth Rounded Up to ${depth} from ${diveDepth}"
      }
    }
    
    // WXYZ Rule
    switch(currentGroup){
      case 'W':
      case 'X':
        if(si < 60){
          si = 60
          messages << "Dive ${diveNumber} SI adjusted to 60 mins for WXYZ Rule."
        }
        break;
      case 'Y':
      case 'Z':
      if(si < 180){
          si = 180
          messages << "Dive ${diveNumber} SI adjusted to 180 mins for WXYZ Rule."
        }
        break;
    }
    
    // Parameter Check
    // Have Time, Depth, SI
    if(depth && si && time){
      dives = computeDive(depth, time, si)
    // Have Time, Depth
    }else if (depth && !si && time) {
      if(diveNumber == 1){
        println "Computing Dive 1 w/ Time(${time}) and Depth(${depth})"
        dives = computeDive(depth, time, 0)
      }else{
        si = getSINeededForDive(depth, time)
        messages << "The Maximum SI for Dive ${diveNumber} was computed as ${si} min."
        dives = computeDive(depth, time, si)
      }
    // Have Depth, SI
    }else if(depth && si && !time){
      def oldGroup = currentGroup
      def newGroup = addSurfaceIntervalAndUpdateGroup(si)
      time = getMaxTableTime(getTableDepth(depth),newGroup,true)
      messages << "The Maximum ABT for Dive ${diveNumber} was computed as ${time} min."
      currentGroup = oldGroup
      dives = computeDive(depth, time, si)
    // Have SI
    }else if(!depth && si && !time){
      dives.SI = si
      dives.SG = addSurfaceIntervalAndUpdateGroup(si)
      messages << "Dive ${diveNumber} has no Depth/Time, Only Computing S. Group"
    }else if(depth && !si && !time){
      messages << "Dive ${diveNumber} requires a SI, and Time."
    }else if(!depth && !si && time){
      messages << "Dive ${diveNumber} requires a SI, and Depth."
    }
    
    // Check Dive for Errors, Generate Messates
    if (dives.EG == 'XX') {
        dives.EG = 'ERR'
        dives.DECO = false
        println "PG: ${previousGroup}"
        def abt
        if(diveNumber == 1){
          abt = getMaxTableTime(getTableDepth(depth),previousGroup,false)
        }else{
          abt = getMaxTableTime(getTableDepth(depth),previousGroup,true)
        }
        def m = ""
        if(abt == -1){
          m = "Dive Not Possible"
        }else{
          m = "Max ABT is ${abt}"
        }
        messages << "Dive ${diveNumber} exceeds No Deco Limits, ${m}."
    }
      
    // Is Deco Dive?  
    if(dives.DECO){
      warnings << "Dive ${diveNumber} Requires a Safety Stop."
    }
    
    // Return
    dives.MSGS = messages
    dives.WARN = warnings

    return dives
  }

  protected getChar(x) {
      return charMap[x]
  }

  protected getIntFromChar(ch){
      for(c in charMap){
        if(c.getValue() == ch){
          return c.getKey()
        }
      }
      return -1
  }
}