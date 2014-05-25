package com.org.ouc.TwoBeatOne;



import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ColorModel;
import java.awt.image.MemoryImageSource;
import java.util.ArrayList;

import javax.swing.JFrame;


import com.org.ouc.platform.Board;
import com.org.ouc.platform.BoardAgent;
import com.org.ouc.platform.Move;
import com.org.ouc.platform.Piece;
import com.org.ouc.platform.Position;

public class TwoBeatOneBoard extends Board{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* ����һ����x�����*/
	public static final int XLen = 5;		
	/* ����һ����y�����*/
	public static final int YLen = 5;	

	public static TwoBeatOneBoard twoBeatOneBoard = null;
	
	
	/*�ϱ߾࣬*/
	private int topMargin = 0;
	/*��߾�*/
	private int leftMargin = 0;	
	/*x������֮��ļ��*/
	private int xLenBetweenLines = 0;	
	/*y������֮��ļ��*/
	private int yLenBetweenLines = 0;	
	public int getTopMargin() {
		return topMargin;
	}




	public void setTopMargin(int topMargin) {
		this.topMargin = topMargin;
	}




	public int getLeftMargin() {
		return leftMargin;
	}




	public void setLeftMargin(int leftMargin) {
		this.leftMargin = leftMargin;
	}




	public int getxLenBetweenLines() {
		return xLenBetweenLines;
	}




	public void setxLenBetweenLines(int xLenBetweenLines) {
		this.xLenBetweenLines = xLenBetweenLines;
	}




	public int getyLenBetweenLines() {
		return yLenBetweenLines;
	}




	public void setyLenBetweenLines(int yLenBetweenLines) {
		this.yLenBetweenLines = yLenBetweenLines;
	}

	/*����һ����ΪԲ�Σ����ﶨ����뾶*/
	private int chessRadius = 0;
	
	
	/*����ֻ����һ������ʱ������������x���С*/
	private int x = -1;
	/*����ֻ����һ������ʱ������������y���С*/
	private int y = -1;
	/*����*/
	Image BlackStone, WhiteStone;
	/*���ӵ�Ӱ��*/
	Image  EmptyShadow=null,Empty = null;
	final double pixel = 0.8, shadow = 0.7;
	public static Image StaticImage=null,StaticShadowImage=null;
	
	
	/**
	 * �̳���Composite�Ĺ��췽��,��������Ӱ뾶�ļ���
	 * @param arg0	������
	 * @param arg1	����
	 */
	public TwoBeatOneBoard() {
		initialBoard();
		stonesPaint ();
		init();
	}
	

	
	
	/**
	 * �ػ�ͼ��
	 */
	public void paint(Graphics g){
		shadowPaint();
		super.paint(g);
		paintAllChess(g);
	}
	/**
	 * Ϊ�������ӻ��Ϻ�ɫ�ľ���
	 * @param gc	ͼ��������
	 */
	private void drawRect(Graphics g){
		
		if(x < 0 || y < 0)
			return;
		
		Position p = getPositionOfIndex(x, y);
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.drawRect(p.getX() - chessRadius, p.getY() - chessRadius, 2*chessRadius, 2*chessRadius);
		g.setColor(c);
	}
	
	/**
	 * ����ͼƬ
	 */
	public synchronized void shadowPaint(){
		synchronized (this){
			EmptyShadow = createImage(Board.Width, Board.Width);
		}
		repaint();
	}
	
