package com.org.ouc.ConnectSix;

/**
 * Created by ganyiming on 2014/6/14.
 */
import java.util.ArrayList;
public class test {






        int border=19;//�߽�
        int color=1;//������ɫ
        public int [][]a;
        public int[][] fuzhi1(int p,int q,int j,int k,int [][]a,int[][]d2)//��������һ�����ʱ������̳�ʼ��
        {//p,q��Ԫ�صĺ����꣬�����꣬j,k����ĳ���a[][]�������������飬d2[][]Ҫ��չ������
            int b,c,e,f;
            if(p==0) e=0;
            else e=1;//�ϱ��Ƿ񵽱�

            if(q==0) f=0;

            else f=1;//����Ƿ񵽱�
            for(b=e;b<e+j;b++)
            {
                for(c=e;c<e+k;c++)
                    d2[b][c]=a[b-e][c-f];
            }
            return d2;

        }
        public int[][] fuzhi2(int p,int q,int j,int k,int [][]a,int[][]d2)//���������������ʱ������̳�ʼ��
        {//p,q��Ԫ�صĺ����꣬�����꣬j,k����ĳ���a[][]�������������飬d2[][]Ҫ��չ������
            int b,c,e,f;
            if(p==0) e=0;
            else if(p==1)e=1;//���ϱ���1��
            else e=2;//���ϱ���2��
            if(q==0) f=0;
            else if(q==1)f=1;//�������1��
            else f=2;//�������2��
            for(b=e;b<e+j;b++)
            {
                for(c=e;c<e+k;c++)
                    d2[b][c]=a[b-e][c-f];
            }//��a[][]����d2[][]
            return d2;

        }
        public ArrayList<dian> kuozhan1(int [][]a,int p,int q,int j,int k,int a1,int a2,int a3,int a4)//��չһ����
        {
            ArrayList<dian> list1 =new  ArrayList<dian>();
            dian d2=new dian(j+a1+a3,k+a2+a4);//��չ
            d2.row=j+a1+a3;//row
            d2.col=k+a2+a4;//col
            d2.a3.x=p-a1;//��չ֮����Ԫ�صĺ�����
            d2.a3.y=q-a2;//������

            int b,c;
            for(b=0;b<d2.row;b++)
                for(c=0;c<d2.col;c++)
                {
                    d2.shu[b][c]=0;
                }//����ֵ
            d2.shu=fuzhi1(p,q,j,k,a,d2.shu);///��������һ�����ʱ������̳�ʼ��
            for(b=0;b<d2.row;b++){//�����ճ�����
                for(c=0;c<d2.col;c++)
                {
                    if(d2.shu[b][c]==0)
                    {
                        d2.shu[b][c]=color;
                        d2.a1.x=b;//������
                        d2.a1.y=c;//������
                        list1.add(d2);
                        d2.shu[b][c]=0;//�ָ�ԭ����
                    }
                }
            }

            return list1;

        }
        public ArrayList<dian> kuozhan2(int [][]a,int p,int q,int j,int k,int a1,int a2,int a3,int a4)//��չ������
        {
            ArrayList<dian> list1 =new  ArrayList<dian>();
            dian d2=new dian(j+a1+a3,k+a2+a4);//��չ
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

        }//ͬ��


        public ArrayList<dian> shuzu(int i,int [][]a,int p,int q,int j,int k)
        //i ��ʾ����1��2.j��k��ʾ����ĳ��Ϳ�p��q��ʾ������׸�Ԫ���ھ��ε�λ��


        {
            ArrayList<dian> list =new  ArrayList<dian>();

            int e,f,g,h;


            if(i==2)//����������ֵ
            {
                if(p==0) e=0;//��
                else if(p==1)e=1;
                else e=2;
                if(q==0) f=0;//��
                else if(q==1)f=1;
                else f=2;
                if((p+j)==border) g=0;//��
                else if((p+j)==(border-1))g=1;
                else g=2;
                if(q+k==border) h=0;//��
                else if(q+k==(border-1))h=1;
                else h=2;
                list=kuozhan2(a,p,q,j,k,e,f,g,h);
            }
            if(i==1)//ͬ��
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
