package com.org.ouc.TwoBeatOne;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.org.ouc.mainFrame.Constant;
import com.org.ouc.platform.BoardAgent;
import com.org.ouc.platform.Move;
import com.org.ouc.platform.Position;

public class TwoBeatOneBoardAgent extends BoardAgent{

	private Move move = new Move();
	
	public TwoBeatOneBoard getBoard() {
		return (TwoBeatOneBoard) super.getBoard();
	}
	public void setBoard(TwoBeatOneBoard board) {
		super.setBoard(board);
	}
	
	@Override
	/**
	 * ���ChessMove�Ƿ�Ϸ�
	 * 1��Ϊ�Ǹ���   2���������Ҫ�� 
	 * @param move	ChessStatusʵ��
	 * @param xLen  ��ֵ�x�������
	 * @param yLen  ��ֵ�y�������
	 * @return		��֤�����true���Ϸ�������false
	 */
	public boolean isLegal(Move move){
		Position sp = move.getStartPoint();
		Position ep = move.getEndPoint();
		
		return  (sp.getX()>=0 && sp.getX() < TwoBeatOneBoard.XLen) && 
				(sp.getY()>=0 && sp.getY() < TwoBeatOneBoard.YLen) &&
				(ep.getX()>=0 && ep.getX() < TwoBeatOneBoard.XLen) && 
				(ep.getY()>=0 && ep.getY() < TwoBeatOneBoard.YLen) ;
	}
	
	/**
	 * ���ò��߷��Ƿ��ǿɵ���ģ�
	 * �ڶ���һ�У�ֻ����һ����λ����
	 * @param move  ChessStatusʵ��
	 * @return      ��֤�����true���Ϸ�������false
	 */
	private boolean isReachable(Move move){
		Position sp = move.getStartPoint();
		Position ep = move.getEndPoint();
		
		return Math.abs(sp.getX() - ep.getX() + sp.getY() - ep.getY()) == 1; 
	}
	
	/**
	 * ����߲��Ƿ�����߼���
	 * 1���ò���ǰһλ����roleType�����
	 * 2���ò��ĺ�һλ��δ��ռ��
	 * @param status����ǰ���
	 * @param move����ǰ����µ�һ���߲�
	 * @param roleType�����߲��Ǻڷ����ǰ׷�
	 * @return
	 */
	private boolean isLogical(int[][] status, Move move, int roleType){	
		Position sp = move.getStartPoint();
		Position ep = move.getEndPoint();
		
		int stat[][] = status; // ��õ�ǰ��ֵľ����ʾ
		
		if(stat[sp.getX()][sp.getY()] != roleType || stat[ep.getX()][ep.getY()] != 0){
			return false;
		}
		
		return true;
		
	}
	
	public boolean check(int[][] status, Move move, int roleType) {
		//����˳�����Ƿ�Ϸ���˳���ܱ仯��������ܻᱨ��
		if(!isLegal(move)){
			System.out.println("isLegal Point");
			return false;
		}
		
		if(!isReachable(move)){
			System.out.println("unReachable");
			return false;
		}
		
		if(!isLogical(status, move, roleType)){
			System.out.println("is not logical");
			return false;
		}
		
		return true;
	}


	public ArrayList<MoveResp> getMoveResps(int[][] status, Move move,
			int roleType) {
		
		ArrayList<MoveResp> mrList = new ArrayList<MoveResp>();
		mrList.add(new MoveResp(move.getStartPoint(), MoveResp.ACTION_DISPEAR));
		mrList.add(new MoveResp(move.getEndPoint(), MoveResp.ACTION_DRAW));
		
		addMoveRespByRule(status, move, roleType, mrList);
		
		return mrList;
	}
	
