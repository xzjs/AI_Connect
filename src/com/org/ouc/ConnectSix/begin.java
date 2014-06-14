package com.org.ouc.ConnectSix;

/**
 * Created by ganyiming on 2014/6/15.
 */
public class begin {
    public int border=57;
    public int a[][];
    public int color;
    int center=border/2;
    int i;

    public   start st(int a[][],int color)
    {
        start s=new start();
        if (color==-1)
        {

            a[center][center]=-1;
            s.zo1.x=center;
            s.zo1.y=center;
            s.zo2.x=center;
            s.zo2.y=center;
        }
        else
        {
            if(a[center][center+1]!=-1)
            {
                a[center][center+1]=1;
                if(a[center+1][center]!=-1)
                {
                    a[center+1][center]=1;
                    s.zo1.x=center+1;
                    s.zo1.y=center;
                    s.zo2.x=center;
                    s.zo2.y=center+1;
                }
                else
                {
                    a[center+1][center+1]=1;
                    a[center+2][center]=1;
                    s.zo1.x=center+1;
                    s.zo1.y=center+1;
                    s.zo2.x=center+2;
                    s.zo2.y=center;
                }
            }
            else
            {
                a[center][center+2]=1;
                a[center+1][center+1]=1;
                s.zo1.x=center;
                s.zo1.y=center+2;
                s.zo2.x=center+1;
                s.zo2.y=center+1;

            }
        }
        return s;


    }
}
