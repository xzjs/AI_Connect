package com.org.ouc.ConnectSix;

/**
 * Created by ganyiming on 2014/6/14.
 */
public class dian {
    public int[][]shu;
    public zuobiao a1,a2,a3;//a1,a2为下棋的点的坐标，a3为矩阵首元素在棋盘的坐标
    public int row;
    public int col;
    public dian(int row,int col)
    {

        this.shu=new int[row][col];
        a1=new zuobiao(0,0);
        a2=new zuobiao(0,0);
        a3=new zuobiao(0,0);
    }
}
