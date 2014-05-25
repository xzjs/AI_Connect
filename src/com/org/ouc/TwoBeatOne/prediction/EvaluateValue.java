package com.org.ouc.TwoBeatOne.prediction;

import com.org.ouc.TwoBeatOne.TwoBeatOneBoard;


public class EvaluateValue {	//棋局评价值

	private int role = 1;	//当前走步玩家，1：我方、-1：敌方
	private int evaluateValue = 0;	//预测棋局评价值
	private int[][] predictMode = null;	//预测棋局
	private ChessCoordinate newMove = new ChessCoordinate(-1, -1);	//新移动棋子坐标
	
	//构造函数
	public EvaluateValue(int role, int[][] mode, ChessCoordinate move) {
		this.role = role;
		this.evaluateValue = 0;
		this.predictMode = mode;
		this.newMove = move;
	}
	
	public int getEvaluateValue() {	//计算预测棋局的评价值
		int boardX = TwoBeatOneBoard.XLen;
		int boardY = TwoBeatOneBoard.YLen;
		int[][] chessModeArr = new int[boardX][boardY];
		chessModeArr = predictMode;
		
		int myTotalValue = 0;	//我方状态估计值总和
		int enemyTotalValue = 0;	//敌方状态估计值总和
		
		for(int i = 0; i < boardX; i++) {
			for(int j = 0; j < boardY; j++) {
				if(chessModeArr[i][j] == 1) {	//计算我方状态估计值
					myTotalValue += 500;
					
					myTotalValue += checkLSquareTop1(1, i, j, boardX, boardY, chessModeArr, newMove);
					myTotalValue += checkLSquareTop2(1, i, j, boardX, boardY, chessModeArr);
					myTotalValue += checkLSquareRight1(1, i, j, boardX, boardY, chessModeArr, newMove);
					myTotalValue += checkLSquareRight2(1, i, j, boardX, boardY, chessModeArr);
					myTotalValue += checkLSquareBottom1(1, i, j, boardX, boardY, chessModeArr);
					myTotalValue += checkLSquareBottom2(1, i, j, boardX, boardY, chessModeArr, newMove);
					myTotalValue += checkLSquareLeft1(1, i, j, boardX, boardY, chessModeArr);
					myTotalValue += checkLSquareLeft2(1, i, j, boardX, boardY, chessModeArr, newMove);
					
					if(i < boardX-1 && j > 0 && chessModeArr[i+1][j-1] == 1) {
						myTotalValue += checkRSquareTop(1, i, j, boardX, boardY, chessModeArr);
						myTotalValue += checkRSquareRight1(1, i, j, boardX, boardY, chessModeArr);
						myTotalValue += checkRSquareRight2(1, i, j, boardX, boardY, chessModeArr);
						myTotalValue += checkRSquareBottom(1, i, j, boardX, boardY, chessModeArr);
						myTotalValue += checkRSquareLeft1(1, i, j, boardX, boardY, chessModeArr);
						myTotalValue += checkRSquareLeft2(1, i, j, boardX, boardY, chessModeArr);
					}
					
					myTotalValue += checkLineTop(1, i, j, boardX, boardY, chessModeArr);
					myTotalValue += checkLineRight(1, i, j, boardX, boardY, chessModeArr);
					myTotalValue += checkLineBottom(1, i, j, boardX, boardY, chessModeArr);
					myTotalValue += checkLineLeft(1, i, j, boardX, boardY, chessModeArr);
				}
				
				else if(chessModeArr[i][j] == -1) {	//计算敌方状态估计值
					enemyTotalValue += 500;
					
					enemyTotalValue += checkLSquareTop1(-1, i, j, boardX, boardY, chessModeArr, newMove);
					enemyTotalValue += checkLSquareTop2(-1, i, j, boardX, boardY, chessModeArr);
					enemyTotalValue += checkLSquareRight1(-1, i, j, boardX, boardY, chessModeArr, newMove);
					enemyTotalValue += checkLSquareRight2(-1, i, j, boardX, boardY, chessModeArr);
					enemyTotalValue += checkLSquareBottom1(-1, i, j, boardX, boardY, chessModeArr);
					enemyTotalValue += checkLSquareBottom2(-1, i, j, boardX, boardY, chessModeArr, newMove);
					enemyTotalValue += checkLSquareLeft1(-1, i, j, boardX, boardY, chessModeArr);
					enemyTotalValue += checkLSquareLeft2(-1, i, j, boardX, boardY, chessModeArr, newMove);
					
					if(i < boardX-1 && j > 0 && chessModeArr[i+1][j-1] == -1) {
						enemyTotalValue += checkRSquareTop(-1, i, j, boardX, boardY, chessModeArr);
						enemyTotalValue += checkRSquareRight1(-1, i, j, boardX, boardY, chessModeArr);
						enemyTotalValue += checkRSquareRight2(-1, i, j, boardX, boardY, chessModeArr);
						enemyTotalValue += checkRSquareBottom(-1, i, j, boardX, boardY, chessModeArr);
						enemyTotalValue += checkRSquareLeft1(-1, i, j, boardX, boardY, chessModeArr);
						enemyTotalValue += checkRSquareLeft2(-1, i, j, boardX, boardY, chessModeArr);
					}
					
					enemyTotalValue += checkLineTop(-1, i, j, boardX, boardY, chessModeArr);
					enemyTotalValue += checkLineRight(-1, i, j, boardX, boardY, chessModeArr);
					enemyTotalValue += checkLineBottom(-1, i, j, boardX, boardY, chessModeArr);
					enemyTotalValue += checkLineLeft(-1, i, j, boardX, boardY, chessModeArr);
				}
			}
		}
		/*
		System.out.println("myValue="+myTotalValue);
		System.out.println("enemyValue="+enemyTotalValue);
		*/		
		evaluateValue = myTotalValue - enemyTotalValue;
		
		return evaluateValue;
	}
	
