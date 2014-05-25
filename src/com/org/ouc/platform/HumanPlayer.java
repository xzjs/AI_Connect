package com.org.ouc.platform;


public class HumanPlayer extends Player{

	public void setBoardAgent(BoardAgent bAgent)
	{
		super.setBoardAgent(bAgent);
		bAgent.listen();
	}
	public HumanPlayer(){
		
	}
	
	public HumanPlayer(int id){
		this.id = id;
	}
	
	public boolean think() {
		
		return false;
	}

	@Override
	public Move next(int[][] now) {
		Move move = this.getBoardAgent().getMove();
		this.getBoardAgent().setMove(null);
		return move;
	}

	
}