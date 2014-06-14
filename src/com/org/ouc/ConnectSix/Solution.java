package com.org.ouc.ConnectSix;

/**
 * Created by xzjs on 2014/6/14.
 */


public class Solution {
    Fpiece piece1;
    Fpiece piece2;
    Fpiece get_piece1;
    Fpiece get_piece2;
    int[] X_Axis;
    int[] Y_Axis;
    int[] Z_1_Axis;
    int[] Z_2_Axis;
    int count_num=0;//��¼���ص�ĸ���
    int [][] S_chess;
    int location1;
    int location2;//��¼ÿ�������鴦���õ��ĵ��λ�ã�
    int x_location=0;//��location��ӵõ��������ϵ����ꣻ
    int y_location=0;
    int Z_1_1ocation=0;
    int Z_2_location=0;
    public int[] Get_X_Axis(Fpiece piece){//�Ӻ��������õ�X�᷽���һ������X_Axis[]��
        int x=piece.x;
        int y=piece.y;
        int flag=piece.flag;
        int flg=0;
        int i=1;
        while(flg!=3&&(x-i)!=0&&Cut_chess.chess[x-i][y]!=-flag){
            if(Cut_chess.chess[x-i][y]==0)
                flg++;
            else
                flg=0;
            i++;
            if(flg==3)
                i=i-1;
            x_location=i;
        }
        int j=0;
        for( j=0;i>=0;j++){
            X_Axis[j]=Cut_chess.chess[x-i][y];
            i--;
        }
        i=1;flg=0;
        while(flg!=3&&(x+i)!=59&&Cut_chess.chess[x+i][y]!=-flag){
            if(Cut_chess.chess[x+i][y]==0)
                flg++;
            else
                flg=0;
            if(flg!=3)
                X_Axis[j]=Cut_chess.chess[x+i][y];
            i++;
            j++;
        }
        return X_Axis;
    }

    public int[] Get_Y_Axis (Fpiece piece) {//�����������õ�Y�᷽���һ������Y_Axis[]��
        int x=piece.x;
        int y=piece.y;
        int flag=piece.flag;
        int flg=0;
        int i=1;
        while(flg!=3&&(y-i)!=0&&Cut_chess.chess[x][y-i]!=-flag){
            if(Cut_chess.chess[x][y-i]==0)
                flg++;
            else
                flg=0;
            i++;
            if(flg==3)
                i=i-1;
            y_location=i;
        }
        int j=0;
        for( j=0;i>=0;j++){
            Y_Axis[j]=Cut_chess.chess[x][y-i];
            i--;
        }
        i=1;flg=0;
        while(flg!=3&&(y+i)!=59&&Cut_chess.chess[x][y+i]!=-flag){
            if(Cut_chess.chess[x][y+i]==0)
                flg++;
            else
                flg=0;
            if(flg!=3)
                Y_Axis[j]=Cut_chess.chess[x][y+i];
            i++;
            j++;
        }
        return Y_Axis;
    }

    public int[] Get_Z_1_Axis(Fpiece piece){//��/���������õ�����Z_1_Axis[]��
        int x=piece.x;
        int y=piece.y;
        int flag=piece.flag;
        int flg=0;
        int i=1;
        while(flg!=3&&(y-i)!=0&&(x-i)!=0&&Cut_chess.chess[x-i][y-i]!=-flag){
            if(Cut_chess.chess[x-i][y-i]==0)
                flg++;
            else
                flg=0;
            i++;
            if(flg==3)
                i=i-1;
            Z_1_1ocation=i;
        }
        int j=0;
        for( j=0;i>=0;j++){
            Z_1_Axis[j]=Cut_chess.chess[x-i][y-i];
            i--;
        }
        i=1;flg=0;
        while(flg!=3&&(y+i)!=59&&(x+i)!=59&&Cut_chess.chess[x+i][y+i]!=-flag){
            if(Cut_chess.chess[x+i][y+i]==0)
                flg++;
            else
                flg=0;
            if(flg!=3)
                Z_1_Axis[j]=Cut_chess.chess[x+i][y+i];
            i++;
            j++;
        }
        return Z_1_Axis;
    }

    public int[] Get_Z_2_Axis(Fpiece piece){//��\���������õ�����Z_2_Axis[]��
        int x=piece.x;
        int y=piece.y;
        int flag=piece.flag;
        int flg=0;
        int i=1;
        while(flg!=3&&(y+i)!=59&&(x-i)!=0&&Cut_chess.chess[x-i][y+i]!=-flag){
            if(Cut_chess.chess[x-i][y+i]==0)
                flg++;
            else
                flg=0;
            i++;
            if(flg==3)
                i=i-1;
            Z_2_location=i;
        }
        int j=0;
        for( j=0;i>=0;j++){
            Z_2_Axis[j]=Cut_chess.chess[x-i][y+i];
            i--;
        }
        i=1;flg=0;
        while(flg!=3&&(y-i)!=0&&(x+i)!=59&&Cut_chess.chess[x+i][y-i]!=-flag){
            if(Cut_chess.chess[x+i][y-i]==0)
                flg++;
            else
                flg=0;
            if(flg!=3)
                Z_2_Axis[j]=Cut_chess.chess[x+i][y-i];
            i++;
            j++;
        }
        return Z_2_Axis;
    }

