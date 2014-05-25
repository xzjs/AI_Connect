package com.org.ouc.platform;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class BoardAgent implements RuleInterface, MouseListener, MouseMotionListener
{
	private int turn=-1;
	
	Board board;
	Piece piece;
	Move move;
	
	public BoardAgent()
	{
		turn=-1;
	}
	public BoardAgent(Board board, int turn)	
	{
		this.board = board;
		this.setTurn(turn);
	}
	
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}
	
	public void setTurn(int turn)
	{
		this.turn = turn; 
	}
	public int getTurn()
	{
		return turn;
	}
	
	public abstract void listen();
	public abstract void setStatus(Move move);

}