	//******分别计算以一个棋子为左上顶点的四方格区域周围上右下左8格的控制分值********
	private int checkLSquareTop1(int playerFlag, int x, int y,
			int boardX, int boardY, int[][] chessModeArr, ChessCoordinate newMove) {
		int control = 0;
		if(x > 0 && x < boardX-1) {
			if((x == newMove.getChessX() && y == newMove.getChessY() && chessModeArr[x+1][y] == playerFlag) ||
					(x+1 == newMove.getChessX() && y == newMove.getChessY()) && playerFlag == role) {
				if(((x > 1 && chessModeArr[x-2][y] == 0) || x == 1) &&
						((x < boardX-2 && chessModeArr[x+2][y] == 0) || x == boardX-2) &&
						chessModeArr[x-1][y] == -1*playerFlag)
					control = 500;	//炮击点
			}
			else if((y > 0 && chessModeArr[x+1][y-1] == playerFlag) ||
					(y < boardY-1 && chessModeArr[x+1][y+1] == playerFlag)) {
				if(chessModeArr[x+1][y] == 0) {
					control = 1;	//半控制点
					if(((x > 1 && chessModeArr[x-2][y] == 0) || x == 1) &&
							((x < boardX-2 && chessModeArr[x+2][y] == 0) || x == boardX-2)) {
						control = 2;	//控制点
						if(chessModeArr[x-1][y] == -1*playerFlag)
							control = 5;	//进攻点
					}
				}
			}
		}
		return control;
	}
	
	private int checkLSquareTop2(int playerFlag, int x, int y,
			int boardX, int boardY, int[][] chessModeArr) {
		int control = 0;
		if(x > 0 && x < boardX-1 && y < boardY-1) {
			if(chessModeArr[x+1][y+1] == playerFlag) {
				if(chessModeArr[x][y+1] == 0) {
					control = 1;	//半控制点
					if(((x > 1 && chessModeArr[x-2][y+1] == 0) || x == 1) &&
							((x < boardX-2 && chessModeArr[x+2][y+1] == 0) || x == boardX-2)) {
						control = 2;	//控制点
						if(chessModeArr[x-1][y+1] == -1*playerFlag)
							control = 5;	//进攻点
					}
				}
			}
		}
		return control;
	}
	
