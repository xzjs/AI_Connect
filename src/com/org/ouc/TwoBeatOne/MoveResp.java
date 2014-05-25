package com.org.ouc.TwoBeatOne;

import com.org.ouc.platform.Position;

/**
 * 定义了当棋手走棋后，根据相应规则所生成的响应，主要包括两点：
 * 1、坐标 2、在该坐标上执行的动作
 * 
 * @author yh
 *
 */
public class MoveResp {

	/*消除棋子动作*/
	public static final int ACTION_DISPEAR = -1;
	
	/*绘制棋子动作*/
	public static final int ACTION_DRAW = 1;
	
	/*默认动作：无任何动作*/
	public static final int ACTION_DEFAULT = 0; 
	
	/*坐标点*/
	private Position point = null;
	
	private Position point2 = null;
	/*动作*/
	private int action = 0;
	
	/**
	 * 构造函数
	 * @param p   坐标点
	 * @param action	响应动作
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
	 * 设置坐标
	 * @param p 坐标
	 */
	public void setPoint(Position p){
		this.point = p;
	}
	
	/**
	 * 设置动作
	 * @param p 动作
	 */
	public void setAction(int action){
		this.action = action;
	}
	
	/**
	 * 得到坐标
	 * @return point-坐标
	 */
	public Position getPoint(){
		return this.point;
	}
	
	/**
	 * 得到动作
	 * @return int-动作
	 */
	public int getAction(){
		return this.action;
	}
	
	
	
}
