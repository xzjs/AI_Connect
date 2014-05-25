package com.org.ouc.TwoBeatOne;

import com.org.ouc.platform.Position;

/**
 * �����˵���������󣬸�����Ӧ���������ɵ���Ӧ����Ҫ�������㣺
 * 1������ 2���ڸ�������ִ�еĶ���
 * 
 * @author yh
 *
 */
public class MoveResp {

	/*�������Ӷ���*/
	public static final int ACTION_DISPEAR = -1;
	
	/*�������Ӷ���*/
	public static final int ACTION_DRAW = 1;
	
	/*Ĭ�϶��������κζ���*/
	public static final int ACTION_DEFAULT = 0; 
	
	/*�����*/
	private Position point = null;
	
	private Position point2 = null;
	/*����*/
	private int action = 0;
	
	/**
	 * ���캯��
	 * @param p   �����
	 * @param action	��Ӧ����
	 */
	public MoveResp(Position p,int action){
		this.point = p;
		this.action = action;
	}
	public MoveResp( Position pos1, Position pos2, int action ){
		this.action = action;
		this.point = pos1;
		this.point2 = pos2;
	}
	/**
	 * ��������
	 * @param p ����
	 */
	public void setPoint(Position p){
		this.point = p;
	}
	
	/**
	 * ���ö���
	 * @param p ����
	 */
	public void setAction(int action){
		this.action = action;
	}
	
	/**
	 * �õ�����
	 * @return point-����
	 */
	public Position getPoint(){
		return this.point;
	}
	
	/**
	 * �õ�����
	 * @return int-����
	 */
	public int getAction(){
		return this.action;
	}
	
	
	
}
