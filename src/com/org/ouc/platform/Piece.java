package com.org.ouc.platform;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

public class Piece extends JLabel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int turn;	// 先手方、后手方 
	public int role;	// 棋子的种类，车、马、炮等
	public String name; // 棋子的名字，如"炮"、"马"、"相"或"象"
	public int width,height;	// 棋子的宽、高
	public Color fontColor,backColor;
	Board board=null;
	
	Piece(){
		turn=-1;
		role=1;
		name="";
	}
	
	Piece(int turn,int role,String name,int w,int h,Color fc,Color bc,Board board){
		this.turn = turn;
		this.role = role;
		this.name = name;
		this.width = w;
		this.height = h;
		this.fontColor = fc;
		this.backColor = bc;
		setSize(width,height);
		this.board = board;
		System.out.println("创建  棋子 --"+name);
		addMouseMotionListener( board.getBoardAgent(-1));
		addMouseListener(board.getBoardAgent(-1));
			
	}
	
	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setPiece(Piece piece,Board board){
		
	}
	public void paint(Graphics g){
		System.out.println("画棋子"+this.name);
		((Graphics2D)g).setColor(new Color(250,223,184));
		((Graphics2D)g).fillOval(2, 2, width-2, height-2);
		((Graphics2D)g).setColor(this.fontColor);
		((Graphics2D)g).setFont(new Font("隶书",Font.BOLD,23));
		((Graphics2D)g).drawString(name, 12, height-12);
		((Graphics2D)g).setColor(Color.black);
	    float lineWidth = 1.0f;
	    ((Graphics2D)g).setStroke(new BasicStroke(lineWidth));
	    ((Graphics2D)g).drawOval(2, 2, width-2, height-2);
	}
}