    public int sulution(int [] Axis){
        int i=0;
        int ReAxis[] = null;
        int flg=1;//flg=0��ʾ�������ֻ�����ӵ�������������ӻ����
        while(flg==1&&i<Axis.length){//�ж��и������������ܷ���ֻ�����ӵ�������γ����������
            if(Axis[i]==0){
                Axis[i]=piece1.flag;
                int j=i+1;
                while(flg==1&&j<Axis.length){
                    if(Axis[j]==0){
                        Axis[j]=piece1.flag;
                        int f=0;
                        for(int h=0;h<Axis.length-1;h++){
                            if(Axis[h]==piece1.flag&&Axis[h+1]==piece1.flag)
                                f++;
                            else {
                                f=0;
                            }
                        }
                        if(f==6)
                            flg=0;
                        else
                            Axis[j]=0;
                    }
                    j++;
                }
                i++;
            }
            i++;
        }
        if(flg==0){//
            int n=0;
            int m;
            int flg2=1;
            int h=0;
            int [] num=new int [h];//��¼������Ϊ0���±ꣻ
            while(n<Axis.length&&flg2>=6){//�������пհ״��������Ϊ-piece1.flag,����հ�ȫ��piece1.flag��䣬�ж��Ƿ��������6�ӻ����
                if(Axis[n]==0){
                    num[h]=n;
                    h++;
                    Axis[n]=piece1.flag;
                    ReAxis=Axis;
                    for(m=n+1;m<Axis.length;m++){
                        if(ReAxis[m]==0)
                            ReAxis[m]=piece1.flag;
                    }
                    for(m=0;m<ReAxis.length-1;m++){
                        if(ReAxis[m]==piece1.flag&&ReAxis[m+1]==piece1.flag)
                            flg2++;
                        else
                            flg2=0;
                    }
                    n++;
                }
                n++;

            }
            int x=0;//�����ж���˴�flg2һ��С��6��
            while(flg2<6){//�����������-flagֱ����������6�ӻ����ʱ�������ν�ԭ�����Ϊ-flag����Ϊflag���жϸĺ��Ƿ��������6�ӻ����
                int y=num[x];
                ReAxis[y]=piece1.flag;//��ԭ��0����Ϊflag��
                for(m=0;m<ReAxis.length-1;m++){
                    if(ReAxis[m]==piece1.flag&&ReAxis[m+1]==piece1.flag)
                        flg2++;
                    else
                        flg2=0;
                }
                x++;
            }
            if(x<num.length){//location1��2�Ƿֱ��¼�µ���2��������ꣻ���x=num.length˵��ֻ��һ����Ϳ��Զ����ˣ�
                location1=num[x-1];
                location2=num[x];
                count_num=2;
            }
            else {
                location1=num[x-1];
                count_num=1;
            }

            return 1;
        }
        else {
            return 0;
        }
    }

    public int selece(Fpiece rpiece1){
        Solution s=new Solution();
        if(s.sulution(s.Get_X_Axis(rpiece1))==1)
            return 1;
        if(s.sulution(s.Get_Y_Axis(rpiece1))==1)
            return 2;
        if(s.sulution(s.Get_Z_1_Axis(rpiece1))==1)
            return 3;
        else
            return 4;
    }

