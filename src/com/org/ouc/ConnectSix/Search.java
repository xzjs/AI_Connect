package com.org.ouc.ConnectSix;

import java.util.ArrayList;
import java.util.List;

public class Search {

    public Search() {
        super();
        GaoXing t = new GaoXing();
        phaseLists = t.testSearch();

    }

    private List<PhaseList> phaseLists = new ArrayList<PhaseList>();
    private List<PhaseList> answer = new ArrayList<PhaseList>();
    private int[][] next = null;

    //public List<PhaseList> search(Phase phase, int isWin, int num) {
    public int[][] search(Phase phase, int isWin, int num) {

        for (PhaseList phaseList : phaseLists) {

            if (phaseList.getPhases().size() > num) {
                //System.out.println(phaseList.getIsWin() == isWin);
                //System.out.println(phaseList.getPhases().get(num - 1).getPhaseString() == "111111111");
                System.out.println((phaseList.getIsWin() == isWin));
                System.out.println((phaseList.getPhases().get(num - 1).getPhaseString()).equals(phase.getPhaseString()));
                System.out.println(phaseList.getPhases().get(num - 1).getPhaseString());
                if ((phaseList.getIsWin() == isWin)
                        && ((phaseList.getPhases().get(num - 1).getPhaseString()).equals(phase.getPhaseString()))) {

                    String string = phaseList.getPhases().get(num).getPhaseString();
                    System.out.println(string);
                    next = new int[19][19];
                    int index = 0;
                    char[] ch = string.toCharArray();
                    for(int i=0;i<19;i++){
                        for(int j=0;j<19;j++){
                            switch (ch[index]) {
                                case '0':
                                    next[i][j] = 0;
                                    break;
                                case '1':
                                    next[i][j] = 1;
                                    break;

                                default:
                                    next[i][j] = -1;
                                    break;
                            }
                            //System.out.print(next[i][j]);
                            index++;
                        }
                    }
                    System.out.println(string);
                    answer.add(phaseList);
                    System.out.println("find");
                    return next;

                }
            }

        }

        //return answer;
        return null;

    }
}
