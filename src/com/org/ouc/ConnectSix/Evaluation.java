package com.org.ouc.ConnectSix;

/**
 * Created by xinbo on 2014/5/28.
 * �ж����Ͳ��������--���Ǹ�������
 * ���ſ��������ȿ����һ������������
 */


import java.util.ArrayList;


//border ��ֵ�ά��
//
//extern int border;

public class Evaluation //������ֵ
{
    public int border = 19;
    int[][] status= new int[border][border];
    int stepIndex;//���ӵ���һά�����е����

    //��չΪ��ʵ����,
    public int[][] Enlarge(dian point)
    //arr[][]����չ
    //(x,y)���Ͻǵ�λ
    //r,c ����ά��
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

    //��ˮƽ����ֱ����б����б�������ж����һά������
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

    public int[] GetLLine(zuobiao a)///������������
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

    public int[] GetRLine(zuobiao a)///������������
    {
        int i,j,k,t;  //[i][j]��ʼ����
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
        ///////��ȡ��Ҫ����
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

        int linkLong = rightEdge - leftEdge + 1;  //���ӵ��������죬ͬɫ�ĳ���
        int count = linkLong;
        int lCount = 0, rCount = 0;
        int i;
        //��leftRange , lCount
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
        //��rightRange , rCount
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
        //�µġ����յ�״̬����
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


        ////������valueֵ
        if(linkLong >= 6)
            value = 1000;//����
        else if(linkLong == 5)
        {
            if(statusArr[0] == 0 && statusArr[statusArrLong] == 0 && space>= 2)
                value = 200;//����
            else if(space == 0)
                value = 0;//����
            else
                value = 50;//����
        }
        else if(linkLong == 4)
        {
            if(space == 4)
            {
                if(count >= 5)
                    value = 200;//����
                if(count == 4)
                    value = 80;//����
            }
            else if(space < 4 && space >= 2)
            {
                if(count >= 5)
                    value = 50;//����
                if(count == 4)
                    value = 48;//����
            }
            else
                value = 0;//����
        }
        else if(linkLong == 3)
        {
            if(count == 3)
            {
                if(space >= 5)
                    value = 20;//����
                else if(space < 3)
                    value = 0;//����
                else
                    value = 8;//����
            }
            if(count == 4)
            {
                value = 48;//����
            }
            if(count >=5)
            {
                if(space == 6 && lCount > 0 && rCount > 0)
                    value = 200;//����
                else
                    value = 50;//����
            }
        }
        else if(linkLong == 2)
        {
            if(count == 2)
            {
                if(space >= 6)
                    value = 10;//���
                else if(space < 4)
                    value = 0;//��
                else
                    value = 5;//�߶�
            }
            if(count == 3)
            {
                if(space >= 7)
                {
                    if((statusArr[statusArrLong-3] == 0 && statusArr[statusArrLong-5] == 0 && statusArr[statusArrLong-6] == 0) || (statusArr[2] == 0 && statusArr[4] == 0 && statusArr[5] == 0))
                        value = 20;//����
                    else
                        value = 16;//������
                }
                else if(space <= 3)
                    value = 0;//��
                else
                {
                    if(statusArr[0] == 0 && statusArr[statusArrLong - 1] == 0)
                        value = 16;//������
                    else
                        value = 8;//����
                }
            }
            if(count == 4)
            {
                value = 48;//����
            }
            if(count == 5)
            {
                value = 50;//����
            }
            if(count == 6)
            {
                if(space == 8 && lCount == 2 && rCount == 2)
                    value = 200;//����
                else
                    value = 50;//����
            }
            if(count > 6)
            {
                value = 200;//����
            }
        }
        else  //���ӵ㴦Ϊ������
        {
            if(space < 5)
            {
                value = 0;
            }
            else
            {
                if(count == 1)
                    value = 1;//��һ
                if(count == 2)
                {
                    if(space >= 7 && colorLocation[0] >= 2 && statusArrLong-1-colorLocation[1] >=2 && colorLocation[1]-colorLocation[0]<=3)
                        value = 10;//���
                    else
                        value = 5;//�߶�
                }
                if(count == 3)
                {
                    if(!LongestIsOne(statusArr))
                        value = 0;
                    else
                    {
                        if(colorLocation[0]>0 && colorLocation[1]-colorLocation[0]==2 && colorLocation[2]-colorLocation[1]==2 && statusArrLong-1-colorLocation[2]>0)
                            value = 3;//������
                        else if(colorLocation[2]-colorLocation[0]==5 && (colorLocation[0]>1 || statusArrLong-1-colorLocation[2]>1))
                            value = 3;//������
                        else
                            value = 8;//����
                    }
                }
                if(count == 4)
                {
                    if(!LongestIsOne(statusArr))
                        value = 0;
                    else
                        value = 48;//����
                }
                if(count >= 5)
                {
                    if(!LongestIsOne(statusArr))
                        value = 0;
                    else
                        value = 50;//����
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

    //����һ���������ֵ
    // pointΪ�ڵ�����ں�δ��չ�����
    public int CountValue(dian point, zuobiao a)
    {
        int value;
        status = Enlarge(point);

        value = SingleDirectionValue(GetHLine(a)) + SingleDirectionValue(GetVLine(a)) + SingleDirectionValue(GetLLine(a)) + SingleDirectionValue(GetRLine(a));

        return value;
    }


    //�����ܵ�����ֵ
    //a1,a2Ϊ����ĵ�����꣬a3Ϊ������Ԫ�������̵����ꣻ
    public int CountTotalValue(dian point)
    {
        int totalValue;//a1,a2��������ֵ�ĺ�
        totalValue = CountValue(point, point.a1) + CountValue(point, point.a2);
        return totalValue;

    }


    //����  �ڶ��ӽڵ������ֵ ���γɵ�ArrayList
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
