package com.org.ouc.platform;

import java.awt.Container;
import java.util.ArrayList;

import javax.swing.JPanel;




public abstract class Board extends JPanel implements BoardInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int XLen = 19;	// x�᳤,����
	public static int YLen = 19;	// y�᳤,����
	public static int unitHeight=(Width-2*Margins)/(XLen-1);// ��Ԫ��߶�
	public static int unitWidth=(Height-2*Margins)/(YLen-1); // ��Ԫ����
	
	/**���ӽ�ɫ���壺0  ����������
	 *              1  ����[����-��]��[Χ�塢�����塢����һ������������]
	 *              2  ����[����-��]
	 *              3  ����[����-��/��]
	 *              4  ����[����-ʿ/��]
	 *              5  ����[����-��/˧]
	 *              6  ����[����-��]
	 *              7  ����[����-��/��]
	 * ��Щ���ִ���status[][]��;������Ϊ���ַ����ӣ�������role * -1��Ϊ���ַ�����
	 * �����ӷ���������Agent��draw�����С�
	 */	
	public final int role[]={0,1,2,3,4,5,6,7};
	public int[][] status;
	public Piece[][] pieces;
	public String name = "";  // �������
	
	public JPanel boardCanvas = null;			//Board������
	public Container con=null;
	
	boolean backGroundRunning = true;	// �Ƿ��̨���С��ɴ˾����Ƿ���û����ӡ�������ӵȲ�����
	
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
	 * �õ�������
	 * @return
	 */
	public abstract Board getInstance();
	
	
	/**
	 * �����꣨x��y������������ΪchessType������
	 * 
	 * @param x  ���ӵ��x����
	 * @param y  ���ӵ��y����
	 * @param chessType  ���ӵ����࣬1 Ϊ����/���壬-1Ϊ����
	 * @return   �Ƿ�ɹ��������˶�Ҫ�������ӵ�У��
	 */
	public abstract boolean dispearChessAtPoint(int x, int y, int chessType);	
	
	/**
	 * �����꣨x��y������������ΪchessType������
	 * @param x	���ӵ��x����
	 * @param y ���ӵ��y����
	 * @param chessType  ���ӵ����࣬1 Ϊ����/���壬-1Ϊ����
	 * @return �Ƿ�ɹ�
	 */
	public abstract boolean drawChessAtPoint(int x, int y, int chessType);
	
	public int[][] getStatus(){
		return this.status;
	}
	
	/**
	 * ��ʼ������
	 */
	public abstract void init();
	
	abstract public void setBoardCanvas(int Width,int Height,int XLen,int YLen);
	abstract public void addListener(BoardAgent agent);
	abstract public void initialBoard();                    // ��ʼ������,����status����ĳ�ʼֵ
	abstract public ArrayList<Position> move(Move move);	// ��һ���壬�޸Ķ�ά���飬������һ���Ե����ӵ�λ��
	abstract public void draw(Position p, Piece piece);    	// ��ĳ��λ�ã���ĳ������
	abstract public void clear(Position p);					// ���pλ�õ�����
}