	/**
	 * ���������������µ�MoveResp
	 * @param mrList
	 */
	private void addMoveRespByRule(int[][] status, Move move,
			int roleType, ArrayList<MoveResp> mrList){

		int xLen = TwoBeatOneBoard.XLen;
		int yLen = TwoBeatOneBoard.YLen;
		
		Position ep = move.getEndPoint();
		
		int stat[][] = status;
		stat[move.getStartPoint().getX()][move.getStartPoint().getY()] = 0;
		
		int x = ep.getX(); int y = ep.getY(); // ��ǰ�߲����߶����ӵ�λ��
		
		int roleRight, roleLeft, roleUp, roleDown; //���ŵ�����ͬ������ҡ�����λ�õ�����
		
		Position pRight = null, pLeft = null; // ���ŵ�����ͬ�������λ��
		
		if (x > 0 && stat[x - 1][y] == roleType) // ��ǰ�߲��������ͬ��
		{	
			// ͬ�������Ƿ���λ��
			if (x - 1 > 0) // ��λ��
			{
				roleLeft = stat[(x - 1) - 1][y];
				if (roleLeft == -roleType) // ͬ�������ǵ���
					pLeft = new Position((x - 1) - 1, y);
			}	
			else roleLeft = 0; // ͬ�����������
			
			// �Լ����ұ��Ƿ���λ��
			if (x < xLen - 1) 
			{
				roleRight = stat[x + 1][y];
				if (roleRight == -roleType) //�Լ����ұ��ǵ���
					pRight = new Position(x + 1, y);
			}
			else roleRight = 0; // �Լ��������ұ�
			
			if (roleLeft * roleRight == 0) // ���ϳ���������ͬ�����ߺ��Լ����ұ�������һ���ǿո�
			{
			
				if (pLeft != null) //ͬ�������е���
					if (pLeft.getX() == 0 || stat[pLeft.getX() - 1][y] == 0) // ��ߵ��ӿ��Ա��Ե�
						mrList.add(new MoveResp(pLeft, MoveResp.ACTION_DISPEAR));
				if (pRight != null) //�Լ����ұ��е���
					if (pRight.getX() == xLen - 1 || stat[pRight.getX() + 1][y] == 0)	// �ұߵ��ӿ��Ա��Ե�
						mrList.add(new MoveResp(pRight, MoveResp.ACTION_DISPEAR));
			}

		} // end if ����ǰ�߲��������ͬ�
		
		pRight = null; pLeft = null; // ��������λ��
		if (x < xLen - 1 && stat[x + 1][y] == roleType)	// ��ǰ�߲����ұ���ͬ��
		{	
			// ͬ����ұ��Ƿ���λ��
			if (x + 1 < xLen - 1)  // �ұ���λ��
			{
				roleRight = stat[(x + 1) + 1][y];
				if (roleRight == -roleType) // ͬ����ұ��ǵ���
					pRight = new Position((x + 1) + 1, y);
			}	
			else roleRight = 0;  //ͬ���������ұ�
			
			// �Լ�������Ƿ���λ��
			if (x > 0) 
			{
				roleLeft = stat[x - 1][y];
				if (roleLeft == -roleType) //�Լ�������ǵ���
					pLeft = new Position(x - 1, y);
			}
			else roleLeft = 0; // �Լ����������
			
			if (roleLeft * roleRight == 0) // ���ϳ����������Լ�����ߺ�ͬ����ұ�������һ���ǿո�
			{
			
				if (pLeft != null) //�Լ�������е���
					if (pLeft.getX() == 0 || stat[pLeft.getX() - 1][y] == 0) // ��ߵ��ӿ��Ա��Ե�
						mrList.add(new MoveResp(pLeft, MoveResp.ACTION_DISPEAR));
				if (pRight != null) //ͬ����ұ��е���
					if (pRight.getX() == xLen - 1 || stat[pRight.getX() + 1][y] == 0)	// �ұߵ��ӿ��Ա��Ե�
						mrList.add(new MoveResp(pRight, MoveResp.ACTION_DISPEAR));
			}

		} // end if ����ǰ�߲����ұ���ͬ�
		
		
		Position pUp = null, pDown = null; // ���ŵ�����ͬ�������λ��
		
		if (y > 0 && stat[x][y - 1] == roleType) // ��ǰ�߲����ϱ���ͬ��
		{	
			// ͬ����ϱ��Ƿ���λ��
			if (y - 1 > 0) // ��λ��
			{
				roleUp = stat[x][(y - 1) - 1];
				if (roleUp == -roleType) // ͬ����ϱ��ǵ���
					pUp = new Position(x, (y - 1) - 1);
			}	
			else roleUp = 0; //ͬ���������ϱ�
			
			// �Լ����±��Ƿ���λ��
			if (y < yLen - 1) 
			{
				roleDown = stat[x][y + 1];
				if (roleDown == -roleType) //�Լ����±��ǵ���
					pDown = new Position(x, y + 1);
			}
			else roleDown = 0; //�Լ��������±�
			
			if (roleUp * roleDown == 0) // ���ϳ���������ͬ����ϱߺ��Լ����±�������һ���ǿո�
			{
			
				if (pUp != null) //ͬ����ϱ��е���
					if (pUp.getY() == 0 || stat[pUp.getX()][pUp.getY() - 1] == 0) // �ϱߵ��ӿ��Ա��Ե�
						mrList.add(new MoveResp(pUp, MoveResp.ACTION_DISPEAR));
				if (pDown != null) //�Լ����±��е���
					if (pDown.getY() == yLen - 1 || stat[pDown.getX()][pDown.getY() + 1] == 0)	// �ұߵ��ӿ��Ա��Ե�
						mrList.add(new MoveResp(pDown, MoveResp.ACTION_DISPEAR));
			}

		} // end if ����ǰ�߲����ϱ���ͬ�
		
		pUp = null; pDown = null; // ��������λ��
		if (y < yLen - 1 && stat[x][y + 1] == roleType)	// ��ǰ�߲����±���ͬ��
		{	
			// ͬ����±��Ƿ���λ��
			if (y + 1 < yLen - 1)  // �±���λ��
			{
				roleDown = stat[x][(y + 1) + 1];
				if (roleDown == -roleType) // ͬ����±��ǵ���
					pDown = new Position(x, (y + 1) + 1);
			}	
			else roleDown = 0;  //ͬ���������±�
			
			// �Լ����ϱ��Ƿ���λ��
			if (y > 0) 
			{
				roleUp = stat[x][y - 1];
				if (roleUp == -roleType) //�Լ����ϱ��ǵ���
					pUp = new Position(x, y - 1);
			}
			else roleUp = 0; //�Լ��������ϱ�
			
			if (roleUp * roleDown == 0) // ���ϳ����������Լ����ϱߺ�ͬ����±�������һ���ǿո�
			{
			
				if (pUp != null) //�Լ����ϱ��е���
					if (pUp.getY() == 0 || stat[pUp.getX()][pUp.getY() - 1] == 0) // �ϱߵ��ӿ��Ա��Ե�
						mrList.add(new MoveResp(pUp, MoveResp.ACTION_DISPEAR));
				if (pDown != null) //ͬ����±��е���
					if (pDown.getY() == yLen - 1 || stat[pDown.getX()][pDown.getY() + 1] == 0)	// �ұߵ��ӿ��Ա��Ե�
						mrList.add(new MoveResp(pDown, MoveResp.ACTION_DISPEAR));
			}

		} // end if ����ǰ�߲����±���ͬ�

	}
	
