package com.org.ouc.platform;

import java.util.ArrayList;

import com.org.ouc.TwoBeatOne.MoveResp;

public interface RuleInterface {
	
	public boolean isLegal(Move move);		// �Ƿ�Ϸ��߲�
	/**
	 * �ж��Ƿ������������winner
	 * 1���׷�
	 * 0��δ����
	 * -1���ڷ�
	 * @return
	 */
	public int hasWinner(int[][] status);	// �Ƿ���ʤ��{0 Ŀǰ��ʤ��, -1 ���ַ�ʤ��, 1���ַ�ʤ��}
	/**
	 * ��֤��status����£���־ΪroleType�����ֵ��߷�move�Ƿ����
	 * @param status	��ǰ���
	 * @param move		��ǰ�߷�
	 * @param roleType	��ǰ���ֽ�ɫ����׷���1�����ڷ���-1��
	 * @return			��֤�����true-���У�����false
	 */
	boolean check(int[][] status,Move move,int roleType);
	/**
	 * status����£���־ΪroleType�����ֵ��߷�move��������������Ӧ
	 * ���øú���ǰ���ȵ���check������֤��������ܻ����
	 * @param status 	��ǰ���
	 * @param move		��ǰ�߷�
	 * @param roleType	��ǰ���ֽ�ɫ����׷���1�����ڷ���-1��
	 * @return			������Ӧ��
	 */
	ArrayList<MoveResp> getMoveResps(int[][] status,Move move,int roleType);
	
	
}
