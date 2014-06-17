package com.org.ouc.ConnectSix;

import java.util.ArrayList;

public class PhaseList {

    private ArrayList<Phase> phases = new ArrayList<Phase>();
    private int isWin = 0;
    public ArrayList<Phase> getPhases() {
        return phases;
    }
    public void setPhases(ArrayList<Phase> phases) {
        this.phases = phases;
    }
    public int getIsWin() {
        return isWin;
    }
    public void setIsWin(int isWin) {
        this.isWin = isWin;
    }
    public void addPhase(Phase phase){
        phases.add(phase);
    }

}