    public void fmain(){
        switch(selece(piece1)){
            case 1:{
                if(count_num==2){
                    get_piece1=new Fpiece(piece1.x-x_location+location1,piece1.y,-piece1.flag);
                    get_piece2=new Fpiece(piece1.x-x_location+location2,piece1.y,-piece1.flag);
                    Cut_chess.chess[piece1.x-x_location+location1][piece1.y]=-piece1.flag;
                    Cut_chess.chess[piece1.x-x_location+location2][piece1.y]=-piece1.flag;
                }
                else {
                    get_piece1=new Fpiece(piece1.x-x_location+location1,piece1.y,-piece1.flag);
                    Cut_chess.chess[piece1.x-x_location+location1][piece1.y]=-piece1.flag;
                }
            }
            case 2:{
                if(count_num==2){
                    get_piece1=new Fpiece(piece1.x,piece1.y-y_location+location1,-piece1.flag);
                    get_piece2=new Fpiece(piece1.x,piece1.y-y_location+location2,-piece1.flag);
                    Cut_chess.chess[piece1.x][piece1.y-y_location+location1]=-piece1.flag;
                    Cut_chess.chess[piece1.x][piece1.y-y_location+location2]=-piece1.flag;
                }
                else {
                    get_piece1=new Fpiece(piece1.x,piece1.y-y_location+location1,-piece1.flag);
                    Cut_chess.chess[piece1.x][piece1.y-y_location+location1]=-piece1.flag;
                }
            }
            case 3:{
                if(count_num==2){
                    get_piece1=new Fpiece(piece1.x-Z_1_1ocation+location1,piece1.y-Z_1_1ocation+location1,-piece1.flag);
                    get_piece2=new Fpiece(piece1.x-Z_1_1ocation+location2,piece1.y-Z_1_1ocation+location2,-piece1.flag);
                    Cut_chess.chess[piece1.x-Z_1_1ocation+location1][piece1.y-Z_1_1ocation+location1]=-piece1.flag;
                    Cut_chess.chess[piece1.x-Z_1_1ocation+location2][piece1.y-Z_1_1ocation+location2]=-piece1.flag;
                }
                else {
                    get_piece1=new Fpiece(piece1.x-Z_1_1ocation+location1,piece1.y-Z_1_1ocation+location1,-piece1.flag);
                    Cut_chess.chess[piece1.x-Z_1_1ocation+location1][piece1.y-Z_1_1ocation+location1]=-piece1.flag;
                }
            }
            case 4:{
                if(count_num==2){
                    get_piece1=new Fpiece(piece1.x-Z_2_location+location1,piece1.y+Z_2_location-location1,-piece1.flag);
                    get_piece2=new Fpiece(piece1.x-Z_2_location+location2,piece1.y+Z_2_location-location2,-piece1.flag);
                    Cut_chess.chess[piece1.x-Z_2_location+location1][piece1.y+Z_2_location-location1]=-piece1.flag;
                    Cut_chess.chess[piece1.x-Z_2_location+location2][piece1.y+Z_2_location-location2]=-piece1.flag;
                }
                else {
                    get_piece1=new Fpiece(piece1.x-Z_2_location+location1,piece1.y+Z_2_location-location1,-piece1.flag);
                    Cut_chess.chess[piece1.x-Z_2_location+location1][piece1.y+Z_2_location-location1]=-piece1.flag;
                }
            }
        }

        switch(selece(piece2)){
            case 1:{
                if(count_num==2){
                    get_piece1=new Fpiece(piece2.x-x_location+location1,piece2.y,-piece2.flag);
                    get_piece2=new Fpiece(piece2.x-x_location+location2,piece2.y,-piece2.flag);
                    Cut_chess.chess[piece2.x-x_location+location1][piece2.y]=-piece2.flag;
                    Cut_chess.chess[piece2.x-x_location+location2][piece2.y]=-piece2.flag;
                }
                else {
                    get_piece1=new Fpiece(piece2.x-x_location+location1,piece2.y,-piece2.flag);
                    Cut_chess.chess[piece2.x-x_location+location1][piece2.y]=-piece2.flag;
                }
            }
            case 2:{
                if(count_num==2){
                    get_piece1=new Fpiece(piece2.x,piece2.y-y_location+location2,-piece2.flag);
                    get_piece2=new Fpiece(piece2.x,piece2.y-y_location+location2,-piece2.flag);
                    Cut_chess.chess[piece2.x][piece2.y-y_location+location1]=-piece2.flag;
                    Cut_chess.chess[piece2.x][piece2.y-y_location+location2]=-piece2.flag;
                }
                else {
                    get_piece1=new Fpiece(piece2.x,piece2.y-y_location+location1,-piece2.flag);
                    Cut_chess.chess[piece2.x][piece2.y-y_location+location1]=-piece2.flag;
                }
            }
            case 3:{
                if(count_num==2){
                    get_piece1=new Fpiece(piece2.x-Z_1_1ocation+location1,piece2.y-Z_1_1ocation+location1,-piece2.flag);
                    get_piece2=new Fpiece(piece2.x-Z_1_1ocation+location2,piece2.y-Z_1_1ocation+location2,-piece2.flag);
                    Cut_chess.chess[piece2.x-Z_1_1ocation+location1][piece2.y-Z_1_1ocation+location1]=-piece2.flag;
                    Cut_chess.chess[piece2.x-Z_1_1ocation+location2][piece2.y-Z_1_1ocation+location2]=-piece2.flag;
                }
                else {
                    get_piece1=new Fpiece(piece2.x-Z_1_1ocation+location1,piece2.y-Z_1_1ocation+location1,-piece2.flag);
                    Cut_chess.chess[piece2.x-Z_1_1ocation+location1][piece2.y-Z_1_1ocation+location1]=-piece2.flag;
                }
            }
            case 4:{
                if(count_num==2){
                    get_piece1=new Fpiece(piece2.x-Z_2_location+location1,piece2.y+Z_2_location-location1,-piece2.flag);
                    get_piece2=new Fpiece(piece2.x-Z_2_location+location2,piece2.y+Z_2_location-location2,-piece2.flag);
                    Cut_chess.chess[piece2.x-Z_2_location+location1][piece2.y+Z_2_location-location1]=-piece2.flag;
                    Cut_chess.chess[piece2.x-Z_2_location+location2][piece2.y+Z_2_location-location2]=-piece2.flag;
                }
                else {
                    get_piece1=new Fpiece(piece2.x-Z_2_location+location1,piece2.y+Z_2_location-location1,-piece2.flag);
                    Cut_chess.chess[piece2.x-Z_2_location+location1][piece2.y+Z_2_location-location1]=-piece2.flag;
                }
            }
        }
    }


}



