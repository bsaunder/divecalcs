package bs.net.dive.tables

import bs.net.dive.tables.PadiDiveTable

final class PadiNitrox32 extends PadiDiveTable {

    def setTable1(){
        println "Setting Table 1"
       def i = 0
        limitsTable[45] = [:]
        [10,20,26,30,34,37,41,46,50,55,60,65,71,11,83,90,98,106,115,126,138,151,167,187,213,220].each{ limitsTable[45].put(it,getChar(i++)) }
                       
        i = 0
        limitsTable[50] = [:]
        [9,17,23,26,29,32,36,39,43,47,51,55,59,64,69,74,80,85,92,99,106,114,123,133,145,155].each{ limitsTable[50].put(it,getChar(i++)) }
            
        i = 0
        limitsTable[55] = [:]
        [8,15,20,23,26,28,31,34,37,41,44,47,51,55,59,63,67,72,77,82,87,93,99,106,110].each{ limitsTable[55].put(it,getChar(i++)) }
        
        i = 0
        limitsTable[60] = [:]
        [7,14,18,20,23,25,28,30,33,36,39,42,45,48,51,55,58,62,70,74,79,84,90].each{ limitsTable[60].put(it,getChar(i++)) }
        
        i = 0
        limitsTable[70] = [:]
        [6,11,15,17,19,21,23,25,27,29,32,34,36,39,41,44,46,49,52,55,58,60].each{ limitsTable[70].put(it,getChar(i++)) }
            
        i = 0
        limitsTable[80] = [:]
        [5,10,13,14,16,18,19,21,23,25,27,28,30,32,34,36,38,41,43,45].each{ limitsTable[80].put(it,getChar(i++)) }
            
        i = 0
        limitsTable[90] = [:]
        [5,8,11,13,14,15,17,18,20,21,23,24,26,28,29,31,33,34,35].each{ limitsTable[90].put(it,getChar(i++)) }
            
        i = 0
        limitsTable[100] = [:]
        [4,7,10,11,12,14,15,16,17,19,20,22,23,24,26,27,29,30].each{ limitsTable[100].put(it,getChar(i++)) }
            
        i = 0
        limitsTable[110] = [:]
        [4,7,9,10,11,12,13,14,16,17,18,19,20,22,23,24,25].each{ limitsTable[110].put(it,getChar(i++)) }
            
        i = 0
        limitsTable[120] = [:]
        [3,6,8,9,10,11,12,13,14,15,16,17,18,19,20].each{ limitsTable[120].put(it,getChar(i++)) }
            
        i = 0
        limitsTable[130] = [:]
        [3,6,7,8,9,10,11,12,13,14,15,16,17,18].each{ limitsTable[130].put(it,getChar(i++)) }
        println "Dive Limits Set"
    }
    
    def setTable2(){
        println "Setting Table 2"
        def surface = [3*60,47,21,8,7,7,6,5,5,5,4,4,4,3,3,3,3,3,3,2,2,2,2,2,2,2]
        char a = 'A'
        for(start in (0..25)){
            def accum = 0
            def end = start
            while(end >= 0){
                accum += surface[end]
                if(!intervalTable[getChar(start)]){
                    intervalTable[getChar(start)] = [(accum + start - end):getChar(end)]
                }else{
                    intervalTable[getChar(start)].put(accum + start - end,getChar(end))
                }
                end--
            }
        }
        
        // Table 2 Corrections
        intervalTable['E'].remove(86)
        intervalTable['E'].put(87,'B')
        intervalTable['E'].remove(267)
        intervalTable['E'].put(268,'A')
        intervalTable['O'].remove(142)
        intervalTable['O'].put(143,'B')
        intervalTable['P'].remove(146)
        intervalTable['P'].put(147,'B')
        println "Intervals Set"
    }
    
    def setTable3(){
        println "Setting Table 3"
        residualTable = limitsTable
    }
    
    def setOxygenPP(){
        oxygenPPTable = [
            45:0.76,
            50:0.8,
            55:0.85,
            60:0.9,
            70:1,
            80:1.1,
            90:1.19,
            100:1.29,
            110:1.39,
            120:1.48,
            130:1.58]
    }
    
    def getOxygenPP(int diveDepth){
    	def pp = oxygenPPTable[diveDepth]
    	if(pp)
    		return pp
    	else
    		return -1 
    }
    
    def PadiNitrox32(){
        setTable1()
        setTable2()
        setTable3()
        setOxygenPP()
        setDecoTable()
        maxDepth = 110
    	maxActualDepth = 130
    }

  public setDecoTable() {
        decoTable[45] = 167
        decoTable[50] = 123
        decoTable[55] = 93
        decoTable[60] = 74
        decoTable[70] = 52
        decoTable[80] = 38
        decoTable[90] =31
        decoTable[100] = 0
        decoTable[110] = 0
        decoTable[120] = 0
        decoTable[130] = 0
        decoTable[140] = 0
  }
}