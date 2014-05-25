package com.org.ouc.mainFrame;

import java.awt.Color;
import java.util.Timer;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimerComp extends JPanel{
	/*计时器的x轴长度*/
	private final int xLen = 180;
	/*计时器的y轴长度*/
	private final int yLen = 30;
	
	/*计时器Label*/
	private JLabel timeInfoLabel = null;
	/*计时器显示值*/
	private int time = 0;
	
	/*当前棋手是否在思考*/
	private boolean isThinking = false;
	/*定时器*/
	private Timer timer = null;
	
	private boolean stopTimer = false;
	
	/**
	 * 继承而来的构造函数
	 * @param arg0	父容器
	 * @param arg1      类型
	 */
	public TimerComp() {
		timeInfoLabel = new JLabel();
		
		timeInfoLabel.setToolTipText("计时器");
		timeInfoLabel.setBounds(0,0,xLen,yLen);
		
		setBackground(new Color(189,213,213));
		this.add(timeInfoLabel);
	}
	
	/*SWT的定时器*/
	Runnable runClock = new Runnable() {
		public void run() {
			System.out.println(isThinking+" "+time);
			if(isThinking){
				time ++;
				String timeStr = formateTime(time);
				timeInfoLabel.setText(timeStr);
			}
		}
		
	};
	public Timer getTimer(){
		return timer;
	}
	/**
	 * 设置时间容器的Labe内容
	 * @param text
	 */
	public void setText(String text){
		timeInfoLabel.setText(text);
	}
	
	/**
	 * 格式化时间表达方式
	 * @param 	num 时间，单位：秒
	 * @return  字符串，格式：hh:mm:ss
	 */
	private String formateTime(int num){
		int hour = 0;
		int minute = 0;
		
		StringBuffer sb = new StringBuffer();
		if(num >= 3600){
			hour = num / 3600;
			num = num % 3600;
		}
		if(num >= 60){
			minute = num / 60;
			num = num % 60;
		}
		
		if(hour< 10)
			sb.append("0");
		sb.append(hour);
		sb.append(" : ");
		if(minute < 10)
			sb.append("0");
		sb.append(minute);
		sb.append(" : ");
		if(num < 10)
			sb.append("0");
		sb.append(num);
		
		return sb.toString();
	}

	/**
	 * 开始计时
	 */
	public void startThinking(){
		this.isThinking = true;
	}
	/**
	 * 停止计时
	 */
	public void stopThinking(){
		this.isThinking = false;
	}
	/**
	 * 得到时间值，单位：秒
	 * 须在startThinking之后，stopThinking之前调用
	 */
	public int getTime(){
		return this.time;
	}

	
	//测试
	public static void main(String args[]){}

}
