/*
package com.org.ouc.ConnectSix;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GaoXing {

    /*
    private final static int WIDTH = 3;
    private final static int REAL_WIDTH = 9;
    private static String midString = "";
    */
/*    private static List<PhaseList> phaseLists = null;


    public List<PhaseList> testSearch(){
        SaxParseService saxParseService = new SaxParseService();
        try {
            phaseLists = saxParseService.getPhaseLists(null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		/*
		for(PhaseList phaseList : phaseLists){
			//System.out.println(phaseList.getPhases().get(0).getPhaseString());
			//System.out.println(phaseList.getIsWin());
			}
		*/

/*        return phaseLists;


    }


    public void testAdd(PhaseList pl) throws IOException{

        phaseLists.add(pl);
        JDomOutput  jDomOutput = new JDomOutput();
        jDomOutput.add(phaseLists);
        System.out.println(phaseLists.get(0).getPhases().get(0).getPhaseString());

    }
    public void Add(List<int[][]> intList,int isWin) throws Exception{

        PhaseList pl = new PhaseList();
        Phase phase = new Phase();
        ArrayList<Phase> phases = new ArrayList<Phase>();
        for(int i=0;i<intList.size();i++){
            String string = new String();
            for(int j=0;j<19;j++){
                for(int k=0;k<19;k++){
                    switch (intList.get(i)[j][k]) {
                        case 1:
                            string += "1";
                            break;
                        case 0:
                            string += "0";
                            break;
                        case -1:
                            string += "2";
                            break;

                        default:
                            string += "0";
                            break;
                    }
                }
            }
            phase.setPhaseString(string);
            phases.add(phase);
        }
        pl.setIsWin(isWin);
        pl.setPhases(phases);
        //System.out.println(phaseLists.get(0).getPhases().get(0).getPhaseString());
        SaxParseService saxParseService = new SaxParseService();
        phaseLists = saxParseService.getPhaseLists(null);
        phaseLists.add(pl);
        JDomOutput  jDomOutput = new JDomOutput();
        jDomOutput.add(phaseLists);

    }
}
	
*/