	/**
	 * ��������
	 */
	public void stonesPaint ()
	// Create the (beauty) images of the stones (black and white)
	{	int col= new Color(170,120,70).getRGB();
		int blue = col & 0x0000FF, green = (col & 0x00FF00) >> 8, red = (col & 0xFF0000) >> 16;
		int D = 2*chessRadius;
		boolean Alias = true;
		
		if (BlackStone == null || BlackStone.getWidth(this) != D + 2)
		{	int d = D + 2;
			int pb[] = new int[d*d];
			int pw[] = new int[d*d];
			int i, j, g, k;
			double di, dj, d2 = (double)d/2.0-5e-1, r = d2-2e-1, f = Math.sqrt(3);
			double x, y, z, xr, xg, hh;
			k = 0;
			for (i=0; i<d; i++)
				for (j=0; j<d; j++)
				{	di=i-d2; dj=j-d2;
					hh=r-Math.sqrt(di*di+dj*dj);
					if (hh>=0)
					{	z=r*r-di*di-dj*dj;
					    if (z>0) z=Math.sqrt(z)*f;
					    else z=0;
						x=di; y=dj;
						xr=Math.sqrt(6*(x*x+y*y+z*z));
						xr=(2*z-x+y)/xr;
						if (xr>0.9) xg=(xr-0.9)*10;
						else xg=0;
						if (hh>pixel || !Alias)
						{	g=(int)(10+10*xr+xg*140);
							pb[k]=(255<<24)|(g<<16)|(g<<8)|g;
							g=(int)(200+10*xr+xg*45);
							pw[k]=(255<<24)|(g<<16)|(g<<8)|g;
						}
						else
						{	hh=(pixel-hh)/pixel;
							g=(int)(10+10*xr+xg*140);
							double shade;
							if (di-dj<r/3) shade=1;
							else shade=shadow;
							pb[k]=((255<<24)
							    |(((int)((1-hh)*g+hh*shade*red))<<16)
								|(((int)((1-hh)*g+hh*shade*green))<<8)
								|((int)((1-hh)*g+hh*shade*blue)));
							g=(int)(200+10*xr+xg*45);
							pw[k]=((255<<24)
							    |(((int)((1-hh)*g+hh*shade*red))<<16)
								|(((int)((1-hh)*g+hh*shade*green))<<8)
								|((int)((1-hh)*g+hh*shade*blue)));
						}
					}
					else pb[k]=pw[k]=0;
					k++;
				}
			BlackStone = createImage(
				new MemoryImageSource(d, d, ColorModel.getRGBdefault(),
							pb, 0, d));
			WhiteStone = createImage(
				new MemoryImageSource(d, d, ColorModel.getRGBdefault(),
							pw, 0, d));
		}
	}
	/**
	Create an image of the wooden board. The component is used
	to create the image.
	*/
	public void createwood (int w, int h, Color c, boolean shadows, int ox, int oy, int d)
	{	if (w==0 || h==0) return;
		StaticImage=StaticShadowImage=null;
		int p[]=new int[w*h];
		int ps[]=null;
		if (shadows) ps=new int[w*h];
		int i,j;
		double f=9e-1;
		int col=c.getRGB();
		int blue=col&0x0000FF,green=(col&0x00FF00)>>8,red=(col&0xFF0000)>>16;
		double r,g,b;
		double x,y,dist;
		boolean fine= true;
		for (i=0; i<h; i++)
			for (j=0; j<w; j++)
			{	if (fine)
					f=((Math.sin(18*(double)j/w)+1)/2
					+(Math.sin(3*(double)j/w)+1)/10
					+0.2*Math.cos(5*(double)i/h)+
					+0.1*Math.sin(11*(double)i/h))
					*20+0.5;
				else
					f=((Math.sin(14*(double)j/w)+1)/2
					+0.2*Math.cos(3*(double)i/h)+
					+0.1*Math.sin(11*(double)i/h))
					*10+0.5;
				f=f-Math.floor(f);
				if (f<2e-1) f=1-f/2;
				else if (f<4e-1) f=1-(4e-1-f)/2;
				else f=1;
				if (i==w-1 || (i==w-2 && j<w-2) || j==0
					|| (j==1 && i>1)) f=f/2;
				if (i==0 || (i==1 && j>1) || j>=w-1
					|| (j==w-2 && i<h-1))
				{	r=128+red*f/2; g=128+green*f/2; b=128+blue*f/2;
				}
				else
				{	r=red*f; g=green*f; b=blue*f;
				}
				p[w*i+j]=(255<<24)|((int)(r)<<16)|((int)(g)<<8)|(int)(b);
				if (shadows)
				{	
					f=1;
					y=Math.abs((i-(ox+d/2+(i-ox)/d*(double)d)));
					x=Math.abs((j-(oy+d/2+(j-oy)/d*(double)d)));

					dist=Math.sqrt(x*x+y*y)/d*2;
					if (dist<0.75) f=0.9*dist;
					ps[w*i+j]=(255<<24)|((int)(r*f)<<16)|((int)(g*f)<<8)|(int)(b*f);
				}
			}
		if (shadows)
			StaticShadowImage=createImage(
				new MemoryImageSource(w,h,ColorModel.getRGBdefault(),
						ps,0,w));
		StaticImage=createImage(
				new MemoryImageSource(w,h,ColorModel.getRGBdefault(),
						p,0,w));
	}
	
