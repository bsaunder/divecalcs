package bs.net.dive.tables

import bs.net.dive.tables.PadiDiveTable

final class PadiNitrox21 extends PadiDiveTable {

    def setTable1(){
       def i = 0
        limitsTable[35] = [:]
        [10,19,25,29,32,36,40,44,48,52,57,62,67,73,79,85,92,100,108,117,127,139,152,168,188,205].each{ limitsTable[35].put(it,getChar(i++)) }

        i = 0
        limitsTable[40] = [:]
        [9,16,22,25,27,31,34,37,40,44,48,51,55,60,64,69,74,79,85,91,97,104,111,120,129,140].each{ limitsTable[40].put(it,getChar(i++)) }

        i = 0
        limitsTable[50] = [:]
        [7,13,17,19,21,24,26,28,31,33,36,38,41,44,47,50,53,57,60,63,67,71,75,80].each{ limitsTable[50].put(it,getChar(i++)) }

        i = 0
        limitsTable[60] = [:]
        [6,11,14,16,17,19,21,23,25,27,29,31,33,35,37,39,42,44,47,49,52,54,55].each{ limitsTable[60].put(it,getChar(i++)) }

        i = 0
        limitsTable[70] = [:]
        [5,9,12,13,15,16,18,19,21,22,24,26,27,29,31,33,35,36,38,40].each{ limitsTable[70].put(it,getChar(i++)) }

        i = 0
        limitsTable[80] = [:]
        [4,8,10,11,13,14,15,17,18,19,21,22,23,25,26,28,29,30].each{ limitsTable[80].put(it,getChar(i++)) }

        i = 0
        limitsTable[90] = [:]
        [4,7,9,10,11,12,13,15,16,17,18,19,21,22,23,24,25].each{ limitsTable[90].put(it,getChar(i++)) }

        i = 0
        limitsTable[100] = [:]
        [3,6,8,9,10,11,12,13,14,15,16,17,18,19,20].each{ limitsTable[100].put(it,getChar(i++)) }

        i = 0
        limitsTable[110] = [:]
        [3,6,7,8,9,10,11,12,13,13,14,15,16].each{ limitsTable[110].put(it,getChar(i++)) }

        i = 0
        limitsTable[120] = [:]
        [3,5,6,7,8,9,10,11,11,12,13].each{ limitsTable[120].put(it,getChar(i++)) }

        i = 0
        limitsTable[130] = [:]
        [3,5,6,7,7,8,9,10].each{ limitsTable[130].put(it,getChar(i++)) }

        i = 0
        limitsTable[140] = [:]
        [0,4,5,6,7,8].each{ limitsTable[140].put(it,getChar(i++)) }
    }

    def setTable2(){
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
    }

    def setTable3(){
       residualTable = limitsTable
    }

    def setDecoTable(){
        decoTable[35] = 152
        decoTable[40] = 111
        decoTable[50] = 67
        decoTable[60] = 49
        decoTable[70] = 35
        decoTable[80] = 26
        decoTable[90] = 22
        decoTable[100] = 0
        decoTable[110] = 0
        decoTable[120] = 0
        decoTable[130] = 0
        decoTable[140] = 0
    }

    def PadiNitrox21(){
        setTable1()
        setTable2()
        setTable3()
        setDecoTable()
        maxDepth = 140
    	maxActualDepth = 140
    }
}