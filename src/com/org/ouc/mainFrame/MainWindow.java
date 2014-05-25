package com.org.ouc.mainFrame;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.org.ouc.ConnectSix.ConnectSixBoard;
import com.org.ouc.ConnectSix.ConnectSixBoardAgent;
import com.org.ouc.TwoBeatOne.RandomPlayer;
import com.org.ouc.TwoBeatOne.TwoBeatOneBoard;
import com.org.ouc.TwoBeatOne.TwoBeatOneBoardAgent;
import com.org.ouc.platform.Board;
import com.org.ouc.platform.BoardAgent;
import com.org.ouc.platform.HumanPlayer;
import com.org.ouc.platform.Player;


public class MainWindow extends JFrame{
	
	private int length = 725;
	private int width = 600;
	
	private int leftPartX = 0;      // �������������߿�ľ���
	private int leftPartXLen = 200; // ��������Ŀ��
	private int rightPartX = 206;   // board������߿�ľ���
	private int rightPartXLen = 500;// board��� ctrl�Ŀ��
	
	private int pcpaY = 5 ;         // playerA��������ϱ߿�ľ���
	private int pcpaHeight  =  115; // playerA����ĸ߶�
	
	private int tcY  = pcpaY + pcpaHeight + 5; // ʱ����������ϱ߿�ľ���
	private int tcHeight = 30;                 // ʱ������߶�
	
	private int pcpbY = tcY + tcHeight + 5;    // playerB��������ϱ߿�ľ���
	private int pcpbHeight  =  115;             // playerB����ĸ߶�
	
	private int msgY = pcpbY + pcpbHeight + 5;  // msg��������ϱ߿�ľ���
	private int msgHeight = 260;                // msg����ĸ߶�
	
	private int boardY = pcpaY;                 // board�����ϱ߿�ľ���
	private int boardHeight = 500;              // board�ĸ߶�
	
	private int ctrlY = boardY + boardHeight + 5; // ctrl�����ϱ߿�ľ���
	private int ctrlHeight = 30;                  // ctrl�ĸ߶�
	
	Board board;	
	PlayerComp playerCompA;	// ����A����Ϣ���
	PlayerComp playerCompB;	// ����B����Ϣ���	
	Player pya;			// ����A
	Player pyb;			// ����B
	BoardAgent BoardAgentA;
	BoardAgent BoardAgentB;
	
	TimerComp timerComp; // ʱ�����
	MsgComp msgComp;     // ��Ϣ��ʾ���
	CtrlComp  ctrlComp;  // �������

	private PlayerInfoPopmenu blackPlayerInfo;
	private PlayerInfoPopmenu whitePlayerInfo;
	MainWindow self = null;
	
	JMenuItem mtStartOrEnd;

	public MainWindow() throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException{
		this.setLayout(null);
		this.setTitle("Board Game");
		this.setBounds(100,100 , length, width);
		self = this;
		
		buildMenuBar();
		
		playerCompA = new PlayerComp();
		playerCompA.setRoleType(-1, "�ڷ�");
		playerCompA.setPlayerName("����");
		playerCompA.setBounds(leftPartX, pcpaY,leftPartXLen,pcpaHeight);
		this.add(playerCompA);
		
		timerComp = new TimerComp();
		timerComp.setBounds(leftPartX, tcY,leftPartXLen,tcHeight);
		this.add(timerComp);
		
		playerCompB = new PlayerComp();
		playerCompB.setRoleType(1, "�׷�");
		playerCompB.setPlayerName("С��");
		
		playerCompB.setBounds(leftPartX, pcpbY,leftPartXLen,pcpbHeight);
		this.add(playerCompB);
		
		msgComp = new MsgComp(new JTextArea());
		msgComp.setBounds(leftPartX, msgY,leftPartXLen,msgHeight);
		this.add(msgComp);
		
		
		ctrlComp = new CtrlComp(self);
		ctrlComp.setBounds(rightPartX, ctrlY,rightPartXLen, ctrlHeight);
		this.add(ctrlComp);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		this.setVisible(true);
	}
	
