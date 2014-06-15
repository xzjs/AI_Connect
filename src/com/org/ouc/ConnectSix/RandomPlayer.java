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
        if(isFirstStep()) {
            clearFirstStep();
            begin b=new begin();
            start st= b.st(s,this.PIECE_ID);
            pos1.x=st.zo1.x;
            pos1.y=st.zo1.y;
            pos2.x=st.zo2.x;
            pos2.y=st.zo2.y;


            newSteps=new Move(pos1,pos2);
            return  newSteps;
        }else if(this.getSteps()==1){
            begin b=new begin();
            start st= b.st(s,this.PIECE_ID);
            pos1.x=st.zo1.x;
            pos1.y=st.zo1.y;
            pos2.x=st.zo2.x;
            pos2.y=st.zo2.y;
            newSteps=new Move(pos1,pos2);
            return  newSteps;
        }
        //С��
        /*Cut_chess cc=new Cut_chess(s);
      /*  Cut_chess cc=new Cut_chess(s);
        int[][] newchess =cc.cut();
        Fpiece fp1=new Fpiece(oppoentLastMove.getStartPoint().getX(),oppoentLastMove.getStartPoint().getY(),getOpponentPieceID());
        Fpiece fp2=new Fpiece(oppoentLastMove.getStartPoint().getX(),oppoentLastMove.getStartPoint().getY(),getOpponentPieceID());
        Solution so=new Solution();
        so.selece(fp1);
        so.selece(fp2);
        so.fmain();*/

        //С��

        test t=new test();
        /*zuobiao z1=new zuobiao(so.get_piece1.x,so.get_piece1.y);
        zuobiao z2=new zuobiao(so.get_piece2.x,so.get_piece2.y);
        ArrayList<dian> ad=t.shuzu(so.count_num,newchess,cc.k1,cc.k2,cc.lengh1,cc.lengh2,z1,z2);*/
        zuobiao z1=new zuobiao(9,9);
        zuobiao z2=new zuobiao(10,10);

        int [][]a=new int[2][2];
        a[0][0]=-1;
        a[0][1]=1;
        a[1][0]=1;
        a[1][1]=0;
        ArrayList<dian> ad = new ArrayList<dian>();
        ad=t.shuzu(0,a,9,9,2,2,z1,z2,-1);
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
