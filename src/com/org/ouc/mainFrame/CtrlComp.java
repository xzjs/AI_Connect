package com.org.ouc.mainFrame;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.org.ouc.ConnectSix.ConnectSixBoard;
import com.org.ouc.ConnectSix.ConnectSixBoardAgent;
import com.org.ouc.ConnectSix.ConnectSixPlayer;
import com.org.ouc.TwoBeatOne.MoveResp;
import com.org.ouc.TwoBeatOne.TwoBeatOneBoard;
import com.org.ouc.TwoBeatOne.TwoBeatOneBoardAgent;
import com.org.ouc.TwoBeatOne.TwoBeatOnePlayer;
import com.org.ouc.platform.Board;
import com.org.ouc.platform.BoardAgent;
import com.org.ouc.platform.Move;
import com.org.ouc.platform.Player;



public class CtrlComp extends JPanel{
	int turnCnt = 0 ; 	// 0: 黑方， 1：白方
	
	/**当前两步之间的时间*/
	int curTimeBTS = 0;
	/**当前还剩几次尝试*/
	int curTimeRAS = 0;

	/**两步之间时间限制*/
	int timeBetweenTwoSteps = 10;
	/**尝试步数限制*/
	int timeResultAfterStep = 10;

	boolean gameOver = false;
	boolean newStep = true;
	
	int[][] curs = null;
	Move curm = null;	//
	/**开局按钮*/
	private JButton startChessBt = null;
	/**确定谁是先手*/
	private JComboBox whoIsFirstCom = null;
	/**退出系统*/
	private JButton exitBt = null;
	
	
	/*棋局类引用*/
	private Board board = null;
	/*棋手引用*/
	private Player players[] ;
	/*Agent引用*/
	private BoardAgent agents[] ;
	/*棋手面板引用*/
	private PlayerComp playerComps[] = new PlayerComp[2];
	/*消息面板引用*/
	private MsgComp msgComp = null;
	/*时间面板引用*/
	private TimerComp timer = null;			//现在并未使用，待以后升级使用
	
	/*是否开局标志*/
	private boolean startedChess = false;
	
	Scanner sin = new Scanner(System.in);
	
	/**
	 * 继承而来的构造函数
	 * @param arg0	父容器
	 * @param arg1      类型
	 */
	public CtrlComp() {
		this.setLayout(null);
		this.setBackground(new Color(208,217,217));
		
		startChessBt = new JButton();
		startChessBt.setBounds(62, 5, 80, 20);
		startChessBt.setText("开局");
		this.add(startChessBt);
		
		whoIsFirstCom = new JComboBox();
		whoIsFirstCom.setBounds(213, 5, 80, 20);
		whoIsFirstCom.setToolTipText("猜 先 :确定哪方先走");
		whoIsFirstCom.addItem("黑  方");
		whoIsFirstCom.addItem("白  方");
		this.add(whoIsFirstCom);
		
		exitBt = new JButton();
		exitBt.setBounds(389, 5, 80, 20);
		exitBt.setText("退  出");
		this.add(exitBt);
		
		addListener();
	}
	public static void main(String[] args) {
		JFrame jf = new JFrame();
		jf.setLayout(null);
		CtrlComp mc = new CtrlComp();
		mc.setBounds(0, 0,280, 165);
		jf.setSize(800, 800);
		jf.getContentPane().add(mc);
		jf.setVisible(true);
	}
	
