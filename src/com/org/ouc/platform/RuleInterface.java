package com.org.ouc.platform;

import java.util.ArrayList;

import com.org.ouc.TwoBeatOne.MoveResp;

public interface RuleInterface {
	
	public boolean isLegal(Move move);		// 是否合法走步
	/**
	 * 判定是否结束，即产生winner
	 * 1：白方
	 * 0：未结束
	 * -1：黑方
	 * @return
	 */
	public int hasWinner(int[][] status);	// 是否有胜者{0 目前无胜者, -1 先手方胜利, 1后手方胜利}
	/**
	 * 验证在status棋局下，标志为roleType的棋手的走法move是否可行
	 * @param status	当前棋局
	 * @param move		当前走法
	 * @param roleType	当前棋手角色，如白方（1），黑方（-1）
	 * @return			验证结果，true-可行，否则false
	 */
	boolean check(int[][] status,Move move,int roleType);
	/**
	 * status棋局下，标志为roleType的棋手的走法move所产生的棋盘响应
	 * 调用该函数前需先调用check加以验证，否则可能会出错
	 * @param status 	当前棋局
	 * @param move		当前走法
	 * @param roleType	当前棋手角色，如白方（1），黑方（-1）
	 * @return			棋盘响应集
	 */
	ArrayList<MoveResp> getMoveResps(int[][] status,Move move,int roleType);
	
	
}
