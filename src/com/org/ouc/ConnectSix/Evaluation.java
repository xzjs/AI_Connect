package com.org.ouc.ConnectSix;

/**
 * Created by xinbo on 2014/5/28.
 * 判断棋型并评估棋局--这是个评估类
 * 倒着看。。。先看最后一个函数。。。
 */


import java.util.ArrayList;


//border 棋局的维度
//
//extern int border;

public class Evaluation //求评估值
{
    public int border = 19;
    int[][] status= new int[border][border];
    int stepIndex;//着子点在一维数组中的序号

    //扩展为真实棋盘,
    public int[][] Enlarge(dian point)
    //arr[][]待扩展
    //(x,y)左上角点位
    //r,c 行列维度
    {
        int x=point.a3.x;
        int y=point.a3.y;
        int r=point.row;
        int c=point.col;
        int[][] arr =point.shu;

        int i = 0, j = 0;
        int[][] status= new int[border][border];
        for(; i < border; i++)
            for(; j < border; j++)
            {
                if((i >= x) && (i <= x+r-1) && (j>=y) && (j <= y+c-1))
                    status[i][j] = arr[i-x][j-y];
                else
                    status[i][j] = 0;
            }
        return status;
    }

    //将水平、垂直、左斜、右斜方向整行都存进一维数组中
    public int[] GetHLine(zuobiao a)
    {
        int[] temp = new int[border];
        for(int i = 0; i < border; i++)
        {
            temp[i] = status[a.x][i];
        }
        stepIndex = a.y;
        return temp;
    }

    public int[] GetVLine(zuobiao a)
    {
        int[] temp = new int[border];
        for(int i = 0; i < border; i++)
        {
            temp[i] = status[i][a.y];
        }
        stepIndex = a.x;
        return temp;
    }

    public int[] GetLLine(zuobiao a)///从左上至右下
    {
        int[] temp = new int[border - Math.abs(a.x-a.y)];
        int i,j,k;
        if(a.x>a.y)  //[4][3]
        {
            i = a.x - a.y;
            j = 0;
        }
        else
        {
            i = 0;
            j = a.y - a.x;
        }
        for(k = 0; k < border; k++)
        {
            if(i+k >= border || j+k >= border)
            {
                break;
            }
            temp[k] = status[i+k][j+k];
        }

        stepIndex = (a.x>a.y) ? a.y:a.x;
        return temp;
    }

    public int[] GetRLine(zuobiao a)///从左下至右上
    {
        int i,j,k,t;  //[i][j]起始坐标
        if(a.x+a.y > border-1)
        {
            i = border-1;
            j = a.x+a.y-border + 1;
            stepIndex = i - a.x;
            t = 2*border - a.x - a.y -1;
        }
        else
        {
            j = 0;
            i = a.y + a.x;
            stepIndex = a.y;
            t = a.x + a.y + 1;
        }

        int[] temp = new int[t];

        for(k = 0; k < border; k++)
        {
            if(i-k < 0 || j+k >= border)
            {
                break;
            }
            temp[k] = status[i-k][j+k];
        }
        return temp;
    }

