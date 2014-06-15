package com.org.ouc.ConnectSix;

/**
 * Created by ganyiming on 2014/6/14.
 */
public class dian implements Cloneable  {
    public int[][]shu;
    public zuobiao a1,a2,a3;//a1,a2为下棋的点的坐标,基于小棋局上的，a3为矩阵首元素在棋盘的坐标
    public int row;//共几行
    public int col;
    public Object clone() {
        dian o = null;
        try {
            o = (dian) super.clone();
            o.a1=(zuobiao)a1.clone();
            o.a2=(zuobiao)a2.clone();
            o.a3=(zuobiao)a3.clone();
            o.shu=shu.clone();
            for(int b=0;b<o.row;b++)
                shu[b]=shu[b].clone();


        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    public dian(int row,int col)
    {

        this.shu=new int[row][col];
        a1=new zuobiao(0,0);
        a2=new zuobiao(0,0);
        a3=new zuobiao(0,0);
    }
}
