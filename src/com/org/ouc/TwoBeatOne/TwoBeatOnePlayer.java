package com.org.ouc.TwoBeatOne;


import com.org.ouc.platform.Move;
import com.org.ouc.platform.Player;

public abstract class TwoBeatOnePlayer extends Player{
	
	public void setBoardAgent(TwoBeatOneBoardAgent bAgent) 
	{
		super.setBoardAgent(bAgent);
	}
	
	public TwoBeatOneBoardAgent getBoardAgent()
	{
		return (TwoBeatOneBoardAgent) super.getBoardAgent();
	}
	
	public abstract Move next(int[][] now);
	
    

}
