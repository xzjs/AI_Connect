package com.org.ouc.mainFrame;

/**
 * 
 * ��������ƽ̨�еĳ���
 * 
 *
 */
public class Constant {

	public static final String CBOARD_TWO_BEAT_ONE = "TwoBeatOne";
	
	/*public static final String CBOARD_REN_JU = "RenJu";*/
	//�Ͼ��滻Ϊ
	public static final String CBOARD_CONNECT_SIX = "ConnectSix";
	
	public static enum SysStatus{notStarted,running,pause};
	
	//ϵͳ����
	public static String curChessBoard = "";
	public static SysStatus curStatus = Constant.SysStatus.notStarted;
	
	
	/*����Ĭ�ϵĸ���ͼ��*/
	public static final String DEFAULT_IMGPATH = "images/defaultA.jpg";
	/*��̬ʱ��ͼ��*/
	public static final String STATIC_CLOCK_IMGPATH = "E:\\b308\\eclipseWorkplace\\ChessPlatform\\sclock.gif";
	/*��̬ʱ��ͼ��*/
	public static final String DYMINIC_CLOCK_IMGPATH = "E:\\b308\\eclipseWorkplace\\ChessPlatform\\dclock.gif";
	
	public static final int BLACK_PIECE = -1;
	public static final int WHITE_PIECE = 1;
	//������������ֽ�ɫ
	public static final int ROLE_BALCK = -1;	//�ڷ�
	public static final int ROLE_WHITE = 1;		//�׷�
	public static final int ROLE_RED = 1;		//�췽
	
	//����ʤ��
	public static final int BALCK_WIN = -1;	//�ڷ�
	public static final int WHITE_WIN = 1;	//�׷�
	public static final int DRAW = 2; // ƽ��
	public static final int NOT_OVER = 0; // δ����
	
	//�ڷ���������
	public static String playerAName = "";
	public static String playerAImg = "";
	public static String jarAPath = "";
	public static String playerAMainClass = "";

	//�ף��죩����������
	public static String playerBName = "";
	public static String jarBPath = "";
	public static String playerBImg = "";
	public static String playerBMainClass = "";
	
	//����Ĭ�ϵ�ͷ��
	public static String playerDefaultImg = "";
	
	
	//�ڡ������ӣ������ڶ���һ�������塢Χ��
	public static final int CHESS_BLACK = -1;
	public static final int CHESS_BLANK = 0;
	public static final int CHESS_WHITE = 1;
	
	
	//����һ�����̳�����
	public static final int TWO_BEAT_ONE_BOARD_XLEN = 5;
	public static final int TWO_BEAT_ONE_BOARD_YLEN = 5;

	
	public static String playerA = "com.yh.player.TwoBeatOnePlayer";
	public static String playerB = "com.yh.player.TwoBeatOnePlayer";
	
	//��ʱ���ñ���
	public static int startNum = 0;
	public static int timeNum = 0;
	
	public static int clockSleep = 50;
	
}
