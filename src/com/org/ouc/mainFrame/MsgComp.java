package com.org.ouc.mainFrame;




import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class MsgComp extends JScrollPane{	
	/*��Ϣ��ʾ���*/
	private JTextArea msgText = null;
	
	/**
	 * �̳ж����Ĺ��캯��
	 * @param arg0	������
	 * @param arg1      ����
	 */
	public MsgComp(JTextArea msgText) {
		super(msgText);

		this.msgText = msgText;
		this.msgText.setEditable(false);
	}

	/**
	 * ��Ӳ����س�����Ϣ
	 * @param msg	��Ϣ
	 */
	public void print(String msg){
		msgText.append(msg);
	}
	/**
	 * ��Ӵ��س�����Ϣ
	 * @param msg	��Ϣ
	 */
	public void println(String msg){
		print(msg);
		print("\n");
	}
	/**
	 * �����Ϣ
	 */
	public void clear(){
		msgText.setText("");
	}
	
	
	
	//����
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
