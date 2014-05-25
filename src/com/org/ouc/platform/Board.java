package com.org.ouc.platform;

import java.awt.Container;
import java.util.ArrayList;

import javax.swing.JPanel;




public abstract class Board extends JPanel implements BoardInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int XLen = 19;	// x轴长,列数
	public static int YLen = 19;	// y轴长,行数
	public static int unitHeight=(Width-2*Margins)/(XLen-1);// 单元格高度
	public static int unitWidth=(Height-2*Margins)/(YLen-1); // 单元格宽度
	
	/**棋子角色定义：0  代表无棋子
	 *              1  代表[象棋-车]或[围棋、五子棋、二打一棋的有棋子情况]
	 *              2  代表[象棋-马]
	 *              3  代表[象棋-象/相]
	 *              4  代表[象棋-士/仕]
	 *              5  代表[象棋-将/帅]
	 *              6  代表[象棋-炮]
	 *              7  代表[象棋-卒/兵]
	 * 这些数字存在status[][]中;正数，为后手方棋子；负数（role * -1）为先手方棋子
	 * 画棋子方法都存在Agent的draw方法中。
	 */	
	public final int role[]={0,1,2,3,4,5,6,7};
	public int[][] status;
	public Piece[][] pieces;
	public String name = "";  // 棋的名字
	
	public JPanel boardCanvas = null;			//Board的容器
	public Container con=null;
	
	boolean backGroundRunning = true;	// 是否后台运行。由此决定是否调用画棋子、清除棋子等操作。
	
    private BoardAgent firstTurnAgent = null;
	private BoardAgent secondTurnAgent = null;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setBoardAgent(BoardAgent bAgent, int turn)
	{
		bAgent.setTurn(turn);
		if (turn == 1)
			secondTurnAgent = bAgent;
		else 
			firstTurnAgent = bAgent;
		
	}
	
	public BoardAgent getBoardAgent(int turn)
	{
		if (turn == 1)
			return secondTurnAgent;
		else 
			return firstTurnAgent; 
	}
	
	/**
	 * 得到单例类
	 * @return
	 */
	public abstract Board getInstance();
	
	
	/**
	 * 在坐标（x，y）处消除种类为chessType的棋子
	 * 
	 * @param x  棋子点的x坐标
	 * @param y  棋子点的y坐标
	 * @param chessType  棋子的种类，1 为白棋/红棋，-1为白棋
	 * @return   是否成功，包含了对要消除棋子的校验
	 */
	public abstract boolean dispearChessAtPoint(int x, int y, int chessType);	
	
	/**
	 * 在坐标（x，y）处绘制种类为chessType的棋子
	 * @param x	棋子点的x坐标
	 * @param y 棋子点的y坐标
	 * @param chessType  棋子的种类，1 为白棋/红棋，-1为白棋
	 * @return 是否成功
	 */
	public abstract boolean drawChessAtPoint(int x, int y, int chessType);
	
	public int[][] getStatus(){
		return this.status;
	}
	
	/**
	 * 初始化棋盘
	 */
	public abstract void init();
	
	abstract public void setBoardCanvas(int Width,int Height,int XLen,int YLen);
	abstract public void addListener(BoardAgent agent);
	abstract public void initialBoard();                    // 初始化棋盘,设置status数组的初始值
	abstract public ArrayList<Position> move(Move move);	// 走一步棋，修改二维数组，返回这一步吃掉的子的位置
	abstract public void draw(Position p, Piece piece);    	// 在某个位置，画某个棋子
	abstract public void clear(Position p);					// 清除p位置的棋子
}
