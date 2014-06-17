package com.org.ouc.ConnectSix;

import java.util.ArrayList;

/**
 * Created by xzjs on 2014/6/14.
 */


public class Solution {
    Fpiece piece1;
    Fpiece piece2;
    Fpiece get_piece1;
    Fpiece get_piece2;
    ArrayList<Integer> X_Axis=new ArrayList<Integer>() ;
    ArrayList<Integer> Y_Axis=new ArrayList<Integer>() ;
    ArrayList<Integer> Z_1_Axis=new ArrayList<Integer>() ;
    ArrayList<Integer> Z_2_Axis=new ArrayList<Integer>() ;
    int count_num=0;//��¼���ص�ĸ���
    int [][] S_chess;
    int keyflg=0;
    int location1;
    int location2;//��¼ÿ�������鴦���õ��ĵ��λ�ã�
    int x_location=0;//��location��ӵõ��������ϵ���ꣻ
    int y_location=0;
    int Z_1_1ocation=0;
    int Z_2_location=0;
    public Solution(Fpiece piece1,Fpiece piece2){
      this.piece1=piece1;
      this.piece2=piece2;
    }
    public ArrayList<Integer> Get_X_Axis(Fpiece piece){//�Ӻ��������õ�X�᷽���һ������X_Axis[]��
        int x=piece.x;
        X_Axis.clear();
        int y=piece.y;
        int flag=piece.flag;
        int flg=0;
        int i=1;
        int u=1;
        while((y-i)>=0&&Cut_chess.chess[x][y-i]!=-flag&&flg!=3){
            u=u+1;
            if(Cut_chess.chess[x][y-i]==0)
                flg++;
            else
                flg=0;
            i++;
            if(flg==3)
                i=i-1;
            x_location=i-1;
        }
      
        
        while(u!=1&&i>0){
            X_Axis.add( Cut_chess.chess[x][y-i+1]);
            i--;
           
        }
        i=1;flg=0;
        while(flg!=3&&(y+i)<=18&&Cut_chess.chess[x][y+i]!=-flag){
            if(Cut_chess.chess[x][y+i]==0)
                flg++;
            else
                flg=0;
            if(flg!=3){
                X_Axis.add(Cut_chess.chess[x][y+i])  ;}
            i++;
            
        }
        return X_Axis;
    }

    public ArrayList<Integer> Get_Y_Axis (Fpiece piece) {//�����������õ�Y�᷽���һ������Y_Axis[]��
        int x=piece.x;
        Y_Axis.clear();
        int y=piece.y;
        int flag=piece.flag;
        int flg=0;
        int i=1;
        int u=1;
        while(flg!=3&&(x-i)>=0&&Cut_chess.chess[x-i][y]!=-flag){
            u=u+1;
            if(Cut_chess.chess[x-i][y]==0)
                flg++;
            else
                flg=0;
            i++;
            if(flg==3)
                i=i-1;
            y_location=i-1;
        }
     
        while(u!=1&&i>0){
            
            Y_Axis.add(Cut_chess.chess[x-i+1][y]);
            i--;
            
        }
        i=1;flg=0;
        while(flg!=3&&(x+i)<=18&&Cut_chess.chess[x+i][y]!=-flag){
            if(Cut_chess.chess[x+i][y]==0)
                flg++;
            else
                flg=0;
            if(flg!=3)
                Y_Axis.add(Cut_chess.chess[x+i][y]);
            i++;
            
        }
        return Y_Axis;
    }

    public ArrayList<Integer>Get_Z_1_Axis(Fpiece piece){//��/���������õ�����Z_1_Axis[]��
        int x=piece.x;
        Z_1_Axis.clear();
        int y=piece.y;
        int flag=piece.flag;
        int flg=0;
        int i=1;
        int u=1;
        while(flg!=3&&(y-i)>=0&&(x+i)<=18&&Cut_chess.chess[x+i][y-i]!=-flag){
            u=u+1;
            if(Cut_chess.chess[x+i][y-i]==0)
                flg++;
            else
                flg=0;
            i++;
            if(flg==3)
                i=i-1;
            Z_1_1ocation=i-1;
        }
        
        while(u!=1&&i>0){
           
            Z_1_Axis.add(Cut_chess.chess[x+i-1][y-i+1]);
            i--;
         
        }
        i=1;flg=0;
        while(flg!=3&&(y+i)<=18&&(x-i)>=0&&Cut_chess.chess[x-i][y+i]!=-flag){
            if(Cut_chess.chess[x-i][y+i]==0)
                flg++;
            else
                flg=0;
            if(flg!=3)
                Z_1_Axis.add(Cut_chess.chess[x-i][y+i]);
            i++;
          
        }
        return Z_1_Axis;
    }

