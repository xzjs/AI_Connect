package com.org.ouc.ConnectSix;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ColorModel;
import java.awt.image.MemoryImageSource;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.org.ouc.platform.Board;
import com.org.ouc.platform.BoardAgent;
import com.org.ouc.platform.Move;
import com.org.ouc.platform.Piece;
import com.org.ouc.platform.Player;
import com.org.ouc.platform.Position;

public class ConnectSixBoard extends Board {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Player player1;
	public Player player2;
	public int turn;
	public Board board;
	/*棋子的影子*/
	Image  EmptyShadow=null,Empty = null;
	public static Image StaticImage=null,StaticShadowImage=null;
	public static Image BlackPiece = null, WhitePiece = null;
	public void init(){
		status = new int[XLen][YLen];
		for(int i=0;i<XLen;i++)
			for(int j=0;j<YLen;j++)
				status[i][j]=0;	
	}
	public ConnectSixBoard(){
		init();
	}
	
	@Override
	public void setBoardCanvas(int Width, int Height, int XLen, int YLen) {
		boardCanvas = new JPanel();
		boardCanvas.setBounds(0, 0, Width, Height);		
	}
	
	/**
	 * 缓冲图片
	 */
	public synchronized void shadowPaint(){
		synchronized (this){
			EmptyShadow = createImage(Board.Width, Board.Width);
		}
		repaint();
	}
	//画棋盘和棋子
	private void paintAllChess(Graphics g){
		Graphics gs = EmptyShadow.getGraphics();
		trywood(g,gs);
		
		//横线
	    g.setColor(Color.black);
		for(int i=0;i<YLen;i++)
			g.drawLine(Margins, Margins+i*unitHeight, Margins+(XLen-1)*unitWidth,  Margins+i*unitHeight);
		
		//横线字母标号
		for(int i=0;i<YLen;i++)
			g.drawString(""+i, 10, Margins+i*unitHeight+5);
		
		//纵线
		for(int i=0;i<XLen;i++)
			g.drawLine( Margins+i*unitWidth, Margins, Margins+i*unitHeight , Margins+(YLen-1)*unitHeight);
		
		//纵线数字标号
		for(int i=0;i<XLen;i++)
			g.drawString(""+i, Margins-5+i*unitWidth, 20);
		
		//画四周边线(粗线)
	      float lineWidth = 3.0f;
	      ((Graphics2D)g).setStroke(new BasicStroke(lineWidth));
	      ((Graphics2D)g).drawLine(Margins,Margins,unitWidth*(XLen-1)+Margins,Margins); 
	      ((Graphics2D)g).drawLine(Margins,unitHeight*(YLen-1)+Margins,unitWidth*(XLen-1)+Margins,(YLen-1)*unitWidth+Margins);
	      ((Graphics2D)g).drawLine(Margins,Margins,Margins,(YLen-1)*unitHeight+Margins);
	      ((Graphics2D)g).drawLine(Margins+unitWidth*(XLen-1),Margins,Margins+unitWidth*(XLen-1),(YLen-1)*unitHeight+Margins);
	      
	      //画9个点
	      if(YLen>=13){
		      
		      g.fillOval(Margins+YLen/6*unitWidth-unitWidth/10, Margins+YLen/6*unitHeight-unitWidth/10, unitWidth/5, unitWidth/5);
		      g.fillOval(Margins+YLen/6*unitWidth-unitWidth/10, Margins+3*(YLen/6)*unitHeight-unitWidth/10, unitWidth/5, unitWidth/5);
		      g.fillOval(Margins+YLen/6*unitWidth-unitWidth/10, Margins+5*(YLen/6)*unitHeight-unitWidth/10, unitWidth/5, unitWidth/5);
		      
		      g.fillOval(Margins+YLen/6*3*unitWidth-unitWidth/10, Margins+YLen/6*unitHeight-unitWidth/10, unitWidth/5, unitWidth/5);
		      g.fillOval(Margins+YLen/6*3*unitWidth-unitWidth/10, Margins+YLen/5*3*unitHeight-unitWidth/10, unitWidth/5, unitWidth/5);
		      g.fillOval(Margins+YLen/6*3*unitWidth-unitWidth/10, Margins+YLen/6*5*unitHeight-unitWidth/10, unitWidth/5, unitWidth/5);
		      
		      g.fillOval(Margins+YLen/6*5*unitWidth-unitWidth/10, Margins+YLen/6*unitHeight-unitWidth/10, unitWidth/5, unitWidth/5);
		      g.fillOval(Margins+YLen/6*5*unitWidth-unitWidth/10, Margins+3*(YLen/6)*unitHeight-unitWidth/10, unitWidth/5, unitWidth/5);
		      g.fillOval(Margins+YLen/6*5*unitWidth-unitWidth/10, Margins+5*(YLen/6)*unitHeight-unitWidth/10, unitWidth/5, unitWidth/5);
		      
	      }
	      //画阴影
	      for(int i=0;i<XLen;i++){
				for(int j=0;j<YLen;j++){
					if(status[i][j] == 0)
						continue;
					int D = unitWidth > unitHeight ? unitHeight : unitWidth;
					int chessRadius = D/2;
					Position p = getPositionOfIndex(i, j);
					int xi = p.getX() - chessRadius;
					int xj = p.getY() - chessRadius;
					
					int left = D / 15, down = D / 6;
					if(status[i][j] == 1){
						
						g.drawImage(EmptyShadow,xi - left,xj + down,xi+D-left,xj+D+down,
								xi,xj,xi+D,xj+D,this);
					}else if(status[i][j] == -1){
						
						g.drawImage(EmptyShadow,xi-left,xj+down,xi+D-left,xj+D+down,
								xi,xj,xi+D,xj+D,this);
					}
				}
			}
			
			//画棋子
			for(int i=0;i<YLen;i++)
				for(int j=0;j<XLen;j++)
				{
					if(status[i][j]==-1)
					{//black
						if( BlackPiece == null ){
							createPieceImage(unitWidth );
						}
						g.drawImage( BlackPiece, j*unitWidth+Margins-unitHeight/2, i*unitHeight+Margins-unitHeight/2,BlackPiece.getWidth(null),BlackPiece.getHeight(null), null );
					}
					if(status[i][j]==1)
					{
						if( WhitePiece == null ){
							createPieceImage(unitWidth );
						}
						g.drawImage( WhitePiece, j*unitWidth+Margins-unitHeight/2, i*unitHeight+Margins-unitHeight/2, WhitePiece.getWidth(null),WhitePiece.getHeight(null), null );
						
					}//white
					
				}	
	}
	public void createPieceImage ( int D )
	// Create the (beauty) images of the stones (black and white)
	{	int col=new Color(170,120,70).getRGB();
		int blue=col&0x0000FF,green=(col&0x00FF00)>>8,red=(col&0xFF0000)>>16;
		boolean Alias= true;
		if (BlackPiece==null || BlackPiece.getWidth(this)!=D+2)
		{	int d=D+2;
			int pb[]=new int[d*d];
			int pw[]=new int[d*d];
			int i,j,g,k;
			double di,dj,d2=(double)d/2.0-5e-1,r=d2-2e-1,f=Math.sqrt(3);
			double x,y,z,xr,xg,hh;
			k=0;
			if ( true ) r-=1;
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
						if (hh>0.8 || !Alias)
						{	g=(int)(10+10*xr+xg*140);
							pb[k]=(255<<24)|(g<<16)|(g<<8)|g;
							g=(int)(200+10*xr+xg*45);
							pw[k]=(255<<24)|(g<<16)|(g<<8)|g;
						}
						else
						{	hh=(0.8-hh)/0.8;
							g=(int)(10+10*xr+xg*140);
							double shade;
							if (di-dj<r/3) shade=1;
							else shade=0.7;
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
			BlackPiece=createImage(
				new MemoryImageSource(d,d,ColorModel.getRGBdefault(),
							pb,0,d));
			WhitePiece=createImage(
				new MemoryImageSource(d,d,ColorModel.getRGBdefault(),
							pw,0,d));
		}
	}

	public synchronized void trywood (Graphics gr, Graphics grs)
	{
		int D = unitWidth > unitHeight ? unitHeight : unitWidth;
		int chessRadius = D/2;
		if (StaticImage==null){
			createwood(Board.Width, Board.Width, new Color(170,120,70), true,
					Margins - chessRadius, Margins - chessRadius, D);
		}else{
			gr.drawImage(StaticImage, 0, 0, Width,Height, this);
			if (StaticShadowImage != null && grs != null){
				grs.drawImage(StaticShadowImage, 0, 0, Width,Height-2, this);
			}
		}
	}
	
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
					if (dist<1.0) f=0.9*dist;
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
	
	public void paint(Graphics g){
		shadowPaint();
		super.paint(g);
		paintAllChess(g);      
	    
	}
	
	/**
	 * 画棋子
	 * @param x
	 * @param y
	 * @param chessType
	 * @return
	 */
	@Override
	public boolean drawChessAtPoint(int x, int y, int chessType) {
		if(status[x][y] != 0 ){
			return false;
		}

		status[x][y] = chessType;		
		return true;
	}
	
	private Position getPositionOfIndex(int x,int y){
		int xp = Margins + x * unitWidth;
		int yp = Margins + y * unitHeight;
		
		return new Position(yp,xp);
	}
	
	//测试
		public static void main(String args[]){
			
			JFrame jf = new JFrame();
			jf.setLayout(null);
			ConnectSixBoard b = new ConnectSixBoard();
			b.init();
			b.setBounds(0, 0, 700, 700);
			jf.setSize(900, 760);
			jf.add(b);
			jf.setVisible(true);
		}

	@Override
	public void addListener(BoardAgent agent) {
		// TODO Auto-generated method stub
		addMouseListener(agent);
		addMouseMotionListener(agent);
	}

	@Override
	public void clear(Position p) {
		// TODO Auto-generated method stub
		
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
	public Board getInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean dispearChessAtPoint(int x, int y, int chessType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void initialBoard() {
		// TODO Auto-generated method stub
		
	}	
}
