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
	int turnCnt = 0 ; 	// 0: �ڷ��� 1���׷�
	
	/**��ǰ����֮���ʱ��*/
	int curTimeBTS = 0;
	/**��ǰ��ʣ���γ���*/
	int curTimeRAS = 0;

	/**����֮��ʱ������*/
	int timeBetweenTwoSteps = 10;
	/**���Բ�������*/
	int timeResultAfterStep = 10;

	boolean gameOver = false;
	boolean newStep = true;
	
	int[][] curs = null;
	Move curm = null;	//
	/**���ְ�ť*/
	private JButton startChessBt = null;
	/**ȷ��˭������*/
	private JComboBox whoIsFirstCom = null;
	/**�˳�ϵͳ*/
	private JButton exitBt = null;
	
	
	/*���������*/
	private Board board = null;
	/*��������*/
	private Player players[] ;
	/*Agent����*/
	private BoardAgent agents[] ;
	/*�����������*/
	private PlayerComp playerComps[] = new PlayerComp[2];
	/*��Ϣ�������*/
	private MsgComp msgComp = null;
	/*ʱ���������*/
	private TimerComp timer = null;			//���ڲ�δʹ�ã����Ժ�����ʹ��
	
	/*�Ƿ񿪾ֱ�־*/
	private boolean startedChess = false;
	
	Scanner sin = new Scanner(System.in);
	
	/**
	 * �̳ж����Ĺ��캯��
	 * @param arg0	������
	 * @param arg1      ����
	 */
	public CtrlComp() {
		this.setLayout(null);
		this.setBackground(new Color(208,217,217));
		
		startChessBt = new JButton();
		startChessBt.setBounds(62, 5, 80, 20);
		startChessBt.setText("����");
		this.add(startChessBt);
		
		whoIsFirstCom = new JComboBox();
		whoIsFirstCom.setBounds(213, 5, 80, 20);
		whoIsFirstCom.setToolTipText("�� �� :ȷ���ķ�����");
		whoIsFirstCom.addItem("��  ��");
		whoIsFirstCom.addItem("��  ��");
		this.add(whoIsFirstCom);
		
		exitBt = new JButton();
		exitBt.setBounds(389, 5, 80, 20);
		exitBt.setText("��  ��");
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
	 * ���ӹ���
	 * ÿ���ػ����̺󣬶����ø÷���
	 */
	public void draw() {
		if (!startedChess)	//δ��ʼ
			return;
		if (gameOver)		//�ѽ���
			return;
		if (curTimeBTS >= timeBetweenTwoSteps) {// ��ʱ
			if (newStep) {
				curs = mw.board.getStatus();

				int resu = agents[turnCnt % 2].hasWinner(curs);
				if(players[turnCnt%2].getSteps()>90)
					resu = 2;
				if (resu != 0) {// 0Ϊδ������������0��ʾ���Ѿ�������1Ϊ�ڣ�-1Ϊ��ʤ��2Ϊƽ
					gameOver = true;
					startedChess = false;
					if (resu != 2) {// -1��+1
						JOptionPane.showMessageDialog(null,
								playerComps[(turnCnt + 1) % 2].getPlayerName()
										+ "Ӯ�ˣ�");
					} else if (resu == 2) {
						JOptionPane.showMessageDialog(null, "ƽ�֣�");
					}
					endChess();
					return;
				}// end resu��=0

				newStep = false;
				turnCnt = turnCnt % 2;
				players[turnCnt].setSteps(players[turnCnt].getSteps() + 1);// ���㲽��
				long start = System.currentTimeMillis();
				
				curm = players[turnCnt].next(setStatus(curs, turnCnt == 1));
				players[turnCnt].setLastMove( curm );
				long end = System.currentTimeMillis();
				playerComps[turnCnt].addTime((int) ((end - start)));

				// System.out.println(playerComps[turnCnt].getTotalTime());

				curm.modifyChessMove(turnCnt == 1, curs[0].length,curs.length);

				String msg = "";
				if ( curm.type.equals("������") ) {
					msg = playerComps[turnCnt].getPlayerName() + "��"
							+ players[turnCnt].getSteps() + "��" + " : " + "("
							+ curm.getStartPoint().getX() + ","
							+ curm.getStartPoint().getY() + ") " + "("
							+ curm.getEndPoint().getX() + ","
							+ curm.getEndPoint().getY() + ")";
				} else {
					msg = playerComps[turnCnt].getPlayerName() + "��"
							+ players[turnCnt].getSteps() + "��" + " : " + "("
							+ curm.getStartPoint().getX() + ","
							+ curm.getStartPoint().getY() + ") --> " + "("
							+ curm.getEndPoint().getX() + ","
							+ curm.getEndPoint().getY() + ")";
				}

				mw.msgComp.println("*************************");
				mw.msgComp.println(msg);
			} 
			else // �����߲�����ʱ�߲�
				
			if (curTimeRAS >= timeResultAfterStep) {
				curTimeBTS = 0;
				curTimeRAS = 0;
				newStep = true;

				if (agents[turnCnt].check(curs, curm, turnCnt == 1 ? Constant.WHITE_PIECE : Constant.BLACK_PIECE)) {// �Ϸ��߲�

					ArrayList<MoveResp> list = agents[turnCnt].getMoveResps(
							curs, curm, turnCnt == 1 ? 1 : -1);
					for (int i = 0; i < list.size(); i++) {
						MoveResp mr = list.get(i);
						if (mr.getAction() == MoveResp.ACTION_DISPEAR)
							mw.board.dispearChessAtPoint(mr.getPoint().getX(),mr.getPoint().getY(), 0);
						else
							mw.board.drawChessAtPoint(mr.getPoint().getX(), mr.getPoint().getY(), turnCnt == 1 ? Constant.WHITE_PIECE : Constant.BLACK_PIECE);
					}
				} else {// ���Ϸ��߲�
					
					String moveStr = "(" + curm.getStartPoint().getX() + ","
							+ curm.getStartPoint().getY() + ")-->("
							+ curm.getEndPoint().getX() + ","
							+ curm.getEndPoint().getY() + ")";
					JOptionPane.showMessageDialog(null, "�ò�  " + moveStr
							+ " ���Ϸ�����ȷ�ϣ�");
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
	 * ��Ӹ��ּ����¼�
	 */
	private void addListener(){
		startChessBt.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				JButton  jbt = (JButton)e.getSource();
				if(jbt.getText().equals("����") ||jbt.getText().equals("����")){
					if(Constant.curStatus != Constant.SysStatus.running){
						
						players[0] = mw.pya;
						players[1] = mw.pyb;
						
						agents[0] = mw.BoardAgentA;
						agents[1] = mw.BoardAgentB;
						
						if(mw.board == null){
							JOptionPane.showMessageDialog(null, "������ô���");
							return;
						}
						if(players[0] == null || players[1] == null){
							JOptionPane.showMessageDialog(null, "����mainCalss���ô����޷��õ�playerʵ��");
							return;
						}	
						whoIsFirstCom.disable();
						startEvent();
						jbt.setText("��ͣ");
					}
				}else if(jbt.getText().equals("��ͣ")){
					pauseEvent();
					jbt.setText("����");
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
	 * �������̲��ֹ��㣬��ɳ�ʼ������
	 */
	public void startChess(){
		mw.board.init();
		mw.msgComp.clear();
		
		curTimeBTS = 0;
		curTimeRAS = 0;
		// ��ʽ��ʼ����Ŀ���
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
	 * �������������Ƿ�ƥ��
	 * @return
	 */
	private boolean checkParam(){
		if(mw.board instanceof TwoBeatOneBoard){
			if(/*mw.pya instanceof TwoBeatOnePlayer && mw.pyb instanceof TwoBeatOnePlayer &&*/
					mw.BoardAgentA instanceof TwoBeatOneBoardAgent 
					      && mw.BoardAgentB instanceof TwoBeatOneBoardAgent){
				return true;
			}else{
				JOptionPane.showMessageDialog(null, "��������ֲ�ƥ�䣬��ȷ�ϣ�");
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
				JOptionPane.showMessageDialog(null, "��������ֲ�ƥ�䣬��ȷ�ϣ�");
				return false;
			}
		}
		
		return true;
	}
	
	public void endChess(){
		whoIsFirstCom.enable();
		Constant.curStatus = Constant.SysStatus.notStarted;
		mw.mtStartOrEnd.setText("����");
		startChessBt.setText("����");
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
	
	/**��������*/
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
	 * �ڲ�ʱ����
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
