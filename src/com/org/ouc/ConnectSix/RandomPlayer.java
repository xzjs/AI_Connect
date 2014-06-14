package com.org.ouc.ConnectSix;

import java.awt.Point;
import java.util.ArrayList;
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
        s[10][10]=1;
		Move newSteps = null;
		Move last = getLastMove();
		Move oppoentLastMove = getOpponentLastMove();
		
		/*while(true){
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
		}*/
        //С��
        Cut_chess cc=new Cut_chess(s);
        int[][] newchess =cc.cut();
        Fpiece fp1=new Fpiece(oppoentLastMove.getStartPoint().getX(),oppoentLastMove.getStartPoint().getY(),getOpponentPieceID());
        Fpiece fp2=new Fpiece(oppoentLastMove.getStartPoint().getX(),oppoentLastMove.getStartPoint().getY(),getOpponentPieceID());
        Solution so=new Solution();
        so.selece(fp1);
        so.selece(fp2);
        so.fmain();

        //С��
        test t=new test();
        zuobiao z1=new zuobiao(so.get_piece1.x,so.get_piece1.y);
        zuobiao z2=new zuobiao(so.get_piece2.x,so.get_piece2.y);
        ArrayList<dian> ad=t.shuzu(so.count_num,newchess,cc.k1,cc.k2,cc.lengh1,cc.lengh2,z1,z2);

        //С��
        Evaluation ev=new Evaluation();
        dian d=ev.GetNextStep(ad);

        pos1.x=d.a1.x;
        pos1.y=d.a1.y;
        pos2.x=d.a2.x;
        pos2.y=d.a2.y;
        newSteps=new Move(pos1,pos2);
        return newSteps;
	}

}