    public int SingleDirectionValue(int[] arr)
    {
        int value;
        ///////提取重要数据
        int leftRange,rightRange,leftEdge,rightEdge,leftSpace,rightSpace,space;
        leftEdge = stepIndex;
        rightEdge = stepIndex;
        for(int i = stepIndex; 0 <= i && i <= stepIndex;i--)
        {
            if(arr[i] == arr[stepIndex])
                leftEdge = i;
            else
                break;
        }
        for(int i = stepIndex; stepIndex <= i && i <= arr.length - 1; i++)
        {
            if(arr[i] == arr[stepIndex])
                rightEdge = i;
            else
                break;
        }

        int linkLong = rightEdge - leftEdge + 1;  //着子点左右延伸，同色的长度
        int count = linkLong;
        int lCount = 0, rCount = 0;
        int i;
        //求leftRange , lCount
        for(leftRange = leftEdge - 1, i = 0; i < 6 - linkLong && leftRange >= 0; i++)
        {
            if(arr[leftRange] == arr[stepIndex] || arr[leftRange] == 0)
            {
                if(arr[leftRange] == arr[stepIndex])
                {
                    lCount++;
                    count++;
                }
                leftRange--;
            }
            else
            {
                break;
            }
        }
        leftRange++;
        //求rightRange , rCount
        for(rightRange = rightEdge + 1, i = 0; i < 6 - linkLong && rightRange < arr.length; i++)
        {
            if(arr[rightRange] == arr[stepIndex] || arr[rightRange] == 0)
            {
                if(arr[rightRange] == arr[stepIndex])
                {
                    rCount++;
                    count++;
                }
                rightRange++;
            }
            else
            {
                break;
            }
        }
        leftRange--;

        leftSpace = leftEdge - leftRange;
        rightSpace = rightRange - rightEdge;
        space = leftSpace + rightSpace;
        //新的、最终的状态数组
        int statusArrLong = rightRange - leftRange + 1;
        int[] statusArr = new int[statusArrLong];
        for(i = 0; i < statusArrLong; i++)
            statusArr[i] = arr[leftRange + i];

        int[] colorLocation = new int[statusArrLong];
        int t = 0;
        for(i = 0; i < statusArrLong; i++)
            if(statusArr[i] == arr[stepIndex])
            {
                colorLocation[t] = i;
                t += 1;
            }


        ////下面求value值
        if(linkLong >= 6)
            value = 1000;//六连
        else if(linkLong == 5)
        {
            if(statusArr[0] == 0 && statusArr[statusArrLong] == 0 && space>= 2)
                value = 200;//活五
            else if(space == 0)
                value = 0;//死五
            else
                value = 50;//眠五
        }
        else if(linkLong == 4)
        {
            if(space == 4)
            {
                if(count >= 5)
                    value = 200;//活五
                if(count == 4)
                    value = 80;//活四
            }
            else if(space < 4 && space >= 2)
            {
                if(count >= 5)
                    value = 50;//眠五
                if(count == 4)
                    value = 48;//眠四
            }
            else
                value = 0;//死棋
        }
        else if(linkLong == 3)
        {
            if(count == 3)
            {
                if(space >= 5)
                    value = 20;//活三
                else if(space < 3)
                    value = 0;//死三
                else
                    value = 8;//眠三
            }
            if(count == 4)
            {
                value = 48;//眠四
            }
            if(count >=5)
            {
                if(space == 6 && lCount > 0 && rCount > 0)
                    value = 200;//活五
                else
                    value = 50;//眠五
            }
        }
        else if(linkLong == 2)
        {
            if(count == 2)
            {
                if(space >= 6)
                    value = 10;//活二
                else if(space < 4)
                    value = 0;//死
                else
                    value = 5;//眠二
            }
            if(count == 3)
            {
                if(space >= 7)
                {
                    if((statusArr[statusArrLong-3] == 0 && statusArr[statusArrLong-5] == 0 && statusArr[statusArrLong-6] == 0) || (statusArr[2] == 0 && statusArr[4] == 0 && statusArr[5] == 0))
                        value = 20;//活三
                    else
                        value = 16;//朦胧三
                }
                else if(space <= 3)
                    value = 0;//死
                else
                {
                    if(statusArr[0] == 0 && statusArr[statusArrLong - 1] == 0)
                        value = 16;//朦胧三
                    else
                        value = 8;//眠三
                }
            }
            if(count == 4)
            {
                value = 48;//眠四
            }
            if(count == 5)
            {
                value = 50;//眠五
            }
            if(count == 6)
            {
                if(space == 8 && lCount == 2 && rCount == 2)
                    value = 200;//活五
                else
                    value = 50;//眠五
            }
            if(count > 6)
            {
                value = 200;//活五
            }
        }
        else  //着子点处为独立点
        {
            if(space < 5)
            {
                value = 0;
            }
            else
            {
                if(count == 1)
                    value = 1;//活一
                if(count == 2)
                {
                    if(space >= 7 && colorLocation[0] >= 2 && statusArrLong-1-colorLocation[1] >=2 && colorLocation[1]-colorLocation[0]<=3)
                        value = 10;//活二
                    else
                        value = 5;//眠二
                }
                if(count == 3)
                {
                    if(!LongestIsOne(statusArr))
                        value = 0;
                    else
                    {
                        if(colorLocation[0]>0 && colorLocation[1]-colorLocation[0]==2 && colorLocation[2]-colorLocation[1]==2 && statusArrLong-1-colorLocation[2]>0)
                            value = 3;//朦胧三
                        else if(colorLocation[2]-colorLocation[0]==5 && (colorLocation[0]>1 || statusArrLong-1-colorLocation[2]>1))
                            value = 3;//朦胧三
                        else
                            value = 8;//眠三
                    }
                }
                if(count == 4)
                {
                    if(!LongestIsOne(statusArr))
                        value = 0;
                    else
                        value = 48;//眠四
                }
                if(count >= 5)
                {
                    if(!LongestIsOne(statusArr))
                        value = 0;
                    else
                        value = 50;//眠五
                }

            }
        }
        return value;
    }

    public boolean LongestIsOne(int[] arr)
    {
        boolean flag = true;
        for(int i = 0; i < arr.length - 1; i++)
            if(arr[i] != 0 && arr[i+1] != 0)
            {
                flag = false;
                break;
            }
        return flag;
    }

    //计算一个点的评估值
    // point为节点对象，内含未扩展的棋局
    public int CountValue(dian point, zuobiao a)
    {
        int value;
        status = Enlarge(point);

        value = SingleDirectionValue(GetHLine(a)) + SingleDirectionValue(GetVLine(a)) + SingleDirectionValue(GetLLine(a)) + SingleDirectionValue(GetRLine(a));

        return value;
    }


    //计算总的评估值
    //a1,a2为下棋的点的坐标，a3为矩阵首元素在棋盘的坐标；
    public int CountTotalValue(dian point)
    {
        int totalValue;//a1,a2两点评估值的和
        totalValue = CountValue(point, point.a1) + CountValue(point, point.a2);
        return totalValue;

    }


    //生成  众多子节点的评估值 所形成的ArrayList
    public ArrayList<Integer> ValueList(ArrayList<dian> list)
    {
        ArrayList<Integer> valueList = new ArrayList<Integer>();
        for(dian point : list)
        {
            valueList.add(CountTotalValue(point));
        }
        return valueList;
    }

}
