package com.org.ouc.TwoBeatOne.prediction;

import java.util.*;

import com.org.ouc.TwoBeatOne.TwoBeatOneBoard;


public class SubNodeMode {	//����һ����֧�ڵ���ӽڵ����
	
	private int playerFlag = 1;	//��ǰ�߲���ң�1���ҷ���-1���з�
	private int[][] currentModeArr = null;	//��ǰ����־���
	private int playerChessNum = 0;	//��ǰ�߲���ҿ��ƶ���������
	private boolean[] hasCheckedChess = null;	//���Ҫ�ƶ������ӱ�־��true���Ѽ����ϣ�false��δ������
	private boolean[][] chessMoveDir = null;	//ÿ�����ƶ����ӵ��ĸ��ƶ������־��true���Ѽ�⣬false��δ���
	private ChessCoordinate[] playerChess = null;	//��ǰ���ƶ����ӵ�����
	private int[][] predictModeArr = null;	//��һ��һ��Ԥ�����־���
	private ChessCoordinate[] newMoveChess = new ChessCoordinate[2];	//ĳ��Ԥ����ֶ�Ӧ�ƶ��������¾�����
	private int boardX = 5, boardY = 5;	//���̹��Ĭ��5*5
	
	//���캯��
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
	
	private void checkMoveChess() {	//�����ƶ�������
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
	
	private boolean checkDirection(int x, int y) {	//���÷����Ƿ���ƶ�
		if(x >= 0 && x < boardX && y >= 0 && y < boardY && currentModeArr[x][y] == 0)
			return true;
		else
			return false;
	}
	
	public int getPlayerChessNum() {	//��õ�ǰ�߲���ҿ��ƶ���������������ǰ�ڵ���ӽڵ���Ŀ��
		return this.playerChessNum;
	}
	
	public int[][] getPredictMode() {	//��������ɵ�Ԥ�����
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
	
	public ChessCoordinate[] getNewMoveChess() {	//���ĳԤ����ֶ�Ӧ�ƶ��������¾�����
		return this.newMoveChess;
	}
	
	public boolean hasNotCheckedChess() {	//����Ƿ���δ����������
		for(int i = 0; i < boardY; i++)
			if(hasCheckedChess[i] == false)
				return true;
		return false;
	}
	
	public void makeRandomPredictMode() {	//�������Ԥ����֣���������������
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
	
	public void checkBeatChess() {	//���������ɵ�Ԥ������г��ӵ����
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
	
	private int selectMoveChess() {	//���ѡ��Ҫ�ƶ�������
		Random rdm = new Random();
		int kChess = Math.abs(rdm.nextInt()) % playerChessNum;
		
		if(hasCheckedChess[kChess])
			return -1;
		
		return kChess;
	}
	
	private void selectMoveDir(int kMoveChess) {	//���ѡ���ƶ����ӵķ���
		int newX = -1, newY = -1;
		Random rdm = new Random();
		int dir = Math.abs(rdm.nextInt())%4;	//��������ƶ�����0.�ϣ�1.�ң�2.�£�3.��
		
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
				if(finishedDir == 4)	//�����������ƶ������Ѽ�����
					hasCheckedChess[kMoveChess] = true;
				
				return;
			}
			else	dir = Math.abs(rdm.nextInt())%4;	//������������ƶ�����
		}
		
	}
	
	//******�ֱ��ж���һ������Ϊ���϶�����ķ���������Χ��֮��ͬһֱ���ϵ���������4��ĳ������********
	private void checkLSquareTop(int x, int y) {
		if(x > 0 && x < boardX-1) {
			if((x == newMoveChess[1].getChessX() && y == newMoveChess[1].getChessY() && predictModeArr[x+1][y] == playerFlag) ||
					(x+1 == newMoveChess[1].getChessX() && y == newMoveChess[1].getChessY())) {
				if(((x > 1 && predictModeArr[x-2][y] == 0) || x == 1) &&
						((x < boardX-2 && predictModeArr[x+2][y] == 0) || x == boardX-2) &&
						predictModeArr[x-1][y] == -1*playerFlag) {	//�ڻ���
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
						predictModeArr[x][y+2] == -1*playerFlag) {	//�ڻ���
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
						predictModeArr[x+2][y] == -1*playerFlag) {	//�ڻ���
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
						predictModeArr[x][y-1] == -1*playerFlag) {	//�ڻ���
					predictModeArr[x][y-1] = 0;
				}
			}
		}
	}
	
}
