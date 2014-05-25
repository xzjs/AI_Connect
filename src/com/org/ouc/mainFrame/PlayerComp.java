package com.org.ouc.mainFrame;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * 
 * @author Yanghui
 * 用来显示棋手信息的组件
 *
 */
public class PlayerComp extends JPanel{
	
	/*容器的长度*/
	private final int length = 200;
	/*容器的宽度*/
	private final int width = 165;
	
	/*player图像Label*/
	private JLabel imgLabel = null;
	private ImageIcon img = null;
	
	/*player类型Label：黑方 or 白方 or 红方（中国象棋）*/
	private JLabel playerTypeL = null;
	/*player姓名Label*/
	private JLabel playerNameL = null;
	/*player总用时Label*/
	private JLabel totalTimeL = null;

	private ImageIcon imgDynamic = null;

	/*player类型：黑方(-1) or 白方 (1)or 红方（1:中国象棋）,未定（0）*/
	private int  roleType = 0;
	/*player姓名*/
	private String playerName = "未知";
	/*player总用时*/
	private int totalTime = 0;
	
	/*每步所允许的最长思考时间，由控制器确定*/
	private int maxTimeForOneStep = 0;
	
	/*时钟Label，这里用来表明应该哪位棋手走步
	 *时钟动时，为该棋手，否则静止
	 */
	private JLabel actionLabel = null;

	/**
	 * 继承而来的构造函数
	 * @param arg0	父容器
	 * @param arg1      类型
	 */
	public PlayerComp() {
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);
		
		/**
		 * 设置player的图像，若imgPath为空，则使用默认图像
		 * 该图像的大小不应超过160×160
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
	 * 设置player的角色
	 * @param roleType		整型角色代号
	 * @param roleTypeName	角色名称
	 */
	public void setRoleType(int roleType,String roleTypeName){
		this.roleType = roleType;
		this.playerTypeL.setText("角色："+roleTypeName);
	}
	
	/**
	 * 得到player的角色定义
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
	 * 设置player的名字
	 * @param name	player的名字
	 */
	public void setPlayerName(String name){
		this.playerName = name;
		playerNameL.setText("大名："+name);
	}
	/**
	 * 得到player的名字
	 * @return	player的名字
	 */
	public String getPlayerName(){
		return this.playerName;
	}
	
	
	/**
	 * 设置player的总用时
	 * @param time player的总用时，单位：秒
	 */
	public void setTotalTime(int time){
		this.totalTime = time;
		totalTimeL.setText("T:"+time+" MS");
	}
	/**
	 * 得到player的总用时
	 * @return 	player的总用时，单位：秒
	 */
	public int getTotalTime(){
		return this.totalTime;
	}
	/**
	 * 在totalTime的基础上加上当前耗时
	 * @param adder  新的时耗
	 */
	public void addTime(int adder){
		this.totalTime += adder;
		setTotalTime(totalTime);
	}
	
	//测试
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