	private int checkLSquareRight1(int playerFlag, int x, int y,
			int boardX, int boardY, int[][] chessModeArr, ChessCoordinate newMove) {
		int control = 0;
		if(y < boardY-2) {
			if((x == newMove.getChessX() && y == newMove.getChessY() && chessModeArr[x][y+1] == playerFlag) ||
					(x == newMove.getChessX() && y+1 == newMove.getChessY()) && playerFlag == role) {
				if(((y > 0 && chessModeArr[x][y-1] == 0) || y == 0) &&
						((y < boardY-3 && chessModeArr[x][y+3] == 0) || y == boardY-3) &&
						chessModeArr[x][y+2] == -1*playerFlag)
					control = 500;	//炮击点
			}
			else if(x < boardX-1 && chessModeArr[x+1][y+1] == playerFlag) {
				if(chessModeArr[x][y+1] == 0) {
					control = 1;	//半控制点
					if(((y > 0 && chessModeArr[x][y-1] == 0) || y == 0) &&
							((y < boardY-3 && chessModeArr[x][y+3] == 0) || y == boardY-3)) {
						control = 2;	//控制点
						if(chessModeArr[x][y+2] == -1*playerFlag)
							control = 5;	//进攻点
					}
				}
			}
		}
		return control;
	}
	
	private int checkLSquareRight2(int playerFlag, int x, int y,
			int boardX, int boardY, int[][] chessModeArr) {
		int control = 0;
		if(x < boardX-1 && y < boardY-2) {
			if(chessModeArr[x+1][y+1] == playerFlag) {
				if(chessModeArr[x+1][y] == 0) {
					control = 1;	//半控制点
					if(((y > 0 && chessModeArr[x+1][y-1] == 0) || y == 0) &&
							((y < boardY-3 && chessModeArr[x+1][y+3] == 0) || y == boardY-3)) {
						control = 2;	//控制点
						if(chessModeArr[x+1][y+2] == -1*playerFlag)
							control = 5;	//进攻点
					}
				}
			}
		}
		return control;
	}
	
	private int checkLSquareBottom1(int playerFlag, int x, int y,
			int boardX, int boardY, int[][] chessModeArr) {
		int control = 0;
		if(x < boardX-2 && y < boardY-1) {
			if(chessModeArr[x+1][y+1] == playerFlag) {
				if(chessModeArr[x][y+1] == 0) {
					control = 1;	//半控制点
					if(((x > 0 && chessModeArr[x-1][y+1] == 0) || x == 0) &&
							((x < boardX-3 && chessModeArr[x+3][y+1] == 0) || x == boardX-3)) {
						control = 2;	//控制点
						if(chessModeArr[x+2][y+1] == -1*playerFlag)
							control = 5;	//进攻点
					}
				}
			}
		}
		return control;
	}
	
	private int checkLSquareBottom2(int playerFlag, int x, int y,
			int boardX, int boardY, int[][] chessModeArr, ChessCoordinate newMove) {
		int control = 0;
		if(x < boardX-2) {
			if((x == newMove.getChessX() && y == newMove.getChessY() && chessModeArr[x+1][y] == playerFlag) ||
					(x+1 == newMove.getChessX() && y == newMove.getChessY()) && playerFlag == role) {
				if(((x > 0 && chessModeArr[x-1][y] == 0) || x == 0) &&
						((x < boardX-3 && chessModeArr[x+3][y] == 0) || x == boardX-3) &&
						chessModeArr[x+2][y] == -1*playerFlag)
					control = 500;	//炮击点
			}
			else if((y > 0 && chessModeArr[x+1][y-1] == playerFlag) ||
					(y < boardY-1 && chessModeArr[x+1][y+1] == playerFlag)) {
				if(chessModeArr[x+1][y] == 0) {
					control = 1;	//半控制点
					if(((x > 0 && chessModeArr[x-1][y] == 0) || x == 0) &&
							((x < boardX-3 && chessModeArr[x+3][y] == 0) || x == boardX-3)) {
						control = 2;	//控制点
						if(chessModeArr[x+2][y] == -1*playerFlag)
							control = 5;	//进攻点
					}
				}
			}
		}
		return control;
	}
	
