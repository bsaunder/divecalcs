package bs.net.dive.tables
/**
 * Created by IntelliJ IDEA.
 * User: Bryan
 * Date: Jun 13, 2009
 * Time: 5:10:26 PM
 * To change this template use File | Settings | File Templates.
 */

public class PadiNitrox36  extends PadiDiveTable {

    def setTable1(){
        println "Setting Table 1"
       def i = 0
        limitsTable[50] = [:]
        [10,20,26,30,33,37,41,46,50,55,60,65,71,77,83,90,97,106,115,125,137,150,166,186,211,220].each{ limitsTable[50].put(it,getChar(i++)) }

        i = 0
        limitsTable[55] = [:]
        [9,17,23,26,29,32,36,39,43,47,51,55,60,64,69,75,80,86,93,100,107,115,125,135,146,155].each{ limitsTable[55].put(it,getChar(i++)) }

        i = 0
        limitsTable[60] = [:]
        [8,15,20,23,26,29,32,35,38,41,45,48,52,56,60,64,68,73,78,83,89,95,101,108,115].each{ limitsTable[60].put(it,getChar(i++)) }

        i = 0
        limitsTable[65] = [:]
        [7,14,18,21,23,26,28,31,34,37,40,43,4649,52,5660.64,6872,76,81,86,90].each{ limitsTable[65].put(it,getChar(i++)) }

        i = 0
        limitsTable[70] = [:]
        [7,13,17,19,21,23,26,28,31,33,36,38,41,44,47,50,53,56,60,63,67,71,75].each{ limitsTable[70].put(it,getChar(i++)) }

        i = 0
        limitsTable[80] = [:]
        [6,11,14,16,18,20,22,24,26,28,30,32,34,36,39,41,43,4648,51,53,55].each{ limitsTable[80].put(it,getChar(i++)) }

        i = 0
        limitsTable[90] = [:]
        [5,9,12,14,15,17,19,20,22,24,25,2729,31,33,35,37,38,39,40].each{ limitsTable[90].put(it,getChar(i++)) }

        i = 0
        limitsTable[100] = [:]
        [4,8,11,12,14,15,16,18,19,21,22,24,25,27,29,30,32,34,35].each{ limitsTable[100].put(it,getChar(i++)) }

        i = 0
        limitsTable[110] = [:]
        [4,7,10,11,12,13,15,16,17,18,20,21,22,24,25,27,28,29].each{ limitsTable[110].put(it,getChar(i++)) }

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
            50:0.91,
            55:0.96,
            60:1.01,
            65:1.07,
            70:1.12,
            80:1.23,
            90:1.34,
            100:1.45,
            110:1.56]
    }

    def getOxygenPP(int diveDepth){
    	def pp = oxygenPPTable[diveDepth]
    	if(pp)
    		return pp
    	else
    		return -1
    }

    def PadiNitrox36(){
        setTable1()
        setTable2()
        setTable3()
        setOxygenPP()
        setDecoTable()
        maxDepth = 90
    	maxActualDepth = 110
    }

  public setDecoTable() {
        decoTable[50] = 166
        decoTable[55] = 125
        decoTable[60] = 95
        decoTable[65] = 76
        decoTable[70] = 63
        decoTable[80] = 48
        decoTable[90] = 37
        decoTable[100] = 0
        decoTable[110] = 0
        decoTable[120] = 0
        decoTable[130] = 0
        decoTable[140] = 0
  }
}