package com.org.ouc.TwoBeatOne.gameTree;


import com.org.ouc.TwoBeatOne.TwoBeatOneBoard;
import com.org.ouc.TwoBeatOne.prediction.ChessCoordinate;


/**
 * 博弈树的叶子节点
 *
 * @author YBZ
 * @data 2011-12-08
 *
 */
public class TreeLeafNode extends GameTreeNode {
	
	//构造函数
	public TreeLeafNode(int nodeId, int[][] nodeMode, ChessCoordinate[] newMoveChess) {
		this.setNodeId(nodeId);
		this.setNodeMode(nodeMode);
		this.setNewMoveChess(newMoveChess);
		this.setNodeValue(-99999999);
		
		ChessCoordinate[] bestNextMoveChess = new ChessCoordinate[2];
		bestNextMoveChess[0] = new ChessCoordinate(-1, -1);
		bestNextMoveChess[1] = new ChessCoordinate(-1, -1);
		this.setBestNextMoveChess(bestNextMoveChess);
	}

	//获取叶子节点信息
	protected StringBuffer getNodeInfo() {
		StringBuffer sb = new StringBuffer();
		int boardX = TwoBeatOneBoard.XLen;
		int boardY = TwoBeatOneBoard.YLen;
		int[][] chessModeArr = new int[boardX][boardY];
		chessModeArr = getNodeMode();

		//打印缩进
		for (int i = 0; i < this.getDepth(); i++) {
			sb.append(' ');
		}
		sb.append("---");
		sb.append("[nodeId=");
		sb.append(this.getNodeId());

		sb.append(" nodeValue=");
		sb.append(this.getNodeValue());
		sb.append(']');
		
		for(int i = 0; i < boardX; i++) {
			sb.append("\n   ");
			for(int k = 0; k < this.getDepth(); k++) {
				sb.append(' ');
			}
			for(int j = 0; j < boardY; j++) {
				sb.append(chessModeArr[i][j]);
				sb.append(' ');
			}
		}
		
		return sb;
	}

	public String toString() {
		return getNodeInfo().toString();
	}

}
