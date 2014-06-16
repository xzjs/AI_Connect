package com.org.ouc.ConnectSix;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import com.org.ouc.platform.Move;

/*
 * 
 */
public class RandomPlayer extends ConnectSixPlayer {

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
    public Move next(int[][] now) {

        System.out.println("Mine: " + this.PIECE_ID + " Opponet: " + getOpponentPieceID());
        int[][] s = now;
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

        ZongQiPan.Add(this.PIECE_ID,s.clone());

        //开局
        if (isFirstStep()) {
            clearFirstStep();
            begin b = new begin();
            start st = b.st(s, this.PIECE_ID);
            pos1.x = st.zo1.x;
            pos1.y = st.zo1.y;
            pos2.x = st.zo2.x;
            pos2.y = st.zo2.y;
            newSteps = new Move(pos1, pos2);
            ZongQiPan.AddMove(newSteps,s,this.PIECE_ID);
            return newSteps;
        } else if (this.getSteps() == 1) {
            begin b = new begin();
            start st = b.st(s, this.PIECE_ID);
            pos1.x = st.zo1.x;
            pos1.y = st.zo1.y;
            pos2.x = st.zo2.x;
            pos2.y = st.zo2.y;
            newSteps = new Move(pos1, pos2);
            ZongQiPan.AddMove(newSteps,s,this.PIECE_ID);
            return newSteps;
        }

        //查找xml文件
       /* String phaseString = new String();
        for(int i=0;i<19;i++){
            for(int j=0;j<19;j++){
                switch(now[i][j]){
                    case -1:phaseString += "2";break;
                    case  0:phaseString += "0";break;
                    case  1:phaseString += "1";break;
                }
            }
        }
        Phase phase = new Phase();
        phase.setPhaseString(phaseString);
        Search ssss=new Search();
        int[][] next = ssss.search(phase,1,1);

        if(next!=null) {
            int ste=0;
            for(int i=0;i<19;i++){
                for (int j=0;j<19;j++){
                    if(now[i][j]!=next[i][j]){
                        if(ste==0){
                            pos1.x=i;
                            pos1.y=j;
                            ste++;
                        }else {
                            pos2.x=i;
                            pos2.y=j;
                            ste++;
                        }
                    }
                    if (ste==2){
                        break;
                    }
                }
                if ((ste==2)){
                    break;
                }
            }
            newSteps=new Move(pos1,pos2);
            ZongQiPan.AddMove(newSteps,s,this.PIECE_ID);
            return newSteps;
        }

        //小样
        /*Cut_chess cc=new Cut_chess(s);
      /*  Cut_chess cc=new Cut_chess(s);
        int[][] newchess =cc.cut();
        Fpiece fp1=new Fpiece(oppoentLastMove.getStartPoint().getX(),oppoentLastMove.getStartPoint().getY(),getOpponentPieceID());
        Fpiece fp2=new Fpiece(oppoentLastMove.getStartPoint().getX(),oppoentLastMove.getStartPoint().getY(),getOpponentPieceID());
        Solution so=new Solution();
        so.selece(fp1);
        so.selece(fp2);
        so.fmain();*/

        //小鸣

        test t = new test();
        /*zuobiao z1=new zuobiao(so.get_piece1.x,so.get_piece1.y);
        zuobiao z2=new zuobiao(so.get_piece2.x,so.get_piece2.y);
        ArrayList<dian> ad=t.shuzu(so.count_num,newchess,cc.k1,cc.k2,cc.lengh1,cc.lengh2,z1,z2);*/
        zuobiao z1 = new zuobiao(9, 9);
        zuobiao z2 = new zuobiao(10, 10);

        int[][] a = new int[2][2];
        a[0][0] = -1;
        a[0][1] = 1;
        a[1][0] = 1;
        a[1][1] = 0;
        ArrayList<dian> ad = t.shuzu(0, a, 9, 9, 2, 2, z1, z2, -1);
        //小高
        Evaluation ev = new Evaluation();
        dian d = ev.GetNextStep(ad);

        pos1.x = d.a1.x;
        pos1.y = d.a1.y;
        pos2.x = d.a2.x;
        pos2.y = d.a2.y;
        newSteps = new Move(pos1, pos2);
        ZongQiPan.AddMove(newSteps,s,this.PIECE_ID);
        if(ev.getFlag()){
            GaoXing gs=new GaoXing();
            try {
                gs.Add(ZongQiPan.GetArryList(this.PIECE_ID), this.PIECE_ID);
            }
            catch (Exception ex){
        }
        return newSteps;
    }

}
