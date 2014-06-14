package com.org.ouc.ConnectSix;

/**
 * Created by ganyiming on 2014/6/14.
 */
import java.util.ArrayList;
public class test {






        int border=19;//边界
        int color=1;//棋子颜色
        public int [][]a;
        public int[][] fuzhi1(int p,int q,int j,int k,int [][]a,int[][]d2)//档传进来一个点的时候给棋盘初始化
        {//p,q首元素的横坐标，纵坐标，j,k数组的长宽，a[][]代表传过来的数组，d2[][]要扩展的数组
            int b,c,e,f;
            if(p==0) e=0;
            else e=1;//上边是否到边

            if(q==0) f=0;

            else f=1;//左边是否到边
            for(b=e;b<e+j;b++)
            {
                for(c=e;c<e+k;c++)
                    d2[b][c]=a[b-e][c-f];
            }
            return d2;

        }
        public int[][] fuzhi2(int p,int q,int j,int k,int [][]a,int[][]d2)//档传进来两个点的时候给棋盘初始化
        {//p,q首元素的横坐标，纵坐标，j,k数组的长宽，a[][]代表传过来的数组，d2[][]要扩展的数组
            int b,c,e,f;
            if(p==0) e=0;
            else if(p==1)e=1;//离上边有1格
            else e=2;//离上边有2格
            if(q==0) f=0;
            else if(q==1)f=1;//离左边有1格
            else f=2;//离左边有2格
            for(b=e;b<e+j;b++)
            {
                for(c=e;c<e+k;c++)
                    d2[b][c]=a[b-e][c-f];
            }//将a[][]传给d2[][]
            return d2;

        }
        public ArrayList<dian> kuozhan1(int [][]a,int p,int q,int j,int k,int a1,int a2,int a3,int a4)//扩展一个点
        {
            ArrayList<dian> list1 =new  ArrayList<dian>();
            dian d2=new dian(j+a1+a3,k+a2+a4);//扩展
            d2.row=j+a1+a3;//row
            d2.col=k+a2+a4;//col
            d2.a3.x=p-a1;//扩展之后首元素的横坐标
            d2.a3.y=q-a2;//纵坐标

            int b,c;
            for(b=0;b<d2.row;b++)
                for(c=0;c<d2.col;c++)
                {
                    d2.shu[b][c]=0;
                }//赋初值
            d2.shu=fuzhi1(p,q,j,k,a,d2.shu);///档传进来一个点的时候给棋盘初始化
            for(b=0;b<d2.row;b++){//遍历空出填子
                for(c=0;c<d2.col;c++)
                {
                    if(d2.shu[b][c]==0)
                    {
                        d2.shu[b][c]=color;
                        d2.a1.x=b;//横坐标
                        d2.a1.y=c;//纵坐标
                        list1.add(d2);
                        d2.shu[b][c]=0;//恢复原数组
                    }
                }
            }

            return list1;

        }
        public ArrayList<dian> kuozhan2(int [][]a,int p,int q,int j,int k,int a1,int a2,int a3,int a4)//扩展两个点
        {
            ArrayList<dian> list1 =new  ArrayList<dian>();
            dian d2=new dian(j+a1+a3,k+a2+a4);//扩展
            d2.row=j+a1+a3;
            d2.col=k+a2+a4;
            d2.a3.x=p-a1;
            d2.a3.y=q-a2;
            int b1,c1;
            int b,c;
            for(b=0;b<d2.row;b++)
                for(c=0;c<d2.col;c++)
                {
                    d2.shu[b][c]=0;
                }
            d2.shu=fuzhi2(p,q,j,k,a,d2.shu);
            for(b=0;b<d2.row;b++)
                for(c=0;c<d2.col;c++)
                {
                    if(d2.shu[b][c]==0)
                    {
                        d2.shu[b][c]=color;
                        for(b1=0;b1<d2.row;b1++)
                            for(c1=0;c1<d2.col;c1++)
                            {
                                if(d2.shu[b1][c1]==0)
                                {
                                    d2.shu[b1][c1]=color;
                                    d2.a2.x=b1;
                                    d2.a2.y=c1;
                                    list1.add(d2);
                                    d2.shu[b1][c1]=0;
                                }
                            }


                        d2.shu[b][c]=0;
                    }
                }
            return list1;

        }//同上


        public ArrayList<dian> shuzu(int i,int [][]a,int p,int q,int j,int k)
        //i 表示数字1，2.j，k表示数组的长和宽，p，q表示数组的首个元素在矩形的位置


        {
            ArrayList<dian> list =new  ArrayList<dian>();

            int e,f,g,h;


            if(i==2)//传过来两个值
            {
                if(p==0) e=0;//上
                else if(p==1)e=1;
                else e=2;
                if(q==0) f=0;//左
                else if(q==1)f=1;
                else f=2;
                if((p+j)==border) g=0;//下
                else if((p+j)==(border-1))g=1;
                else g=2;
                if(q+k==border) h=0;//右
                else if(q+k==(border-1))h=1;
                else h=2;
                list=kuozhan2(a,p,q,j,k,e,f,g,h);
            }
            if(i==1)//同上
            {
                if(p==0) e=0;
                else e=1;

                if(q==0) f=0;
                else f=1;

                if((p+j)==border) g=0;
                else g=1;

                if(q+k==border) h=0;
                else h=1;

                list=kuozhan1(a,p,q,j,k,e,f,g,h);
            }
            return list;
        }
int da;







}
