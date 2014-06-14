package com.org.ouc.ConnectSix;

/**
 * Created by xzjs on 2014/6/14.
 */
public class Cut_chess {
    int k1, k2, h1, h2;//[k1][k2]为小明要的第一个坐标。
    int lengh1;//行
    int lengh2;//列
    public static int[][] chess;
    public Cut_chess(int rchess[][]){
        chess=rchess.clone();
    }
    public int[][] cut(){
        int i = 0;
        int j = 0;
        while (chess[i][j] == 0 && j < 59) {//k1.h1分别记录局部棋盘的第一行和最后一行；
            //k2.h2分别记录局部棋盘的第一列和最后一列；
            j++;
            if (j == 59) {
                j = 0;
                i = i + 1;
            }
        }
        k1 = i;
        j = 0;
        i = 58;
        while (chess[i][j] == 0 && j < 59) {
            j++;
            if (j == 59) {
                j = 0;
                i = i - 1;
            }
        }
        h1 = i;
        lengh2 = h1 - k1;
        i = j = 0;
        while (chess[i][j] == 0 && i < 59) {
            i++;
            if (i == 59) {
                i = 0;
                j = j + 1;
            }
        }
        k2 = j;
        i = 0;
        j = 58;
        while (chess[i][j] == 0 && i < 59) {
            i++;
            if (i == 59) {
                i = 0;
                j = j - 1;
            }
        }
        h2 = j;
        lengh1 = h2 - k2;
        int[][] cut_chess = new int[lengh1][lengh2];
        int m=0;
        for (i = k1; i < h1; i++)
        {
            int n=0;
            for (j = k2; j < h2; j++) {
                cut_chess[m][n] = chess[i][j];
                n++;
            }
            m=m+1;
        }
        return cut_chess;
    }
}


