package com.org.ouc.mainFrame;




import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class MsgComp extends JScrollPane{	
	/*消息显示面板*/
	private JTextArea msgText = null;
	
	/**
	 * 继承而来的构造函数
	 * @param arg0	父容器
	 * @param arg1      类型
	 */
	public MsgComp(JTextArea msgText) {
		super(msgText);

		this.msgText = msgText;
		this.msgText.setEditable(false);
	}

	/**
	 * 添加不带回车的消息
	 * @param msg	消息
	 */
	public void print(String msg){
		msgText.append(msg);
	}
	/**
	 * 添加带回车的消息
	 * @param msg	消息
	 */
	public void println(String msg){
		print(msg);
		print("\n");
	}
	/**
	 * 清空消息
	 */
	public void clear(){
		msgText.setText("");
	}
	
	
	
	//测试
	public static void main(String args[]){
		JFrame jf = new JFrame();
		jf.setLayout(null);
		JScrollPane mc = new MsgComp(new JTextArea());
		mc.setBounds(0, 0,280, 413);
		jf.setSize(800, 800);
		jf.getContentPane().add(mc);
		jf.setVisible(true);
	}
	
}
