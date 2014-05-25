package com.org.ouc.platform;

import com.org.ouc.ConnectSix.ConnectSixPlayer;


public abstract class Player {	
	int turn;
	int id;
	BoardAgent bAgent;
	protected int role = 0;			//棋手角色
	private int steps = 0; // 步数
	
	//是否是第一步棋
	private boolean firstStep;
	private Player opponent;
	private Move lastMove;
	public  int PIECE_ID;
	
	public Player(){
		lastMove = new Move();
	}
	public Move getLastMove() {
		return lastMove;
	}
	public Move getOpponentLastMove() {
		
		Move oppMove = new Move();
		oppMove.setStartPoint( Board.XLen - 1 - opponent.getLastMove().getStartPoint().getX(), Board.YLen - 1 - opponent.getLastMove().getStartPoint().getY() );
		oppMove.setEndPoint(Board.XLen - 1 - opponent.getLastMove().getEndPoint().getX(), Board.YLen - 1 - opponent.getLastMove().getEndPoint().getY() );
		return oppMove;
	}
	public void setLastMove(Move lastMove ) {
		this.lastMove.setStartPoint( lastMove.startPoint );
		this.lastMove.setEndPoint( lastMove.endPoint );
	}
	public int getOpponentPieceID(){
		return opponent.PIECE_ID;
	}
	
	public void setOpponent( Player opponent) {
		this.opponent = opponent;
	}
	
	public void setFirstStep(){
		firstStep = true;
	}
	public boolean isFirstStep(){
		return firstStep;
	}
	public void clearFirstStep(){
		firstStep = false;
	}
	public int getTurn() {
		return turn;
	}
	public void setTurn(int turn) {
		this.turn = turn;
	}
	
	public int getSteps() {
		return steps;
	}
	public void setSteps(int steps) {
		this.steps = steps;
	}
	
	public int getRole() {
		return role;
	}
	
	public void setRole(int role) {
		this.role = role;
	}
	
	public void setBoardAgent(BoardAgent bAgent)
	{
		this.bAgent	= bAgent;
	}
	public BoardAgent getBoardAgent()
	{
		return this.bAgent;
	}
	
	public void releaseBoardAgent()
	{
		bAgent = null;
	}
	
	abstract public Move next(int[][] now);
	
}

