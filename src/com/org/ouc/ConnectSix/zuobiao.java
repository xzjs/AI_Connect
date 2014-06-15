package com.org.ouc.ConnectSix;

/**
 * Created by ganyiming on 2014/6/14.
 */
public class zuobiao  implements Cloneable{
    public int x;
    public int y;
    public Object clone(){
        zuobiao o = null;
        try {
            o = (zuobiao) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    public zuobiao(int x,int y)
    {

        this.x=x;
        this.y=y;
    }
}