	public Board getBoard() {
		return board;
	}
	public JComboBox getWhoIsFirstCom(){
		return whoIsFirstCom;
	}
	/**
	 * 走子过程
	 * 每次重画棋盘后，都调用该方法
	 */
	public void draw() {
		if (!startedChess)	//未开始
			return;
		if (gameOver)		//已结束
			return;
		if (curTimeBTS >= timeBetweenTwoSteps) {// 超时
			if (newStep) {
				curs = mw.board.getStatus();

				int resu = agents[turnCnt % 2].hasWinner(curs);
				if(players[turnCnt%2].getSteps()>90)
					resu = 2;
				if (resu != 0) {// 0为未结束，不等于0表示，已经结束。1为黑，-1为白胜，2为平
					gameOver = true;
					startedChess = false;
					if (resu != 2) {// -1或+1
						JOptionPane.showMessageDialog(null,
								playerComps[(turnCnt + 1) % 2].getPlayerName()
										+ "赢了！");
					} else if (resu == 2) {
						JOptionPane.showMessageDialog(null, "平局！");
					}
					endChess();
					return;
				}// end resu！=0

				newStep = false;
				turnCnt = turnCnt % 2;
				players[turnCnt].setSteps(players[turnCnt].getSteps() + 1);// 计算步数
				long start = System.currentTimeMillis();
				
				curm = players[turnCnt].next(setStatus(curs, turnCnt == 1));
				players[turnCnt].setLastMove( curm );
				long end = System.currentTimeMillis();
				playerComps[turnCnt].addTime((int) ((end - start)));

				// System.out.println(playerComps[turnCnt].getTotalTime());

				curm.modifyChessMove(turnCnt == 1, curs[0].length,curs.length);

				String msg = "";
				if ( curm.type.equals("六子棋") ) {
					msg = playerComps[turnCnt].getPlayerName() + "第"
							+ players[turnCnt].getSteps() + "步" + " : " + "("
							+ curm.getStartPoint().getX() + ","
							+ curm.getStartPoint().getY() + ") " + "("
							+ curm.getEndPoint().getX() + ","
							+ curm.getEndPoint().getY() + ")";
				} else {
					msg = playerComps[turnCnt].getPlayerName() + "第"
							+ players[turnCnt].getSteps() + "步" + " : " + "("
							+ curm.getStartPoint().getX() + ","
							+ curm.getStartPoint().getY() + ") --> " + "("
							+ curm.getEndPoint().getX() + ","
							+ curm.getEndPoint().getY() + ")";
				}

				mw.msgComp.println("*************************");
				mw.msgComp.println(msg);
			} 
			else // 错误走步，或超时走步
				
			if (curTimeRAS >= timeResultAfterStep) {
				curTimeBTS = 0;
				curTimeRAS = 0;
				newStep = true;

				if (agents[turnCnt].check(curs, curm, turnCnt == 1 ? Constant.WHITE_PIECE : Constant.BLACK_PIECE)) {// 合法走步

					ArrayList<MoveResp> list = agents[turnCnt].getMoveResps(
							curs, curm, turnCnt == 1 ? 1 : -1);
					for (int i = 0; i < list.size(); i++) {
						MoveResp mr = list.get(i);
						if (mr.getAction() == MoveResp.ACTION_DISPEAR)
							mw.board.dispearChessAtPoint(mr.getPoint().getX(),mr.getPoint().getY(), 0);
						else
							mw.board.drawChessAtPoint(mr.getPoint().getX(), mr.getPoint().getY(), turnCnt == 1 ? Constant.WHITE_PIECE : Constant.BLACK_PIECE);
					}
				} else {// 不合法走步
					
					String moveStr = "(" + curm.getStartPoint().getX() + ","
							+ curm.getStartPoint().getY() + ")-->("
							+ curm.getEndPoint().getX() + ","
							+ curm.getEndPoint().getY() + ")";
					JOptionPane.showMessageDialog(null, "该步  " + moveStr
							+ " 不合法，请确认！");
					System.exit(-1);
				}
				turnCnt++;
			} else {
				curTimeRAS++;
			}
		} else
			curTimeBTS++;
	}
	
    public int[][] setStatus(int s[][], boolean flag){
		int xLen = s.length;
		int yLen = s[0].length;
		
		int[][]status = new int[xLen][yLen];
		if(flag){
			for(int x=0;x<xLen;x++)
				for(int y=0;y<yLen;y++)
					status[x][y] = s[x][y];
		}else{
			for(int x=xLen;x>0;x--)
				for(int y=yLen;y>0;y--)
					status[xLen - x][yLen - y] = s[x-1][y-1];
		}
		return status;
	}
	
