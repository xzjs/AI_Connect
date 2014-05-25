package com.org.ouc.ConnectSix;

import java.awt.event.MouseEvent;
import java.util.ArrayList;


import com.org.ouc.TwoBeatOne.MoveResp;
import com.org.ouc.mainFrame.Constant;
import com.org.ouc.platform.Board;
import com.org.ouc.platform.BoardAgent;
import com.org.ouc.platform.Move;
import com.org.ouc.platform.Position;

public class ConnectSixBoardAgent extends BoardAgent{

	@Override
	public boolean isLegal( Move move ) {
		
		Position firstPos = move.getStartPoint();
		Position secondPos = move.getEndPoint();
		
		// �ж������Ƿ�Ϸ�
		if(( firstPos.getX()>=0 && firstPos.getX() <= Board.XLen-1) &&
				(firstPos.getY()>=0 && firstPos.getY() <= Board.YLen-1)){
			if( ( secondPos.getX()>=0 && secondPos.getX() <= Board.XLen-1) &&
					(secondPos.getY()>=0 && secondPos.getY() <= Board.YLen-1) )
				return true;
		}
		return false;
	}

	@Override
	public int hasWinner(int[][] status) {
		boolean flag = false; // �Ƿ�ƽ�֣�����������δ��ʤ��
		//����  ͬ���������߸�1�����ַ�ʤ��������ͬ���������߸�-1�����ַ�ʤ����
		for(int i=0;i< Board.YLen;i++)
			// 14, 15, 16 ,17,18
			for(int j=0;j< Board.XLen-5;j++){
				if(status[i][j]+status[i][j+1]+status[i][j+2]+status[i][j+3]+status[i][j+4] + status[i][j+5]>= 6)
				{
					return Constant.WHITE_WIN;
				}
				if(status[i][j]+status[i][j+1]+status[i][j+2]+status[i][j+3]+status[i][j+4] +status[i][j+5]<= -6)
				{
					return Constant.BALCK_WIN;
				}
			}
		
		//����  ͬ��������1�����ַ�ʤ����ͬ��������-1�����ַ�ʤ����
		for(int j=0;j< Board.XLen;j++)
			for(int i=0;i< Board.YLen-5;i++){
				if(status[i][j]+status[i+1][j]+status[i+2][j]+status[i+3][j]+status[i+4][j] + status[i+5][j]>= 6)
				{
					return Constant.WHITE_WIN;
				}
				if(status[i][j]+status[i+1][j]+status[i+2][j]+status[i+3][j]+status[i+4][j] + status[i+5][j]<= -6)
				{
					return Constant.BALCK_WIN;
				}
			}
		
		//��б��  ������1�����ַ�ʤ����������-1�����ַ�ʤ����
		for(int j=0;j< Board.XLen-5;j++)
			for(int i=0;i< Board.YLen-5;i++){
				if(status[i][j]+status[i+1][j+1]+status[i+2][j+2]+status[i+3][j+3]+status[i+4][j+4] + status[i+5][j+5]>= 6)
				{
					return Constant.WHITE_WIN;
				}
				if(status[i][j]+status[i+1][j+1]+status[i+2][j+2]+status[i+3][j+3]+status[i+4][j+4] + status[i+5][j+5]<= -6)
				{
					return Constant.BALCK_WIN;
				}
			}
		
		//��б��  ������1�����ַ�ʤ����������-1�����ַ�ʤ����
		for(int i=5;i< Board.YLen;i++)
			for(int j=0;j< Board.XLen-5;j++){
				if(status[i][j]+status[i-1][j+1]+status[i-2][j+2]+status[i-3][j+3]+status[i-4][j+4] + status[i-5][j+4]== 6)
				{
					return Constant.WHITE_WIN;
				}
				if(status[i][j]+status[i-1][j+1]+status[i-2][j+2]+status[i-3][j+3]+status[i-4][j+4] + status[i-5][j+5] == -6)
				{
					return Constant.BALCK_WIN;
				}
			}
		for(int i=0;i< Board.XLen;i++)
			for(int j=0;j< Board.YLen;j++){
				if(status[i][j] == 0){
					flag = true;
					break;
				}
			}
		if(flag){ // ��δ��������δ��ʤ��
			return Constant.NOT_OVER;
		}else{  // ƽ��
			return Constant.DRAW;
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void listen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStatus(Move move) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean check(int[][] status, Move move, int roleType) {
		//����˳�����Ƿ�Ϸ���˳���ܱ仯��������ܻᱨ��
		if(!isLegal(move) || status[move.getStartPoint().getX()][move.getStartPoint().getY()] != 0 || status[move.getEndPoint().getX()][move.getEndPoint().getY()] != 0){
			System.out.println("is unLegal Point");
			return false;
		}
		return true;
	}

	@Override
	/**
	 * һϵ�������ƶ�����
	 */
	public ArrayList<MoveResp> getMoveResps(int[][] status, Move move,
			int roleType) {
		ArrayList<MoveResp> mrList = new ArrayList<MoveResp>();
		
		mrList.add(new MoveResp(move.getStartPoint(), MoveResp.ACTION_DRAW));
		
		if( !move.getStartPoint().isSamePoint( move.getEndPoint() ) )
			mrList.add(new MoveResp(move.getEndPoint(), MoveResp.ACTION_DRAW));
		

		return mrList;
	}

}
