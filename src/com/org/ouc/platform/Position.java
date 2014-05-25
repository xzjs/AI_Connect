package com.org.ouc.platform;


/**
 * 棋盘格坐标
 *    xy 表示绝对坐标
 *    ij 表示格数
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
	//判断两点是否相等
		public boolean isSamePoint(Position op){
			return (this.x == op.x) && (this.y == op.y);
		}
}
