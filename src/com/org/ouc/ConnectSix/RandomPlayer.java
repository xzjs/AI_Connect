package com.org.ouc.ConnectSix;

import java.awt.Point;
import java.util.Random;

import com.org.ouc.platform.Move;
/*
 * 
 */
public class RandomPlayer extends ConnectSixPlayer{

	private Random random = new Random();
	private Point pos1 = new Point();
	private Point pos2 = new Point();
	
	/*
	 * now存储棋盘格局的二维数组， 不包括边界
	 * 如果now[i][j] == this.PIECE_ID,这一位置是自己的子；
	 * 如果now[i][j] == getOpponentPieceID(),这一位置是对手的子；
	 * 否则now[i][j] 这一位置是空的。
	 * 在ConnectSixPlayer中新增了一个方法 Move getOpponentLastStep();获取对手上次的走步
	 * 
	 */
	@Override
	public Move next( int[][] now) {
		
		System.out.println( "Mine: " + this.PIECE_ID + " Opponet: " + getOpponentPieceID() );
		int[][] s = now;
		Move newSteps = null;
		Move last = getLastMove();
		Move oppoentLastMove = getOpponentLastMove();
		
		while(true){
			// 随机选择一个位置，看该位置上是否有该棋手的棋子
			pos1.x = random.nextInt(now.length);
			pos1.y = random.nextInt(now[0].length);
			pos2.x = random.nextInt(now.length);
			pos2.y = random.nextInt(now[0].length);
			if( isFirstStep() ){
				clearFirstStep();
				newSteps = new Move( pos1, pos1 );
				return newSteps;
			}
			// 如果选择的位置上无棋子
			if ( s[pos1.x][pos1.y] == 0 && s[pos2.x][pos2.y] == 0 && !pos1.equals(pos2) ){
				
				newSteps = new Move( pos1, pos2 );
				return newSteps;
			}
		}
	}

}
