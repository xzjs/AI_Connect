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
	 * 检查ChessMove是否合法
	 * 1、为非负数   2、符合棋局要求 
	 * @param move	ChessStatus实例
	 * @param xLen  棋局的x轴格子数
	 * @param yLen  棋局的y轴格子数
	 * @return		验证结果，true：合法，否者false
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
	 * 检查该步走法是否是可到达的，
	 * 在二打一中，只能走一个单位距离
	 * @param move  ChessStatus实例
	 * @return      验证结果，true：合法，否者false
	 */
	private boolean isReachable(Move move){
		Position sp = move.getStartPoint();
		Position ep = move.getEndPoint();
		
		return Math.abs(sp.getX() - ep.getX() + sp.getY() - ep.getY()) == 1; 
	}
	
	/**
	 * 检查走步是否符合逻辑：
	 * 1、该步的前一位置与roleType项符合
	 * 2、该步的后一位置未被占用
	 * @param status，当前棋局
	 * @param move，当前棋局下的一个走步
	 * @param roleType，该走步是黑方还是白方
	 * @return
	 */
	private boolean isLogical(int[][] status, Move move, int roleType){	
		Position sp = move.getStartPoint();
		Position ep = move.getEndPoint();
		
		int stat[][] = status; // 获得当前棋局的矩阵表示
		
		if(stat[sp.getX()][sp.getY()] != roleType || stat[ep.getX()][ep.getY()] != 0){
			return false;
		}
		
		return true;
		
	}
	
	public boolean check(int[][] status, Move move, int roleType) {
		//按此顺序检查是否合法，顺序不能变化，否则可能会报错
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
	 * 根据下棋规则添加新的MoveResp
	 * @param mrList
	 */
	private void addMoveRespByRule(int[][] status, Move move,
			int roleType, ArrayList<MoveResp> mrList){

		int xLen = TwoBeatOneBoard.XLen;
		int yLen = TwoBeatOneBoard.YLen;
		
		Position ep = move.getEndPoint();
		
		int stat[][] = status;
		stat[move.getStartPoint().getX()][move.getStartPoint().getY()] = 0;
		
		int x = ep.getX(); int y = ep.getY(); // 当前走步后，走动棋子的位置
		
		int roleRight, roleLeft, roleUp, roleDown; //连着的两个同伴的左右、上下位置的属性
		
		Position pRight = null, pLeft = null; // 连着的两个同伴的左右位置
		
		if (x > 0 && stat[x - 1][y] == roleType) // 当前走步的左边是同伙
		{	
			// 同伙的左边是否有位置
			if (x - 1 > 0) // 有位置
			{
				roleLeft = stat[(x - 1) - 1][y];
				if (roleLeft == -roleType) // 同伙的左边是敌人
					pLeft = new Position((x - 1) - 1, y);
			}	
			else roleLeft = 0; // 同伙已在最左边
			
			// 自己的右边是否有位置
			if (x < xLen - 1) 
			{
				roleRight = stat[x + 1][y];
				if (roleRight == -roleType) //自己的右边是敌人
					pRight = new Position(x + 1, y);
			}
			else roleRight = 0; // 自己已在最右边
			
			if (roleLeft * roleRight == 0) // 符合吃子条件，同伙的左边和自己的右边至少有一个是空格
			{
			
				if (pLeft != null) //同伴的左边有敌人
					if (pLeft.getX() == 0 || stat[pLeft.getX() - 1][y] == 0) // 左边的子可以被吃掉
						mrList.add(new MoveResp(pLeft, MoveResp.ACTION_DISPEAR));
				if (pRight != null) //自己的右边有敌人
					if (pRight.getX() == xLen - 1 || stat[pRight.getX() + 1][y] == 0)	// 右边的子可以被吃掉
						mrList.add(new MoveResp(pRight, MoveResp.ACTION_DISPEAR));
			}

		} // end if （当前走步的左边是同伙）
		
		pRight = null; pLeft = null; // 重置左右位置
		if (x < xLen - 1 && stat[x + 1][y] == roleType)	// 当前走步的右边是同伙
		{	
			// 同伙的右边是否有位置
			if (x + 1 < xLen - 1)  // 右边有位置
			{
				roleRight = stat[(x + 1) + 1][y];
				if (roleRight == -roleType) // 同伙的右边是敌人
					pRight = new Position((x + 1) + 1, y);
			}	
			else roleRight = 0;  //同伙已在最右边
			
			// 自己的左边是否有位置
			if (x > 0) 
			{
				roleLeft = stat[x - 1][y];
				if (roleLeft == -roleType) //自己的左边是敌人
					pLeft = new Position(x - 1, y);
			}
			else roleLeft = 0; // 自己已在最左边
			
			if (roleLeft * roleRight == 0) // 符合吃子条件，自己的左边和同伴的右边至少有一个是空格
			{
			
				if (pLeft != null) //自己的左边有敌人
					if (pLeft.getX() == 0 || stat[pLeft.getX() - 1][y] == 0) // 左边的子可以被吃掉
						mrList.add(new MoveResp(pLeft, MoveResp.ACTION_DISPEAR));
				if (pRight != null) //同伴的右边有敌人
					if (pRight.getX() == xLen - 1 || stat[pRight.getX() + 1][y] == 0)	// 右边的子可以被吃掉
						mrList.add(new MoveResp(pRight, MoveResp.ACTION_DISPEAR));
			}

		} // end if （当前走步的右边是同伙）
		
		
		Position pUp = null, pDown = null; // 连着的两个同伴的上下位置
		
		if (y > 0 && stat[x][y - 1] == roleType) // 当前走步的上边是同伙
		{	
			// 同伙的上边是否有位置
			if (y - 1 > 0) // 有位置
			{
				roleUp = stat[x][(y - 1) - 1];
				if (roleUp == -roleType) // 同伙的上边是敌人
					pUp = new Position(x, (y - 1) - 1);
			}	
			else roleUp = 0; //同伙已在最上边
			
			// 自己的下边是否有位置
			if (y < yLen - 1) 
			{
				roleDown = stat[x][y + 1];
				if (roleDown == -roleType) //自己的下边是敌人
					pDown = new Position(x, y + 1);
			}
			else roleDown = 0; //自己已在最下边
			
			if (roleUp * roleDown == 0) // 符合吃子条件，同伙的上边和自己的下边至少有一个是空格
			{
			
				if (pUp != null) //同伴的上边有敌人
					if (pUp.getY() == 0 || stat[pUp.getX()][pUp.getY() - 1] == 0) // 上边的子可以被吃掉
						mrList.add(new MoveResp(pUp, MoveResp.ACTION_DISPEAR));
				if (pDown != null) //自己的下边有敌人
					if (pDown.getY() == yLen - 1 || stat[pDown.getX()][pDown.getY() + 1] == 0)	// 右边的子可以被吃掉
						mrList.add(new MoveResp(pDown, MoveResp.ACTION_DISPEAR));
			}

		} // end if （当前走步的上边是同伙）
		
		pUp = null; pDown = null; // 重置上下位置
		if (y < yLen - 1 && stat[x][y + 1] == roleType)	// 当前走步的下边是同伙
		{	
			// 同伙的下边是否有位置
			if (y + 1 < yLen - 1)  // 下边有位置
			{
				roleDown = stat[x][(y + 1) + 1];
				if (roleDown == -roleType) // 同伙的下边是敌人
					pDown = new Position(x, (y + 1) + 1);
			}	
			else roleDown = 0;  //同伙已在最下边
			
			// 自己的上边是否有位置
			if (y > 0) 
			{
				roleUp = stat[x][y - 1];
				if (roleUp == -roleType) //自己的上边是敌人
					pUp = new Position(x, y - 1);
			}
			else roleUp = 0; //自己已在最上边
			
			if (roleUp * roleDown == 0) // 符合吃子条件，自己的上边和同伙的下边至少有一个是空格
			{
			
				if (pUp != null) //自己的上边有敌人
					if (pUp.getY() == 0 || stat[pUp.getX()][pUp.getY() - 1] == 0) // 上边的子可以被吃掉
						mrList.add(new MoveResp(pUp, MoveResp.ACTION_DISPEAR));
				if (pDown != null) //同伴的下边有敌人
					if (pDown.getY() == yLen - 1 || stat[pDown.getX()][pDown.getY() + 1] == 0)	// 右边的子可以被吃掉
						mrList.add(new MoveResp(pDown, MoveResp.ACTION_DISPEAR));
			}

		} // end if （当前走步的下边是同伙）

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
	 * 按下鼠标时，获取Move的StartPoint
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		this.setMove(null);
		move.setStartPoint(new Position((e.getY()-this.getBoard().getTopMargin())/this.getBoard().getyLenBetweenLines(),(e.getX()-this.getBoard().getLeftMargin())/this.getBoard().getxLenBetweenLines()));
		System.out.print("Position:"+move.getStartPoint().getX()+","+move.getStartPoint().getY()+"---->");
	}
	
	/**
	 * 释放时，获取Move的EndPoint
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