	/**
	 * ���Ʊ���ͼƬ��������Ӱ��������ͻ�����
	 * @param gr
	 * @param grs
	 */
	public synchronized void trywood (Graphics gr, Graphics grs)
	{
		int D = 5 * chessRadius/2;
		int OP = D / 4;
		if (StaticImage==null){
			createwood(Board.Width, Board.Width, new Color(170,120,70), true,
					topMargin-chessRadius+ OP/2-5 , leftMargin-chessRadius-OP/2-3, D+3);
		}else{
			gr.drawImage(StaticImage, 0, 0, Width-2*Margins/5,Height-2*Margins/5, this);
			if (StaticShadowImage != null && grs != null){
				grs.drawImage(StaticShadowImage, 0, 0, Width-2*Margins/5,Height-2*Margins/5, this);
			}
		}
	}
	/**
	 * �����������ӣ�����status״̬��ȷ����������
	 * @param gc ͼ��������
	 */
	private void paintAllChess(Graphics g){
		
		int xEnd = topMargin + (XLen - 1) * xLenBetweenLines;
		int yEnd = leftMargin + (YLen - 1) * yLenBetweenLines;
		
		int xStart = topMargin;
		int yStart = leftMargin;
		
		Graphics gs = EmptyShadow.getGraphics();
		trywood(g,gs);
		
		
		//����Ӱ
		for(int i=0;i<XLen;i++){
			for(int j=0;j<YLen;j++){
				if(status[i][j] == 0)
					continue;
				drawShadow(g,i,j);
				drawStone(g, i-1, j);
				drawStone(g, i, j+1);
				drawStone(g, i-1, j+1);
				drawStone(g, i, j);
			}
		}
		//������
		float lineBoardWidth = 3.0f;
		float lineWidth = 1.0f;
		for(int i=0;i<XLen;i++){
			if(i==0 || i == XLen-1){
				((Graphics2D)g).setStroke(new BasicStroke(lineBoardWidth));
			}else{
				((Graphics2D)g).setStroke(new BasicStroke(lineWidth));
			}
			g.drawLine( yStart, xStart,yEnd, xStart );
			xStart += xLenBetweenLines;
		}
		
		xStart = topMargin;
		//������
		for(int i=0;i<YLen;i++){
			if(i==0 || i==YLen-1){
				((Graphics2D)g).setStroke(new BasicStroke(lineBoardWidth));
			}else{
				((Graphics2D)g).setStroke(new BasicStroke(lineWidth));
			}
			g.drawLine(yStart,xStart, yStart, xEnd);
			yStart += yLenBetweenLines;
		}		
		//������������
		for(int i=0;i<XLen;i++)
			for(int j=0;j<YLen;j++){
				if(status[i][j] == 0)
					continue;
				drawStone(g,i,j);
			}
	}
	
	/**
	 * ����(i,j)������Ӱ
	 * @param i
	 * @param j
	 */
	public void drawShadow(Graphics g,int i,int j){
		if (i<0 || j<0) return;
		Position p = getPositionOfIndex(i, j);
		int xi = p.getX() - chessRadius;
		int xj = p.getY() - chessRadius;
		int D = 5* chessRadius/2;
		int OP = D / 4;
		if(status[i][j] == 1){
			g.drawImage(EmptyShadow, xi-OP/2, xj + OP/2 , xi + D-OP/2, xj + D+OP/2,
					xi-OP/2, xj + OP/2 , xi + D-OP/2, xj + D+OP/2, this);
		}else if(status[i][j] == -1){
			g.drawImage(EmptyShadow, xi-OP/2, xj + OP/2 , xi + D-OP/2, xj + D+OP/2,
					xi-OP/2, xj + OP/2 , xi + D-OP/2, xj + D+OP/2, this);
		}
	}
	
