package com.org.ouc.TwoBeatOne.prediction;

public class ChessCoordinate {

	private int xCdt = 0, yCdt = 0;
	
	//¹¹Ôìº¯Êý
	public ChessCoordinate(int x, int y) {
		this.xCdt = x;
		this.yCdt = y;
	}
	public void setChess(int x, int y) {
		xCdt = x;
		yCdt = y;
	}
	public int getChessX() {
		return xCdt;
	}
	public int getChessY() {
		return yCdt;
	}
}