	@Override
	public int hasWinner(int[][] status) {
		int oneNums =0;
		int negativeOneNums = 0;
		
		int xlen = TwoBeatOneBoard.XLen;
		int ylen = TwoBeatOneBoard.YLen;
		int[][] s = status; 
		
		for(int i=0;i<xlen;i++)
			for(int j=0;j<ylen;j++){
				if(s[i][j] == 1)
					oneNums ++;
				else if(s[i][j] == -1)
					negativeOneNums ++;
			}
		
		if(oneNums <= 1)
			return Constant.WHITE_WIN;
		else if(negativeOneNums <= 1)
			return Constant.BALCK_WIN;
		else
			return Constant.NOT_OVER;
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * �������ʱ����ȡMove��StartPoint
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		this.setMove(null);
		move.setStartPoint(new Position((e.getY()-this.getBoard().getTopMargin())/this.getBoard().getyLenBetweenLines(),(e.getX()-this.getBoard().getLeftMargin())/this.getBoard().getxLenBetweenLines()));
		System.out.print("Position:"+move.getStartPoint().getX()+","+move.getStartPoint().getY()+"---->");
	}
	
	/**
	 * �ͷ�ʱ����ȡMove��EndPoint
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
		move.setEndPoint(new Position((e.getY()-this.getBoard().getTopMargin())/this.getBoard().getyLenBetweenLines(),(e.getX()-this.getBoard().getLeftMargin())/this.getBoard().getxLenBetweenLines()));
		System.out.println("Position:"+move.getEndPoint().getX()+","+move.getEndPoint().getY());
		this.setMove(move);
		System.out.println("Position:"+this.getMove().getStartPoint().getX()+","+this.getMove().getStartPoint().getY()+"------>"+this.getMove().getEndPoint().getX()+","+this.getMove().getEndPoint().getY());
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
		this.getBoard().addMouseListener(this);
		this.getBoard().addMouseMotionListener(this);
	}
	
	@Override
	public void setStatus(Move move) {
		// TODO Auto-generated method stub
		
	}
}
