package com.org.ouc.platform;


/**
 * ���̸�����
 *    xy ��ʾ��������
 *    ij ��ʾ����
 *
 */
public class Position{
	
	int x = 0;
	int y = 0;
	
	int i = 0;
	int j = 0;
	public Position(){
		this( 0, 0 );
	}
	public Position(int x,int y){
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String toString(){
		return String.format("X:%d Y:%d", x, y);
	}
	//�ж������Ƿ����
		public boolean isSamePoint(Position op){
			return (this.x == op.x) && (this.y == op.y);
		}
}
