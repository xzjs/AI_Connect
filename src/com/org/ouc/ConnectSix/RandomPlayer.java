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
	 * now�洢���̸�ֵĶ�ά���飬 �������߽�
	 * ���now[i][j] == this.PIECE_ID,��һλ�����Լ����ӣ�
	 * ���now[i][j] == getOpponentPieceID(),��һλ���Ƕ��ֵ��ӣ�
	 * ����now[i][j] ��һλ���ǿյġ�
	 * ��ConnectSixPlayer��������һ������ Move getOpponentLastStep();��ȡ�����ϴε��߲�
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
			// ���ѡ��һ��λ�ã�����λ�����Ƿ��и����ֵ�����
			pos1.x = random.nextInt(now.length);
			pos1.y = random.nextInt(now[0].length);
			pos2.x = random.nextInt(now.length);
			pos2.y = random.nextInt(now[0].length);
			if( isFirstStep() ){
				clearFirstStep();
				newSteps = new Move( pos1, pos1 );
				return newSteps;
			}
			// ���ѡ���λ����������
			if ( s[pos1.x][pos1.y] == 0 && s[pos2.x][pos2.y] == 0 && !pos1.equals(pos2) ){
				
				newSteps = new Move( pos1, pos2 );
				return newSteps;
			}
		}
	}

}