	/**
	 * ����(i,j)��������
	 * @param g
	 * @param i
	 * @param j
	 */
	public void drawStone(Graphics g,int i,int j){
		if (i<0 || i>= XLen || j<0 || j>= YLen) return;
		Position p = getPositionOfIndex(i, j);
		Color c = g.getColor();
		if(status[i][j] == 1){
			g.drawImage(WhiteStone,p.getX() - chessRadius,p.getY() - chessRadius,this);
			
		}else if(status[i][j] == -1){
			g.drawImage(BlackStone,p.getX() - chessRadius, p.getY() - chessRadius, this);
		}
		g.setColor(c);
		drawRect(g);
	}
	
	
	/**
	 * �õ���x��y�����Ӵ���ʵ��λ��
	 * @param x	�����x���С
	 * @param y �����y���С
	 * @return  Point-�������ʵ��λ��
	 */
	private Position getPositionOfIndex(int x,int y){
		int xp = topMargin + x * xLenBetweenLines;
		int yp = leftMargin + y * yLenBetweenLines;
		
		return new Position(yp,xp);
	}

	
	@Override
	public boolean dispearChessAtPoint(int x, int y, int chessType) {
		status[x][y] = 0;
		return true;
	}

	@Override
	public boolean drawChessAtPoint(int x, int y, int chessType) {
		if(status[x][y] != 0 )
			return false;
		
		this.x =  x;
		this.y = y;
		status[x][y] = chessType;
		
		return true;
	}
	/**
	 * ��ʼ������״̬
	 */
	public void init(){
		status = new int[XLen][YLen];
		int val = 0;
		for(int i=0;i<XLen;i++){
			if(i == 0)
				val = -1;
			else if(i == XLen - 1)
				val = 1;
			else
				val = 0;
			for(int j=0;j<YLen;j++)
				status[i][j] = val;
		}
		x = -1;
		y = -1;
	}	
	/**
	 * �õ�������
	 */
	public Board getInstance() {
		if(twoBeatOneBoard == null)
			twoBeatOneBoard = new TwoBeatOneBoard();
		return twoBeatOneBoard;
	}
	
	//����
	public static void main(String args[]){
		
		JFrame jf = new JFrame();
		jf.setLayout(null);
		TwoBeatOneBoard b = new TwoBeatOneBoard();
		b.setBounds(0, 0, 700, 700);
		jf.setSize(720, 760);
		jf.add(b);
		jf.setVisible(true);
	}

	@Override
	public void setBoardCanvas(int Width, int Height, int XLen, int YLen) {
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * ��Ӷ�Agent�ļ���
	 */
	@Override
	public void addListener(BoardAgent agent) {
		// TODO Auto-generated method stub
		addMouseListener(agent);
		addMouseMotionListener(agent);
	}

	
	/**
	 * �������̲������������Ӱ뾶���ϱ߾ࡢ��߾༰�����֮��
	 * �ļ���ȣ�ֻ�ܱ����캯������
	 */
	@Override
	public void initialBoard() {
		xLenBetweenLines = Width / (XLen + 1);
		yLenBetweenLines = Height / (YLen + 1);
		
		if(xLenBetweenLines < yLenBetweenLines){
			yLenBetweenLines = xLenBetweenLines;
			topMargin = xLenBetweenLines ;	
			leftMargin = (Height - xLenBetweenLines * (YLen - 1))/2;
			chessRadius = 2*xLenBetweenLines /5;
		}else{
			xLenBetweenLines = yLenBetweenLines;
			leftMargin = (Width - yLenBetweenLines * (XLen - 1))/2;
			topMargin = (Width - yLenBetweenLines * (XLen - 1))/2;
			chessRadius = 2*yLenBetweenLines /5;
		}
	}

	@Override
	public ArrayList<Position> move(Move move) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Position p, Piece piece) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear(Position p) {
		// TODO Auto-generated method stub
		
	}
}
