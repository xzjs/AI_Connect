package com.org.ouc.mainFrame;

/**
 * 
 * 定义下棋平台中的常量
 * 
 *
 */
public class Constant {

	public static final String CBOARD_TWO_BEAT_ONE = "TwoBeatOne";
	
	/*public static final String CBOARD_REN_JU = "RenJu";*/
	//上句替换为
	public static final String CBOARD_CONNECT_SIX = "ConnectSix";
	
	public static enum SysStatus{notStarted,running,pause};
	
	//系统变量
	public static String curChessBoard = "";
	public static SysStatus curStatus = Constant.SysStatus.notStarted;
	
	
	/*棋手默认的个人图像*/
	public static final String DEFAULT_IMGPATH = "images/defaultA.jpg";
	/*静态时钟图像*/
	public static final String STATIC_CLOCK_IMGPATH = "E:\\b308\\eclipseWorkplace\\ChessPlatform\\sclock.gif";
	/*动态时钟图像*/
	public static final String DYMINIC_CLOCK_IMGPATH = "E:\\b308\\eclipseWorkplace\\ChessPlatform\\dclock.gif";
	
	public static final int BLACK_PIECE = -1;
	public static final int WHITE_PIECE = 1;
	//参与比赛的棋手角色
	public static final int ROLE_BALCK = -1;	//黑方
	public static final int ROLE_WHITE = 1;		//白方
	public static final int ROLE_RED = 1;		//红方
	
	//比赛胜负
	public static final int BALCK_WIN = -1;	//黑方
	public static final int WHITE_WIN = 1;	//白方
	public static final int DRAW = 2; // 平局
	public static final int NOT_OVER = 0; // 未结束
	
	//黑方棋手属性
	public static String playerAName = "";
	public static String playerAImg = "";
	public static String jarAPath = "";
	public static String playerAMainClass = "";

	//白（红）方棋手属性
	public static String playerBName = "";
	public static String jarBPath = "";
	public static String playerBImg = "";
	public static String playerBMainClass = "";
	
	//棋手默认的头像
	public static String playerDefaultImg = "";
	
	
	//黑、白棋子，可用于二打一、五子棋、围棋
	public static final int CHESS_BLACK = -1;
	public static final int CHESS_BLANK = 0;
	public static final int CHESS_WHITE = 1;
	
	
	//二打一的棋盘长、宽
	public static final int TWO_BEAT_ONE_BOARD_XLEN = 5;
	public static final int TWO_BEAT_ONE_BOARD_YLEN = 5;

	
	public static String playerA = "com.yh.player.TwoBeatOnePlayer";
	public static String playerB = "com.yh.player.TwoBeatOnePlayer";
	
	//计时器用变量
	public static int startNum = 0;
	public static int timeNum = 0;
	
	public static int clockSleep = 50;
	
}