	/**
	 * ����ͼ��
	 */
	public void paint(Graphics g){	
		super.paint(g);
		ctrlComp.draw();
	}
	
	/**
	 * ��Ӳ˵�
	 */
	private void buildMenuBar(){
		JMenuBar mb = new JMenuBar();
		
		JMenu   mSys=new JMenu("ϵͳ"); 
		mtStartOrEnd = new JMenuItem("����");
		mtStartOrEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				JMenuItem rs = (JMenuItem)arg0.getSource();
				if(rs.getText().equals("����")){
					if(checkParam()){
						rs.setText("��ͣ");
						ctrlComp.startEvent();
					}
				}else{
					rs.setText("����");
					ctrlComp.pauseEvent();
				}
			}
		});
		JMenuItem mtRestart = new JMenuItem("���¿���");
		mtRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(board != null){
					ctrlComp.restartEvent();
				}else{
					JOptionPane.showMessageDialog(null, "����ѡ�����ƣ�");
				}
				
			}
		});
		JMenuItem mtExit = new JMenuItem("�˳�");
		mtExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		mSys.add(mtStartOrEnd);
		mSys.add(mtRestart);
		mSys.add(mtExit);
		
		JMenu   mChessType =new JMenu("�������"); 
		JMenuItem mtChessBto1 = new JMenuItem("����һ");
		mtChessBto1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Constant.curChessBoard = Constant.CBOARD_TWO_BEAT_ONE;
				configBoard();
			}
		});
		
		//�ڲ˵������������ѡ��-----------
		JMenuItem mtChessBto2 = new JMenuItem("������");
		mtChessBto2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Constant.curChessBoard = Constant.CBOARD_CONNECT_SIX;
				configBoard();
			}
		});
		//--------------
		
		mChessType.add(mtChessBto1);
		mChessType.add(mtChessBto2);
		
		JMenu   mPlayers =new JMenu("��������"); 
		JMenuItem mtPlayerA = new JMenuItem("��      ��");
		mtPlayerA.addMouseListener(new MouseAdapter() {
			boolean ready = false;
			public void mouseReleased(MouseEvent e) {
				ready = false;
				blackPlayerInfo = new PlayerInfoPopmenu(self,"���úڷ�����",true);
			}
			public void mousePressed(MouseEvent e) {
				ready = true;
			}
		});
		JMenu mWhitePlayer = new JMenu("��/�췽");
		JMenuItem mtHumanPlayer = new JMenuItem("��");
		JMenuItem mtPlayerB = new JMenuItem("��/�췽");
		mtHumanPlayer.addMouseListener(new MouseAdapter(){
			boolean ready = false;
			public void mouseReleased(MouseEvent e) {
				ready = false;
				pyb = new HumanPlayer();
			}
			public void mousePressed(MouseEvent e) {
				ready = true;
			}
		});
		mtPlayerB.addMouseListener(new MouseAdapter() {
			boolean ready = false;
			public void mouseReleased(MouseEvent e) {
				ready = false;
				whitePlayerInfo = new PlayerInfoPopmenu(self,"���ð�/�췽����",false);
			}
			public void mousePressed(MouseEvent e) {
				ready = true;
			}
		});
		mWhitePlayer.add(mtPlayerB);
		mWhitePlayer.add(mtHumanPlayer);
		mPlayers.add(mtPlayerA);
		mPlayers.add(mWhitePlayer);
		
		mb.add(mSys);
		mb.add(mChessType);
		mb.add(mPlayers);
		
		setJMenuBar(mb);
	}
	
	
	/**
	 * �������֣�������ʾ������ʵ����������
	 * @param isBlack	���ֱ�־���ڷ����෴
	 */
	protected void configPlayer(boolean isBlack){
		try {
			
			String jarPath = null;
			URLClassLoader uload;
			if(isBlack){
				playerCompA.setPlayerName(Constant.playerAName);
				playerCompA.setImg(Constant.playerAImg);
				jarPath = "file:///"+Constant.jarAPath;
				uload = new URLClassLoader(new URL[]{new URL(jarPath)});
				String classpath = Constant.playerBMainClass;
				
				pya = (Player)uload.loadClass( classpath ).newInstance();
				pya.setFirstStep();
				pya.setTurn(-1);
				pya.setSteps(0);
				pya.PIECE_ID = Constant.BLACK_PIECE;
				BoardAgentA = this.getBoardAgent();
				BoardAgentA.setBoard(board);
				pya.setBoardAgent(BoardAgentA);
			}else{
				playerCompB.setPlayerName(Constant.playerBName);
				playerCompB.setImg(Constant.playerBImg);
				jarPath = "file:///"+Constant.jarBPath;
				uload = new URLClassLoader(new URL[]{new URL(jarPath)});
				
				String classpath = Constant.playerBMainClass.substring(0, Constant.playerBMainClass.length() - 5 );
				
				pyb = (Player)uload.loadClass(Constant.playerBMainClass).newInstance();
//				File file = new File(jarPath);
//				URL url = file.toURL();
//				URLClassLoader loader = new URLClassLoader(new URL[]{url});  
//				pyb = (Player)loader.loadClass(Constant.playerBMainClass).newInstance();
				
				pyb.setTurn(1);
				pyb.setSteps(0);
				pyb.PIECE_ID = Constant.WHITE_PIECE;
				BoardAgentB = this.getBoardAgent();
				BoardAgentB.setBoard(board);
				pyb.setBoardAgent(BoardAgentB);
			}
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(null, "����jar�ļ����ô���");
		} catch (InstantiationException e) {
			JOptionPane.showMessageDialog(null, "����mainCalss���ô����޷��õ�playerʵ��");
		} catch (IllegalAccessException e) {
			JOptionPane.showMessageDialog(null, "����mainCalss���ô����޷��õ�playerʵ��");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "����mainCalss���ô����޷��õ�playerʵ��");
		}
		
		repaint();
	}
	
	/**
	 * �������෵����Ӧ��Agent
	 * @return
	 */
	public BoardAgent getBoardAgent(){
		BoardAgent boardAgent = null;
		if(Constant.curChessBoard == Constant.CBOARD_TWO_BEAT_ONE){
			boardAgent = new TwoBeatOneBoardAgent();
		}/*else if(Constant.curChessBoard == Constant.CBOARD_REN_JU){
			boardAgent = new RenJuBoardAgent();
		}*/
		//�滻Ϊ
		else if(Constant.curChessBoard == Constant.CBOARD_CONNECT_SIX ){
			boardAgent = new ConnectSixBoardAgent();
		}
		return boardAgent;
	}
	
	
	/**
	 * ��������
	 */
	public void configBoard(){
		if(Constant.curChessBoard.equals(Constant.CBOARD_TWO_BEAT_ONE)){
			if(this.isAncestorOf(board)){
				ctrlComp.endChess();
				this.remove(board);
			}
			
			ctrlComp.getWhoIsFirstCom().setEnabled(true);
		    board = new TwoBeatOneBoard();
		    this.setTitle("TwoBeatOne Game");
		    // �ӷ�������������
			String jarPath = "http://www.estong.org:8080/myPlatform-web/app/myPlatform.jar";
			URLClassLoader uload;
			try {
				uload = new URLClassLoader(new URL[]{new URL(jarPath)});
				pya = (Player)uload.loadClass("com.org.ouc.TwoBeatOne.RobotPlayer").newInstance();				
				System.out.println("Web Service");
			} catch (Exception e) {
				pya = new RandomPlayer(); // ����Ĭ�ϵ�RandomPlayer
				System.out.println("Local Random Player");
			}
			
		    pya.setTurn(-1);
		    pya.setSteps(0);
		    BoardAgentA = new TwoBeatOneBoardAgent();
			BoardAgentA.setBoard(board);
			pya.setBoardAgent(BoardAgentA);
			
		    pyb = new RandomPlayer();
		    //pyb = new HumanPlayer();
		    pyb.setTurn(1);
		    pyb.setSteps(0);
		    BoardAgentB = new TwoBeatOneBoardAgent();
			BoardAgentB.setBoard(board);
			pyb.setBoardAgent(BoardAgentB);
			
		}
		
		if(Constant.curChessBoard.equals(Constant.CBOARD_CONNECT_SIX )){
			// ɾ��֮ǰ������
			if(this.isAncestorOf(board)){
				ctrlComp.endChess();
				this.remove(board);
			}
			ctrlComp.getWhoIsFirstCom().setSelectedIndex(0);
			ctrlComp.getWhoIsFirstCom().setEnabled(false);
			
			board = new ConnectSixBoard();
			this.setTitle("ConnectSix Board Game");
			
			if( blackPlayerInfo != null ){
					
				URLClassLoader uload;
				try {
					uload = new URLClassLoader(new URL[]{new URL(blackPlayerInfo.jarPathT.getText())});
					pya = (Player)uload.loadClass(blackPlayerInfo.mainClassNameT.getText()).newInstance();				
					System.out.println("Web Service");
				} catch (Exception e) {
					pya = new com.org.ouc.ConnectSix.RandomPlayer(); // ����Ĭ�ϵ�RandomPlayer
					System.out.println("Local Random Player");
				}
				blackPlayerInfo = null;
			}else{
				pya = new com.org.ouc.ConnectSix.RandomPlayer(); // ����Ĭ�ϵ�RandomPlayer
				System.out.println("Local Random Player");
			}
			pya.setFirstStep();
		    pya.setTurn(-1);
		    pya.setSteps(0);
		    BoardAgentA = new ConnectSixBoardAgent();
			BoardAgentA.setBoard(board);
			pya.setBoardAgent(BoardAgentA);
			pya.PIECE_ID = Constant.BLACK_PIECE;
		    if( whitePlayerInfo != null ){
		    	URLClassLoader uload;
		    	try {
					uload = new URLClassLoader(new URL[]{new URL(whitePlayerInfo.jarPathT.getText())});
					pyb = (Player)uload.loadClass(whitePlayerInfo.mainClassNameT.getText() ).newInstance();				
					System.out.println("Web Service");
				} catch (Exception e) {
					pyb = new com.org.ouc.ConnectSix.RandomPlayer(); // ����Ĭ�ϵ�RandomPlayer
					System.out.println("Local Random Player");
				}
		    }else{
		    	pyb = new com.org.ouc.ConnectSix.RandomPlayer(); // ����Ĭ�ϵ�RandomPlayer
				System.out.println("Local Random Player");
		    }
		    pyb.setTurn(1);
		    pyb.setSteps(0);
		    pyb.PIECE_ID = Constant.WHITE_PIECE;
		    BoardAgentB = new ConnectSixBoardAgent();
			BoardAgentB.setBoard(board);
			pyb.setBoardAgent(BoardAgentB);
			
			 pya.setOpponent(pyb);
			 pyb.setOpponent(pya);
		}
		
		board.setBounds(rightPartX, boardY,rightPartXLen,boardHeight);
		this.add("center",board);
		this.msgComp.clear();
		board.repaint();   // ���������paint()����
	}
	
	
	public boolean checkParam(){
		if(board == null || pya == null || pyb == null){
			JOptionPane.showMessageDialog(null, "����ȷ����������Ϣ");
			return false;
		}
		
		return true;
	}	
	
	public static void main(String args[]){
		try {
			new MainWindow();
		} catch (InstantiationException e) {
			JOptionPane.showMessageDialog(null, "Error:InstantiationException!");
			System.exit(-1);
		} catch (IllegalAccessException e) {
			JOptionPane.showMessageDialog(null, "Error:IllegalAccessException!");
			System.exit(-1);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error:ClassNotFoundException!");
			System.exit(-1);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error:ClassNotFoundException!");
			System.exit(-1);
		}
	}
}

	