	/**
	 * 添加各种监听事件
	 */
	private void addListener(){
		startChessBt.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				JButton  jbt = (JButton)e.getSource();
				if(jbt.getText().equals("开局") ||jbt.getText().equals("继续")){
					if(Constant.curStatus != Constant.SysStatus.running){
						
						players[0] = mw.pya;
						players[1] = mw.pyb;
						
						agents[0] = mw.BoardAgentA;
						agents[1] = mw.BoardAgentB;
						
						if(mw.board == null){
							JOptionPane.showMessageDialog(null, "棋局配置错误");
							return;
						}
						if(players[0] == null || players[1] == null){
							JOptionPane.showMessageDialog(null, "棋手mainCalss配置错误，无法得到player实例");
							return;
						}	
						whoIsFirstCom.disable();
						startEvent();
						jbt.setText("暂停");
					}
				}else if(jbt.getText().equals("暂停")){
					pauseEvent();
					jbt.setText("继续");
				}
				
			}
		});
		
		exitBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	
	/**
	 * 所有棋盘布局归零，完成初始化工作
	 */
	public void startChess(){
		mw.board.init();
		mw.msgComp.clear();
		
		curTimeBTS = 0;
		curTimeRAS = 0;
		// 正式开始下棋的开关
		startedChess = true;
		gameOver = false;
		
		turnCnt = whoIsFirstCom.getSelectedIndex();
		
	}
	
	//==========================================================
	//==========================================================
	MainWindow mw = null;
	
	public CtrlComp(MainWindow mw){
		this();
		this.mw = mw;
		this.playerComps = new PlayerComp[]{mw.playerCompA,mw.playerCompB};
		this.players = new Player[]{mw.pya,mw.pyb};
		this.agents = new BoardAgent[]{mw.BoardAgentA,mw.BoardAgentB};
		
		new Clock().start();
	}
	
	/**
	 * 检查棋局与棋手是否匹配
	 * @return
	 */
	private boolean checkParam(){
		if(mw.board instanceof TwoBeatOneBoard){
			if(/*mw.pya instanceof TwoBeatOnePlayer && mw.pyb instanceof TwoBeatOnePlayer &&*/
					mw.BoardAgentA instanceof TwoBeatOneBoardAgent 
					      && mw.BoardAgentB instanceof TwoBeatOneBoardAgent){
				return true;
			}else{
				JOptionPane.showMessageDialog(null, "棋手与棋局不匹配，请确认！");
				return false;
			}
		}
		if(mw.board instanceof ConnectSixBoard){
			if(/*mw.pya instanceof RenJuPlayer && mw.pyb instanceof RenJuPlayer && */
					mw.BoardAgentA instanceof ConnectSixBoardAgent
					      && mw.BoardAgentB instanceof ConnectSixBoardAgent ){
				return true;
			}
			else{
				JOptionPane.showMessageDialog(null, "棋手与棋局不匹配，请确认！");
				return false;
			}
		}
		
		return true;
	}
	
	public void endChess(){
		whoIsFirstCom.enable();
		Constant.curStatus = Constant.SysStatus.notStarted;
		mw.mtStartOrEnd.setText("运行");
		startChessBt.setText("开局");
	}

	public boolean startEvent(){
		if(checkParam())
		
			if(Constant.SysStatus.notStarted == Constant.curStatus){
				startChess();
				Constant.curStatus = Constant.SysStatus.running;
			}else{
				Constant.curStatus = Constant.SysStatus.running;
			}
		return true;
	}
	
	/**交替下棋*/
	public void alternatelyStep(){
		do{
			draw();			
		}while(true);
	}
		
	public void pauseEvent(){
		Constant.curStatus = Constant.SysStatus.pause;
	}
	
	public boolean restartEvent(){
		endChess();
		
		return true;
	}
	
	/**
	 * 内部时钟类
	 * @author Administrator
	 */
	class Clock extends Thread{
		public void run(){
			while(true){
				try {
					Thread.sleep(Constant.clockSleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				if(Constant.curStatus == Constant.SysStatus.running){
					mw.repaint();
				}
				
			}
		}
	}
	
	

}
