package com.org.ouc.platform;

import java.awt.Point;

/**
 * 表示某个棋子的移动轨迹，例如：
 *    Move(炮，A,B)
 *
 */
public class Move {
	
	public String type = "六子棋"; 
	Position startPoint;
	Position endPoint;
	public Piece piece;
	
	public Move(){
		startPoint = new Position();
		endPoint = new Position();
		piece = new Piece();
	}
	public Move(Piece piece,Position start,Position end){
		this.startPoint = start;
		this.endPoint = end;
		this.piece = piece;
	}
	
	/**
	 * 构造函数
	 * @param start  起始点坐标
	 * @param end    终点坐标
	 */
	public Move(Position start,Position end){
		startPoint = start;
		endPoint = end;
	}
	
	/**
	 * 构造函数
	 * @param x1	起始点坐标x值
	 * @param y1	起始点坐标y值
	 * @param x2	终点坐标x值
	 * @param y2	终点坐标y值
	 */
	public Move(int x1,int y1,int x2,int y2){
		this(new Position(x1,y1),new Position(x2,y2));
	}
	
	public Move(int x,int y){
		this(new Position(x,y),new Position(x,y));
	}
	public Move( Point pos1, Point pos2 ){
		this(null, new Position(pos1.x, pos1.y),new Position(pos2.x,pos2.y));
	}
	public Position getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Position startPoint) {
		this.startPoint.x = startPoint.x;
		this.startPoint.y = startPoint.y;
	}
	
	public void setStartPoint( int x, int y ) {
		this.startPoint.x = x;
		this.startPoint.y = y;
	}

	public Position getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Position endPoint) {
		this.endPoint.x = endPoint.x;
		this.endPoint.y = endPoint.y;
	}
	public void setEndPoint( int x, int y ) {
		this.endPoint.x = x;
		this.endPoint.y = y;
	}
	
	/**
	 * 黑白棋双方看棋不同，所以各自眼中棋局不同
	 * 本方法修正由于视角不一致的带来的坐标点错误
	 * 修正后的结果与棋局平台具有同样的视角
	 * 一般被棋局平台调用
	 * @param visualAngleRight  与棋局平台视角是否一致
	 * @param yLen   
	 * @param xLen
	 */
	public Move modifyChessMove(boolean visualAngleRight,int yLen,int xLen){
		if(!visualAngleRight){
			this.startPoint.setX(xLen - 1 - this.startPoint.getX());
			this.startPoint.setY(yLen - 1 - this.startPoint.getY());
			
			this.endPoint.setX(xLen - 1 - this.endPoint.getX());
			this.endPoint.setY(yLen - 1 - this.endPoint.getY());
		}
		
		return this;
	}
}