	private int checkLSquareLeft1(int playerFlag, int x, int y,
			int boardX, int boardY, int[][] chessModeArr) {
		int control = 0;
		if(x < boardX-1 && y > 0 && y < boardY-1) {
			if(chessModeArr[x+1][y+1] == playerFlag) {
				if(chessModeArr[x+1][y] == 0) {
					control = 1;	//半控制点
					if(((y > 1 && chessModeArr[x+1][y-2] == 0) || y == 1) &&
							((y < boardY-2 && chessModeArr[x+1][y+2] == 0) || y == boardY-2)) {
						control = 2;	//控制点
						if(chessModeArr[x+1][y-1] == -1*playerFlag)
							control = 5;	//进攻点
					}
				}
			}
		}
		return control;
	}
	
	private int checkLSquareLeft2(int playerFlag, int x, int y,
			int boardX, int boardY, int[][] chessModeArr, ChessCoordinate newMove) {
		int control = 0;
		if(y > 0 && y < boardY-1) {
			if((x == newMove.getChessX() && y == newMove.getChessY() && chessModeArr[x][y+1] == playerFlag) ||
					(x == newMove.getChessX() && y+1 == newMove.getChessY()) && playerFlag == role) {
				if(((y > 1 && chessModeArr[x][y-2] == 0) || y == 1) &&
						((y < boardY-2 && chessModeArr[x][y+2] == 0) || y == boardY-2) &&
						chessModeArr[x][y-1] == -1*playerFlag)
					control = 500;	//炮击点
			}
			else if(x < boardX-1 && chessModeArr[x+1][y+1] == playerFlag) {
				if(chessModeArr[x][y+1] == 0) {
					control = 1;	//半控制点
					if(((y > 1 && chessModeArr[x][y-2] == 0) || y == 1) &&
							((y < boardY-2 && chessModeArr[x][y+2] == 0) || y == boardY-2)) {
						control = 2;	//控制点
						if(chessModeArr[x][y-1] == -1*playerFlag)
							control = 5;	//进攻点
					}
				}
			}
		}
		return control;
	}
	
	//******分别计算以一个棋子为右上顶点的四方格区域周围上右下左6格的控制分值********
	private int checkRSquareTop(int playerFlag, int x, int y,
			int boardX, int boardY, int[][] chessModeArr) {
		int control = 0;
		if(x > 0) {
			if(chessModeArr[x][y-1] == 0) {
				control = 1;	//半控制点
				if(((x > 1 && chessModeArr[x-2][y-1] == 0) || x == 1) &&
						((x < boardX-2 && chessModeArr[x+2][y-1] == 0) || x == boardX-2)) {
					control = 2;	//控制点
					if(chessModeArr[x-1][y-1] == -1*playerFlag)
						control = 5;	//进攻点
				}
			}
		}
		return control;
	}
	
	private int checkRSquareRight1(int playerFlag, int x, int y,
			int boardX, int boardY, int[][] chessModeArr) {
		int control = 0;
		if(y < boardY-1) {
			if(chessModeArr[x][y-1] == 0) {
				control = 1;	//半控制点
				if(((y > 1 && chessModeArr[x][y-2] == 0) || y == 1) &&
						((y < boardY-2 && chessModeArr[x][y+2] == 0) || y == boardY-2)) {
					control = 2;	//控制点
					if(chessModeArr[x][y+1] == -1*playerFlag)
						control = 5;	//进攻点
				}
			}
		}
		return control;
	}
	
	private int checkRSquareRight2(int playerFlag, int x, int y,
			int boardX, int boardY, int[][] chessModeArr) {
		int control = 0;
		if(y < boardY-1) {
			if(chessModeArr[x+1][y] == 0) {
				control = 1;	//半控制点
				if(((y > 1 && chessModeArr[x+1][y-2] == 0) || y == 1) &&
						((y < boardY-2 && chessModeArr[x+1][y+2] == 0) || y == boardY-2)) {
					control = 2;	//控制点
					if(chessModeArr[x+1][y+1] == -1*playerFlag)
						control = 5;	//进攻点
				}
			}
		}
		return control;
	}
	
