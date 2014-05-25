package com.org.ouc.mainFrame;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class PlayerInfoPopmenu extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainWindow mw = null;
	private boolean isBlack;
	
	JTextField  playerNameT;
	JTextField  playerImgT;
	JTextField  jarPathT;
	JTextField  mainClassNameT;
	 
	
	public PlayerInfoPopmenu(MainWindow mw,String title,boolean flag) {
		super(null,title,Dialog.ModalityType.APPLICATION_MODAL);
		
		this.mw = mw;
		this.setLayout(null);
		
		this.isBlack = flag;

		final JLabel playerNameL = new JLabel("    棋手姓名         ：");
		playerNameL.setBounds(5, 5, 110, 20);
		playerNameT = new JTextField();
		playerNameT.setBounds(120, 5, 250 , 20);
		
		final JLabel playerImgL = new JLabel("    棋手头像         ：");
		playerImgL.setBounds(5, 35, 110, 20);
		playerImgT = new JTextField();
		playerImgT.setEditable(false);
		playerImgT.setBounds(120, 35, 250 , 20);
		final JButton playerImgBt = new JButton("选择");
		playerImgBt.setBounds(380, 35,60, 20);
		playerImgBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 JFileChooser chooser = new JFileChooser();
				    int returnVal = chooser.showOpenDialog(null);
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				       playerImgT.setText(chooser.getSelectedFile().getAbsolutePath());
				    }
			}
		});
		
		final JLabel jarPathL = new JLabel("选择jar文件路径：");
		jarPathL.setBounds(5, 65, 110, 20);
		jarPathT = new JTextField();
		jarPathT.setEditable(false);
		jarPathT.setBounds(120, 65, 250, 20);
		final JButton jarPathBt = new JButton("选择");
		jarPathBt.setBounds(380, 65,60, 20);
		jarPathBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 JFileChooser chooser = new JFileChooser();
				    int returnVal = chooser.showOpenDialog(null);
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				       jarPathT.setText(chooser.getSelectedFile().getAbsolutePath());
				    }
			}
		});
		
		final JLabel mainClassNameL = new JLabel("mainClass  文件：");
		mainClassNameL.setBounds(5, 90, 110, 20);
		mainClassNameT = new JTextField();
		mainClassNameT.setBounds(120, 90, 250 , 20);
		
		JButton okBt = new JButton("  确   定    ");
		okBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				configPlayer();
			}
		});
		okBt.setBounds(75, 125, 100, 30);
		JButton cancelBt = new JButton("  取   消    ");
		cancelBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				destory();
			}
		});
		cancelBt.setBounds(250, 125, 100, 30);
		
		this.add(playerNameL);
		this.add(playerNameT);
		
		this.add(playerImgL);
		this.add(playerImgT);
		this.add(playerImgBt);
		
		this.add(jarPathL);
		this.add(jarPathT);
		this.add(jarPathBt);
		
		this.add(mainClassNameL);
		this.add(mainClassNameT);
	
		this.add(okBt);
		this.add(cancelBt);
		
		if(isBlack){
			playerNameT.setText(Constant.playerAName);
			playerImgT.setText(Constant.playerAImg);
			jarPathT.setText(Constant.jarAPath);
			mainClassNameT.setText(Constant.playerAMainClass);
		}else{
			playerNameT.setText(Constant.playerBName);
			playerImgT.setText(Constant.playerBImg);
			jarPathT.setText(Constant.jarBPath);
			mainClassNameT.setText(Constant.playerBMainClass);
		}
		
		this.setBounds(300,300,450, 200);
		this.setVisible(true);
	}
	
	/**
	 * 点击确定按钮的动作函数
	 */
	private void configPlayer(){
		String pn = playerNameT.getText().trim();
		String pImg = playerImgT.getText();
		String jarPath = jarPathT.getText();
		String mcName = mainClassNameT.getText().trim();
		
		if(pn.equals("") || pImg.equals("") 
		   || jarPath.equals("") || mcName.equals("")){
			JOptionPane.showMessageDialog(null, "信息不完整，请重新填写！");
			return;
		}
		
		if(isBlack){
			Constant.playerAName = pn;
			Constant.playerAImg = pImg;
			Constant.jarAPath = jarPath;
			Constant.playerAMainClass = mcName;
		}else{
			Constant.playerBName = pn;
			Constant.playerBImg = pImg;
			Constant.jarBPath = jarPath;
			Constant.playerBMainClass = mcName;
		}
		
		mw.configPlayer(isBlack);
		destory();
	}

	private void destory(){
		super.dispose();
	}
	
	public static void main(String args[]){
		new PlayerInfoPopmenu(null,"配置棋手一",true);
	}
	
}
