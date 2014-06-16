package com.org.ouc.ConnectSix;

import com.org.ouc.platform.Move;

import java.util.ArrayList;

/**
 * Created by xzjs on 2014/6/15.
 */
public class ZongQiPan {
    public static QiPan hei = new QiPan(-1);
    public static QiPan bai = new QiPan(1);
    public static boolean duifangsheng = true;

    public static void Add(int role, int[][] qp) {
        switch (role) {
            case 1:
                ZongQiPan.bai.ali.add(qp.clone());
                break;
            case -1:
                ZongQiPan.hei.ali.add(qp.clone());
                break;
            default:
                break;
        }
    }

    public static void AddMove(Move step, int[][] qp, int role) {
        qp[step.getStartPoint().getX()][step.getStartPoint().getY()] = role;
        qp[step.getEndPoint().getX()][step.getEndPoint().getY()] = role;
        Add(role, qp.clone());
    }

    public  static ArrayList<int[][]> GetArryList(int role){
        return role==1?bai.ali:hei.ali;
    }
}