    public ArrayList<Integer> Get_Z_2_Axis(Fpiece piece){//��\���������õ�����Z_2_Axis[]��
        int x=piece.x;
        Z_2_Axis.clear();
        int y=piece.y;
        int flag=piece.flag;
        int flg=0;
        int i=1;
        int u=1;
        while(flg!=3&&(y-i)>=0&&(x-i)>=0&&Cut_chess.chess[x-i][y-i]!=-flag){
            u=u+1;
            if(Cut_chess.chess[x-i][y-i]==0)
                flg++;
            else
                flg=0;
            i++;
            if(flg==3)
                i=i-1;
            Z_2_location=i-1;
        }
  
        while(u!=1&&i>0){
            
            Z_2_Axis.add(Cut_chess.chess[x-i+1][y-i+1]);
            i--;
      
        }
        i=1;flg=0;
        while(flg!=3&&(y+i)<=18&&(x+i)<=18&&Cut_chess.chess[x+i][y+i]!=-flag){
            if(Cut_chess.chess[x+i][y+i]==0)
                flg++;
            else
                flg=0;
            if(flg!=3)
                Z_2_Axis.add(Cut_chess.chess[x+i][y+i]);
            i++;
          
        }
        return Z_2_Axis;
    }

    public int sulution(ArrayList<Integer> Axis){
        int i=0;
        ArrayList<Integer>  ReAxis = null;
        int flg=1;//flg=0��ʾ�������ֻ�����ӵ�������������ӻ����
        while(flg==1&&i<Axis.size()){//�ж��и������������ܷ���ֻ�����ӵ�������γ����������
            if(Axis.get(i)==0){
                Axis.set(i,piece1.flag);
                int j=i+1;
                while(flg==1&&j<Axis.size()){
                    if(Axis.get(j)==0){
                      Axis.set(j,piece1.flag);
                        int f=0;
                        for(int h=0;h<Axis.size()-1;h++){
                            if(Axis.get(h)==piece1.flag&&Axis.get(h+1)==piece1.flag){
                              f++;
                              if(f==5)
                                h=Axis.size();
                            }
                                
                            else {
                                f=0;
                            }                           
                        }
                        if(f==5)
                          flg=0;

                          Axis.set(j,0);
                    }
                    j++;
                }
                Axis.set(i,0);
                i++;
                
            }
            else i++;
        }
        if(flg==0){//
            int n=0;
            int m;
            int flg2=6;

            ArrayList<Integer> num=new ArrayList<Integer>();//��¼������Ϊ0���±ꣻ
            while(n<Axis.size()&&flg2==6){//�������пհ״��������Ϊ-piece1.flag,����հ�ȫ��piece1.flag��䣬�ж��Ƿ��������6�ӻ����
                if(Axis.get(n)==0){
                    num.add(n);

                    Axis.set(n,-piece1.flag);
                    ReAxis=(ArrayList<Integer> )Axis.clone();
                    for(m=n+1;m<Axis.size();m++){
                        if(ReAxis.get(m)==0)
                            ReAxis.set(m,-piece1.flag);
                    }
                    flg2=1;
                    for(m=0;m<ReAxis.size()-1;m++){
                        if(ReAxis.get(m)==piece1.flag&&ReAxis.get(m+1)==piece1.flag){
                          flg2++;
                          if(flg2==6)
                            m=ReAxis.size()-1;
                        }
                            
                        else
                            flg2=0;
                    }
                    n++;
                }
                else n++;

            }
            int x=0;//�����ж���˴�flg2һ��С��6��
            while(flg2<6){//�����������-flagֱ����������6�ӻ����ʱ�������ν�ԭ�����Ϊ-flag����Ϊflag���жϸĺ��Ƿ��������6�ӻ����
                int y=num.get(x);
                flg2=1;
                ReAxis.set(y,piece1.flag);//��ԭ��0����Ϊflag��
                for(m=0;m<ReAxis.size()-1;m++){
                    if(ReAxis.get(m)==piece1.flag&&ReAxis.get(m+1)==piece1.flag){
                      flg2++;
                      if(flg2==6)
                        m=ReAxis.size();
                    }
                        
                    else
                        flg2=0;
                }
                x++;
            }
            if(x<num.size()){//location1��2�Ƿֱ��¼�µ���2�������ꣻ���x=num.length˵��ֻ��һ����Ϳ��Զ����ˣ�
                location1=num.get(x-1);
                location2=num.get(x);
                count_num=2;
                keyflg=1;
            }
            else {
                location1=num.get(x-1);
                count_num=1;
                keyflg=1;
            }

            return 1;
        }
        else {
            return 0;
        }
    }