	private int checkRSquareBottom(int playerFlag, int x, int y,
			int boardX, int boardY, int[][] chessModeArr) {
		int control = 0;
		if(x < boardX-2) {
			if(chessModeArr[x][y-1] == 0) {
				control = 1;	//半控制点
				if(((x > 0 && chessModeArr[x-1][y-1] == 0) || x == 0) &&
						((x < boardX-3 && chessModeArr[x+3][y-1] == 0) || x == boardX-3)) {
					control = 2;	//控制点
					if(chessModeArr[x+2][y-1] == -1*playerFlag)
						control = 5;	//进攻点
				}
			}
		}
		return control;
	}
	
	private int checkRSquareLeft1(int playerFlag, int x, int y,
			int boardX, int boardY, int[][] chessModeArr) {
		int control = 0;
		if(y > 1) {
			if(chessModeArr[x+1][y] == 0) {
				control = 1;	//半控制点
				if(((y > 2 && chessModeArr[x+1][y-3] == 0) || y == 2) &&
						((y < boardY-1 && chessModeArr[x+1][y+1] == 0) || y == boardY-1)) {
					control = 2;	//控制点
					if(chessModeArr[x+1][y-2] == -1*playerFlag)
						control = 5;	//进攻点
				}
			}
		}
		return control;
	}
	
	private int checkRSquareLeft2(int playerFlag, int x, int y,
			int boardX, int boardY, int[][] chessModeArr) {
		int control = 0;
		if(y > 1) {
			if(chessModeArr[x][y-1] == 0) {
				control = 1;	//半控制点
				if(((y > 2 && chessModeArr[x][y-3] == 0) || y == 2) &&
						((y < boardY-1 && chessModeArr[x][y+1] == 0) || y == boardY-1)) {
					control = 2;	//控制点
					if(chessModeArr[x][y-2] == -1*playerFlag)
						control = 5;	//进攻点
				}
			}
		}
		return control;
	}
	
	//******分别计算一个棋子周围上右下左4格的直线控制分值********
	private int checkLineTop(int playerFlag, int x, int y,
			int boardX, int boardY, int[][] chessModeArr) {
		int control = 0;
		if(x > 0 && x < boardX-2) {
			if(chessModeArr[x+2][y] == playerFlag &&
					chessModeArr[x+1][y] == 0) {
				control = 1;	//半控制点
				if((x > 1 && chessModeArr[x-2][y] == 0) || x == 1) {
					control = 2;	//控制点
					if(chessModeArr[x-1][y] == -1*playerFlag)
						control = 5;	//进攻点
				}
			}
		}
		return control;
	}
	
	private int checkLineRight(int playerFlag, int x, int y,
			int boardX, int boardY, int[][] chessModeArr) {
		int control = 0;
		if(y < boardY-3) {
			if(chessModeArr[x][y+2] == playerFlag &&
					chessModeArr[x][y+1] == 0) {
				control = 1;	//半控制点
				if((y < boardY-4 && chessModeArr[x][y+4] == 0) || y == boardY-4) {
					control = 2;	//控制点
					if(chessModeArr[x][y+3] == -1*playerFlag)
						control = 5;	//进攻点
				}
			}
		}
		return control;
	}
	
	private int checkLineBottom(int playerFlag, int x, int y,
			int boardX, int boardY, int[][] chessModeArr) {
		int control = 0;
		if(x < boardX-3) {
			if(chessModeArr[x+2][y] == playerFlag &&
					chessModeArr[x+1][y] == 0) {
				control = 1;	//半控制点
				if((x < boardX-4 && chessModeArr[x+4][y] == 0) || x == boardX-4) {
					control = 2;	//控制点
					if(chessModeArr[x+3][y] == -1*playerFlag)
						control = 5;	//进攻点
				}
			}
		}
		return control;
	}
	
	private int checkLineLeft(int playerFlag, int x, int y,
			int boardX, int boardY, int[][] chessModeArr) {
		int control = 0;
		if(y > 0 && y < boardY-2) {
			if(chessModeArr[x][y+2] == playerFlag &&
					chessModeArr[x][y+1] == 0) {
				control = 1;	//半控制点
				if((y > 1 && chessModeArr[x][y-2] == 0) || y == 1) {
					control = 2;	//控制点
					if(chessModeArr[x][y-1] == -1*playerFlag)
						control = 5;	//进攻点
				}
			}
		}
		return control;
	}
	
}
