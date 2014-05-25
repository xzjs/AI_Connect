package com.org.ouc.platform;

import java.awt.Point;

/**
 * ��ʾĳ�����ӵ��ƶ��켣�����磺
 *    Move(�ڣ�A,B)
 *
 */
public class Move {
	
	public String type = "������"; 
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
	 * ���캯��
	 * @param start  ��ʼ������
	 * @param end    �յ�����
	 */
	public Move(Position start,Position end){
		startPoint = start;
		endPoint = end;
	}
	
	/**
	 * ���캯��
	 * @param x1	��ʼ������xֵ
	 * @param y1	��ʼ������yֵ
	 * @param x2	�յ�����xֵ
	 * @param y2	�յ�����yֵ
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
	 * �ڰ���˫�����岻ͬ�����Ը���������ֲ�ͬ
	 * ���������������ӽǲ�һ�µĴ�������������
	 * ������Ľ�������ƽ̨����ͬ�����ӽ�
	 * һ�㱻���ƽ̨����
	 * @param visualAngleRight  �����ƽ̨�ӽ��Ƿ�һ��
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