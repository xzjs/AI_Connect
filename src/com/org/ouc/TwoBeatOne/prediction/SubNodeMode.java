package com.org.ouc.TwoBeatOne.prediction;

import java.util.*;

import com.org.ouc.TwoBeatOne.TwoBeatOneBoard;


public class SubNodeMode {	//定义一个分支节点的子节点棋局
	
	private int playerFlag = 1;	//当前走步玩家，1：我方、-1：敌方
	private int[][] currentModeArr = null;	//当前的棋局矩阵
	private int playerChessNum = 0;	//当前走步玩家可移动的棋子数
	private boolean[] hasCheckedChess = null;	//检测要移动的棋子标志：true：已检测完毕，false：未检测完毕
	private boolean[][] chessMoveDir = null;	//每个可移动棋子的四个移动方向标志：true：已检测，false：未检测
	private ChessCoordinate[] playerChess = null;	//当前可移动棋子的坐标
	private int[][] predictModeArr = null;	//下一步一种预测的棋局矩阵
	private ChessCoordinate[] newMoveChess = new ChessCoordinate[2];	//某个预测棋局对应移动的棋子新旧坐标
	private int boardX = 5, boardY = 5;	//棋盘规格，默认5*5
	
	//构造函数
	public SubNodeMode(int flag, int[][] currentMode) {
		this.playerFlag = flag;
		this.playerChessNum = 0;
		
		boardX = TwoBeatOneBoard.XLen;
		boardY = TwoBeatOneBoard.YLen;
		
		currentModeArr = new int[boardX][boardY];
		hasCheckedChess = new boolean[boardY];
		chessMoveDir = new boolean[boardY][4];
		playerChess = new ChessCoordinate[boardY];
		predictModeArr = new int[boardX][boardY];
		
		for(int i = 0; i < boardX; i++) {
			System.arraycopy(currentMode[i], 0, currentModeArr[i], 0, boardY);
			System.arraycopy(currentModeArr[i], 0, predictModeArr[i], 0, boardY);
		}
		
		for(int i = 0; i < boardY; i++) {
			hasCheckedChess[i] = false;
			Arrays.fill(chessMoveDir[i], false);
			playerChess[i] = new ChessCoordinate(-1, -1);
		}
		
		checkMoveChess();
		for(int i = playerChessNum; i < boardY; i++) {
			hasCheckedChess[i] = true;
			Arrays.fill(chessMoveDir[i], true);
		}
		
		newMoveChess[0] = new ChessCoordinate(-1, -1);
		newMoveChess[1] = new ChessCoordinate(-1, -1);
	}
	
	private void checkMoveChess() {	//检测可移动的棋子
		for(int i = 0; i < boardX; i++) {
			for(int j = 0; j < boardY; j++) {
				if(currentModeArr[i][j] == playerFlag) {
					if((i > 1 && currentModeArr[i-1][j] == 0) ||
							(j > 1 && currentModeArr[i][j-1] == 0) ||
							(i < boardX-1 && currentModeArr[i+1][j] == 0) ||
							(j < boardY-1 && currentModeArr[i][j+1] == 0)) {
						
						playerChess[playerChessNum].setChess(i, j);
						
						if(!checkDirection(i-1, j))
							chessMoveDir[playerChessNum][0] = true;
						if(!checkDirection(i, j+1))
							chessMoveDir[playerChessNum][1] = true;
						if(!checkDirection(i+1, j))
							chessMoveDir[playerChessNum][2] = true;
						if(!checkDirection(i, j-1))
							chessMoveDir[playerChessNum][3] = true;
						
						playerChessNum++;
					}
				}
			}
		}
	}
	
	private boolean checkDirection(int x, int y) {	//检查该方向是否可移动
		if(x >= 0 && x < boardX && y >= 0 && y < boardY && currentModeArr[x][y] == 0)
			return true;
		else
			return false;
	}
	
