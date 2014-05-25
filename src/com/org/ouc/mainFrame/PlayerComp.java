package com.org.ouc.mainFrame;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * 
 * @author Yanghui
 * ������ʾ������Ϣ�����
 *
 */
public class PlayerComp extends JPanel{
	
	/*�����ĳ���*/
	private final int length = 200;
	/*�����Ŀ��*/
	private final int width = 165;
	
	/*playerͼ��Label*/
	private JLabel imgLabel = null;
	private ImageIcon img = null;
	
	/*player����Label���ڷ� or �׷� or �췽���й����壩*/
	private JLabel playerTypeL = null;
	/*player����Label*/
	private JLabel playerNameL = null;
	/*player����ʱLabel*/
	private JLabel totalTimeL = null;

	private ImageIcon imgDynamic = null;

	/*player���ͣ��ڷ�(-1) or �׷� (1)or �췽��1:�й����壩,δ����0��*/
	private int  roleType = 0;
	/*player����*/
	private String playerName = "δ֪";
	/*player����ʱ*/
	private int totalTime = 0;
	
	/*ÿ����������˼��ʱ�䣬�ɿ�����ȷ��*/
	private int maxTimeForOneStep = 0;
	
	/*ʱ��Label��������������Ӧ����λ�����߲�
	 *ʱ�Ӷ�ʱ��Ϊ�����֣�����ֹ
	 */
	private JLabel actionLabel = null;

	/**
	 * �̳ж����Ĺ��캯��
	 * @param arg0	������
	 * @param arg1      ����
	 */
	public PlayerComp() {
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);
		
		/**
		 * ����player��ͼ����imgPathΪ�գ���ʹ��Ĭ��ͼ��
		 * ��ͼ��Ĵ�С��Ӧ����160��160
		 */
		img = new ImageIcon(ClassLoader.getSystemResource(Constant.DEFAULT_IMGPATH));
		imgDynamic = new ImageIcon("comeon.gif");
		
		actionLabel = new JLabel(imgDynamic);
		actionLabel.setBounds(118, 95, 80, 67);	
		this.add(actionLabel);
		
		
		playerTypeL = new JLabel();
		playerTypeL.setBounds(120, 3, 80, 25);
		this.add(playerTypeL);
		
		playerNameL =new JLabel();
		playerNameL.setBounds(120, 34, 80, 25);
		this.add(playerNameL);

		totalTimeL = new JLabel();
		totalTimeL.setBounds(120, 66, 80, 25);
		this.add(totalTimeL);
		setTotalTime(0);
		
		imgLabel = new JLabel(img);
		imgLabel.setOpaque(true);
		imgLabel.setBackground(new Color(213,217,203));
		imgLabel.setBounds(1,1,110,110);
		this.add(imgLabel);
	}

	/**
	 * ����player�Ľ�ɫ
	 * @param roleType		���ͽ�ɫ����
	 * @param roleTypeName	��ɫ����
	 */
	public void setRoleType(int roleType,String roleTypeName){
		this.roleType = roleType;
		this.playerTypeL.setText("��ɫ��"+roleTypeName);
	}
	
	/**
	 * �õ�player�Ľ�ɫ����
	 * @return
	 */
	public int getRoleType(){
		return this.roleType;
	}
	
	public void setImg(String imgPath){
		if(imgPath.equals(""))
			return;
		
		img = new ImageIcon(imgPath);
		
		if(img != null)
			imgLabel.setIcon(img);		
	}
	
	/**
	 * ����player������
	 * @param name	player������
	 */
	public void setPlayerName(String name){
		this.playerName = name;
		playerNameL.setText("������"+name);
	}
	/**
	 * �õ�player������
	 * @return	player������
	 */
	public String getPlayerName(){
		return this.playerName;
	}
	
	
	/**
	 * ����player������ʱ
	 * @param time player������ʱ����λ����
	 */
	public void setTotalTime(int time){
		this.totalTime = time;
		totalTimeL.setText("T:"+time+" MS");
	}
	/**
	 * �õ�player������ʱ
	 * @return 	player������ʱ����λ����
	 */
	public int getTotalTime(){
		return this.totalTime;
	}
	/**
	 * ��totalTime�Ļ����ϼ��ϵ�ǰ��ʱ
	 * @param adder  �µ�ʱ��
	 */
	public void addTime(int adder){
		this.totalTime += adder;
		setTotalTime(totalTime);
	}
	
	//����
	public static void main(String args[]){
		JFrame jf = new JFrame();
		jf.setLayout(null);
		PlayerComp mc = new PlayerComp();
		mc.setBounds(0, 0,280, 165);
		jf.setSize(800, 800);
		jf.getContentPane().add(mc);
		jf.setVisible(true);
	}

}
