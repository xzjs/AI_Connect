package com.org.ouc.mainFrame;

import java.awt.Color;
import java.util.Timer;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimerComp extends JPanel{
	/*��ʱ����x�᳤��*/
	private final int xLen = 180;
	/*��ʱ����y�᳤��*/
	private final int yLen = 30;
	
	/*��ʱ��Label*/
	private JLabel timeInfoLabel = null;
	/*��ʱ����ʾֵ*/
	private int time = 0;
	
	/*��ǰ�����Ƿ���˼��*/
	private boolean isThinking = false;
	/*��ʱ��*/
	private Timer timer = null;
	
	private boolean stopTimer = false;
	
	/**
	 * �̳ж����Ĺ��캯��
	 * @param arg0	������
	 * @param arg1      ����
	 */
	public TimerComp() {
		timeInfoLabel = new JLabel();
		
		timeInfoLabel.setToolTipText("��ʱ��");
		timeInfoLabel.setBounds(0,0,xLen,yLen);
		
		setBackground(new Color(189,213,213));
		this.add(timeInfoLabel);
	}
	
	/*SWT�Ķ�ʱ��*/
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
	 * ����ʱ��������Labe����
	 * @param text
	 */
	public void setText(String text){
		timeInfoLabel.setText(text);
	}
	
	/**
	 * ��ʽ��ʱ���﷽ʽ
	 * @param 	num ʱ�䣬��λ����
	 * @return  �ַ�������ʽ��hh:mm:ss
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
	 * ��ʼ��ʱ
	 */
	public void startThinking(){
		this.isThinking = true;
	}
	/**
	 * ֹͣ��ʱ
	 */
	public void stopThinking(){
		this.isThinking = false;
	}
	/**
	 * �õ�ʱ��ֵ����λ����
	 * ����startThinking֮��stopThinking֮ǰ����
	 */
	public int getTime(){
		return this.time;
	}

	
	//����
	public static void main(String args[]){}

}