	public int getPlayerChessNum() {	//获得当前走步玩家可移动的棋子数（即当前节点的子节点数目）
		return this.playerChessNum;
	}
	
	public int[][] getPredictMode() {	//获得所生成的预测棋局
		int[][] predictMode = setStatus(predictModeArr, true);
		return predictMode;
	}
	
    public int[][] setStatus(int s[][], boolean flag){
		
		int xLen = s.length;
		int yLen = s[0].length;
		
		
		int [][]status = new int[xLen][yLen];
		if(flag){
			for(int x=0;x<xLen;x++)
				for(int y=0;y<yLen;y++)
					status[x][y] = s[x][y];
		}else{
			for(int x=xLen;x>0;x--)
				for(int y=yLen;y>0;y--)
					status[xLen - x][yLen - y] = s[x-1][y-1];
		}
		return status;
	}
	
	public ChessCoordinate[] getNewMoveChess() {	//获得某预测棋局对应移动的棋子新旧坐标
		return this.newMoveChess;
	}
	
	public boolean hasNotCheckedChess() {	//检查是否还有未检测过的棋子
		for(int i = 0; i < boardY; i++)
			if(hasCheckedChess[i] == false)
				return true;
		return false;
	}
	
	public void makeRandomPredictMode() {	//随机生成预测棋局（随机棋子随机方向）
		for(int i = 0; i < boardX; i++) {
			System.arraycopy(currentModeArr[i], 0, predictModeArr[i], 0, boardY);
		}
		newMoveChess[0].setChess(-1, -1);
		newMoveChess[1].setChess(-1, -1);
		int kMoveChess = -1;
		
		while(kMoveChess == -1) {
			kMoveChess = selectMoveChess();
		}
		selectMoveDir(kMoveChess);
	}
	
	public void checkBeatChess() {	//检查随机生成的预测棋局中吃子的情况
		for(int i = 0; i < boardX; i++) {
			for(int j = 0; j < boardY; j++) {
				if(predictModeArr[i][j] == playerFlag) {
					checkLSquareTop(i, j);
					checkLSquareRight(i ,j);
					checkLSquareBottom(i, j);
					checkLSquareLeft(i, j);
				}
			}
		}
	}
	
	private int selectMoveChess() {	//随机选择要移动的棋子
		Random rdm = new Random();
		int kChess = Math.abs(rdm.nextInt()) % playerChessNum;
		
		if(hasCheckedChess[kChess])
			return -1;
		
		return kChess;
	}
	
	private void selectMoveDir(int kMoveChess) {	//随机选择移动棋子的方向
		int newX = -1, newY = -1;
		Random rdm = new Random();
		int dir = Math.abs(rdm.nextInt())%4;	//随机产生移动方向：0.上，1.右，2.下，3.左
		
		while(true) {
			if(chessMoveDir[kMoveChess][dir] == false) {
				switch(dir) {
					case 0: 	newX = playerChess[kMoveChess].getChessX()-1;
								newY = playerChess[kMoveChess].getChessY();
								predictModeArr[newX][newY] = playerFlag;
								predictModeArr[newX+1][newY] = 0;
								newMoveChess[0].setChess(newX+1, newY);
								newMoveChess[1].setChess(newX, newY);
								break;
					case 1: 	newX = playerChess[kMoveChess].getChessX();
								newY = playerChess[kMoveChess].getChessY()+1;
								predictModeArr[newX][newY] = playerFlag;
								predictModeArr[newX][newY-1] = 0;
								newMoveChess[0].setChess(newX, newY-1);
								newMoveChess[1].setChess(newX, newY);
								break;
					case 2: 	newX = playerChess[kMoveChess].getChessX()+1;
								newY = playerChess[kMoveChess].getChessY();
								predictModeArr[newX][newY] = playerFlag;
								predictModeArr[newX-1][newY] = 0;
								newMoveChess[0].setChess(newX-1, newY);
								newMoveChess[1].setChess(newX, newY);
								break;
					case 3: 	newX = playerChess[kMoveChess].getChessX();
								newY = playerChess[kMoveChess].getChessY()-1;
								predictModeArr[newX][newY] = playerFlag;
								predictModeArr[newX][newY+1] = 0;
								newMoveChess[0].setChess(newX, newY+1);
								newMoveChess[1].setChess(newX, newY);
								break;
				}
				chessMoveDir[kMoveChess][dir] = true;
				
				int finishedDir = 0;
				for( ; finishedDir < 4; finishedDir++) {
					if(chessMoveDir[kMoveChess][finishedDir] == false)
						break;
				}
				if(finishedDir == 4)	//该棋子所有移动方向已检测完毕
					hasCheckedChess[kMoveChess] = true;
				
				return;
			}
			else	dir = Math.abs(rdm.nextInt())%4;	//重新随机产生移动方向
		}
		
	}
	
