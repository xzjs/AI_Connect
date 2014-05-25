package com.org.ouc.TwoBeatOne;

import com.org.ouc.TwoBeatOne.gameTree.GameTree;
import com.org.ouc.TwoBeatOne.gameTree.TreeBranchNode;
import com.org.ouc.platform.Move;


public class RobotPlayer extends TwoBeatOnePlayer{

	public Move next(int[][] now){
		int role = this.getTurn();
		int[][] rootStatusArr = new int[TwoBeatOneBoard.XLen][TwoBeatOneBoard.YLen];
		for(int i = 0; i < TwoBeatOneBoard.XLen; i++) {
			for(int j = 0; j < TwoBeatOneBoard.YLen; j++) {
				rootStatusArr[i][j] = role * now[i][j];
			}
		}
		int[][] rootStatus = setStatus(rootStatusArr, true);
		
		final int thinkDepth = 2;	//Ë¼¿¼Éî¶È
		TreeBranchNode root = new TreeBranchNode(0, rootStatus);
		GameTree ybzGameTree = new GameTree(thinkDepth, root);
		System.out.println("YBZ_rootValue: "+root.getNodeValue());
		//root.print();
		
		int x0, y0, x1, y1;
		x0 = ybzGameTree.getRootMove()[0].getChessX();
		y0 = (ybzGameTree.getRootMove()[0]).getChessY();
		x1 = (ybzGameTree.getRootMove()[1]).getChessX();
		y1 = (ybzGameTree.getRootMove()[1]).getChessY();
		
		return new Move(x0, y0, x1, y1);
	}
	
    public int[][] setStatus(int s[][], boolean flag){
		
		int xLen = TwoBeatOneBoard.XLen;
		int yLen = TwoBeatOneBoard.YLen;
		
		
		int[][]status = new int[xLen][yLen];
		if(flag){
			for(int x=0;x<xLen;x++)
				for(int y=0;y<yLen;y++)
					status[x][y] = s[x][y];
		}else{
			for(int x=xLen;x>0;x--)
				for(int y=yLen;y>0;y--)
					status[xLen - x][yLen - y] = s[x-1][y-1];
		}
		return status;
	}

}