    public int selece(Fpiece rpiece1){
        
        if(sulution(Get_X_Axis(rpiece1))==1)
            return 1;
        if(sulution(Get_Y_Axis(rpiece1))==1)
            return 2;
        if(sulution(Get_Z_1_Axis(rpiece1))==1)
            return 3;
        if(sulution(Get_Z_2_Axis(rpiece1))==1)
            return 4;
        else return 5;
    }

    public void fmain(Fpiece piece1){


            switch(selece(piece1)){
                case 1:{
                    if(count_num==2){
                        get_piece1=new Fpiece(piece1.x,piece1.y-x_location+location1,-piece1.flag);
                        get_piece2=new Fpiece(piece1.x,piece1.y-x_location+location2,-piece1.flag);
                        Cut_chess.chess[piece1.x][piece1.y-x_location+location1]=-piece1.flag;
                        Cut_chess.chess[piece1.x][piece1.y-x_location+location2]=-piece1.flag;
                    }
                    else {
                        get_piece1=new Fpiece(piece1.x,piece1.y-x_location+location1,-piece1.flag);
                        Cut_chess.chess[piece1.x][piece1.y-x_location+location1]=-piece1.flag;
                        get_piece2=new Fpiece(0,0,1);
                    }
                }break;
                case 2:{
                    if(count_num==2){
                        get_piece1=new Fpiece(piece1.x-y_location+location1,piece1.y,-piece1.flag);
                        get_piece2=new Fpiece(piece1.x-y_location+location2,piece1.y,-piece1.flag);
                        Cut_chess.chess[piece1.x-y_location+location1][piece1.y]=-piece1.flag;
                        Cut_chess.chess[piece1.x-y_location+location2][piece1.y]=-piece1.flag;
                    }
                    else {
                        get_piece1=new Fpiece(piece1.x-y_location+location1,piece1.y,-piece1.flag);
                        Cut_chess.chess[piece1.x-y_location+location1][piece1.y]=-piece1.flag;
                        get_piece2=new Fpiece(0,0,1);
                    }
                }break;
                case 3:{
                    if(count_num==2){
                        get_piece1=new Fpiece(piece1.x+Z_1_1ocation+location1,piece1.y-Z_1_1ocation+location1,-piece1.flag);
                        get_piece2=new Fpiece(piece1.x+Z_1_1ocation+location2,piece1.y-Z_1_1ocation+location2,-piece1.flag);
                        Cut_chess.chess[piece1.x+Z_1_1ocation+location1][piece1.y-Z_1_1ocation+location1]=-piece1.flag;
                        Cut_chess.chess[piece1.x+Z_1_1ocation+location2][piece1.y-Z_1_1ocation+location2]=-piece1.flag;
                    }
                    else {
                        get_piece1=new Fpiece(piece1.x+Z_1_1ocation+location1,piece1.y-Z_1_1ocation+location1,-piece1.flag);
                        Cut_chess.chess[piece1.x+Z_1_1ocation+location1][piece1.y-Z_1_1ocation+location1]=-piece1.flag;
                        get_piece2=new Fpiece(0,0,1);
                    }
                }break;
                case 4:{
                    if(count_num==2){
                        get_piece1=new Fpiece(piece1.x-Z_2_location+location1,piece1.y-Z_2_location-location1,-piece1.flag);
                        get_piece2=new Fpiece(piece1.x-Z_2_location+location2,piece1.y-Z_2_location-location2,-piece1.flag);
                        Cut_chess.chess[piece1.x-Z_2_location+location1][piece1.y-Z_2_location-location1]=-piece1.flag;
                        Cut_chess.chess[piece1.x-Z_2_location+location2][piece1.y-Z_2_location-location2]=-piece1.flag;
                    }
                    else {
                        get_piece1=new Fpiece(piece1.x-Z_2_location+location1,piece1.y-Z_2_location-location1,-piece1.flag);
                        Cut_chess.chess[piece1.x-Z_2_location+location1][piece1.y-Z_2_location-location1]=-piece1.flag;
                        get_piece2=new Fpiece(0,0,1);
                    }
                }break;
                case 5:{
                    count_num=0;
                    get_piece1=new Fpiece(0,0,1);
                    get_piece2=new Fpiece(0,0,1);


                }break;
            }



       
    }


}