	//******分别判断以一个棋子为左上顶点的四方格区域周围与之在同一直线上的上右下左4格的吃子情况********
	private void checkLSquareTop(int x, int y) {
		if(x > 0 && x < boardX-1) {
			if((x == newMoveChess[1].getChessX() && y == newMoveChess[1].getChessY() && predictModeArr[x+1][y] == playerFlag) ||
					(x+1 == newMoveChess[1].getChessX() && y == newMoveChess[1].getChessY())) {
				if(((x > 1 && predictModeArr[x-2][y] == 0) || x == 1) &&
						((x < boardX-2 && predictModeArr[x+2][y] == 0) || x == boardX-2) &&
						predictModeArr[x-1][y] == -1*playerFlag) {	//炮击点
					predictModeArr[x-1][y] = 0;
				}
			}
		}
	}
	
	private void checkLSquareRight(int x, int y) {
		if(y < boardY-2) {
			if((x == newMoveChess[1].getChessX() && y == newMoveChess[1].getChessY() && predictModeArr[x][y+1] == playerFlag) ||
					(x == newMoveChess[1].getChessX() && y+1 == newMoveChess[1].getChessY())) {
				if(((y > 0 && predictModeArr[x][y-1] == 0) || y == 0) &&
						((y < boardY-3 && predictModeArr[x][y+3] == 0) || y == boardY-3) &&
						predictModeArr[x][y+2] == -1*playerFlag) {	//炮击点
					predictModeArr[x][y+2] = 0;
				}
			}
		}
	}
	
	private void checkLSquareBottom(int x, int y) {
		if(x < boardX-2) {
			if((x == newMoveChess[1].getChessX() && y == newMoveChess[1].getChessY() && predictModeArr[x+1][y] == playerFlag) ||
					(x+1 == newMoveChess[1].getChessX() && y == newMoveChess[1].getChessY())) {
				if(((x > 0 && predictModeArr[x-1][y] == 0) || x == 0) &&
						((x < boardX-3 && predictModeArr[x+3][y] == 0) || x == boardX-3) &&
						predictModeArr[x+2][y] == -1*playerFlag) {	//炮击点
					predictModeArr[x+2][y] = 0;
				}
			}
		}
	}
	
	private void checkLSquareLeft(int x, int y) {
		if(y > 0 && y < boardY-1) {
			if((x == newMoveChess[1].getChessX() && y == newMoveChess[1].getChessY() && predictModeArr[x][y+1] == playerFlag) ||
					(x == newMoveChess[1].getChessX() && y+1 == newMoveChess[1].getChessY())) {
				if(((y > 1 && predictModeArr[x][y-2] == 0) || y == 1) &&
						((y < boardY-2 && predictModeArr[x][y+2] == 0) || y == boardY-2) &&
						predictModeArr[x][y-1] == -1*playerFlag) {	//炮击点
					predictModeArr[x][y-1] = 0;
				}
			}
		}
	}
	
}
