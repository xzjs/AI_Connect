package com.org.ouc.ConnectSix;


import com.org.ouc.platform.Move;
import com.org.ouc.platform.Player;

public abstract class ConnectSixPlayer extends Player{
	
	public void setBoardAgent(ConnectSixBoardAgent bAgent) 
	{
		super.setBoardAgent(bAgent);
	}
	
	public ConnectSixBoardAgent getBoardAgent()
	{
		return (ConnectSixBoardAgent) super.getBoardAgent();
	}
	public abstract Move next( int[][] now );
	
}
