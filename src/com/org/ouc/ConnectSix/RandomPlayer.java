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
        s[10][10]=1;
		Move newSteps = null;
		Move last = getLastMove();
		Move oppoentLastMove = getOpponentLastMove();
		
		/*while(true){
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
		}*/
        //小样
        Cut_chess cc=new Cut_chess(s);
        int[][] newchess =cc.cut();
        Fpiece fp1=new Fpiece(oppoentLastMove.getStartPoint().getX(),oppoentLastMove.getStartPoint().getY(),getOpponentPieceID());
        Fpiece fp2=new Fpiece(oppoentLastMove.getStartPoint().getX(),oppoentLastMove.getStartPoint().getY(),getOpponentPieceID());
        Solution so=new Solution();
        so.selece(fp1);
        so.selece(fp2);
        so.fmain();

        //小鸣
        test t=new test();
        zuobiao z1=new zuobiao(so.get_piece1.x,so.get_piece1.y);
        zuobiao z2=new zuobiao(so.get_piece2.x,so.get_piece2.y);
        ArrayList<dian> ad=t.shuzu(so.count_num,newchess,cc.k1,cc.k2,cc.lengh1,cc.lengh2,z1,z2);

        //小高
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
