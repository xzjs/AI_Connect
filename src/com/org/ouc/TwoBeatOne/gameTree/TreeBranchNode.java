package com.org.ouc.TwoBeatOne.gameTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.org.ouc.TwoBeatOne.TwoBeatOneBoard;
import com.org.ouc.TwoBeatOne.prediction.ChessCoordinate;


/**
 * 博弈树的分支节点
 *
 * @author YBZ
 * @data 2011-12-08
 */
public class TreeBranchNode extends GameTreeNode {

	//存储子节点
	List subNodesList = new ArrayList();
	
	//构造函数（根节点）
	public TreeBranchNode(int nodeId, int[][] nodeMode) {
		this.setNodeId(nodeId);
		this.setNodeMode(nodeMode);
		this.setNodeValue(-99999999);
		
		ChessCoordinate[] newMoveChess = new ChessCoordinate[2];
		newMoveChess[0] = new ChessCoordinate(-1, -1);
		newMoveChess[1] = new ChessCoordinate(-1, -1);
		this.setNewMoveChess(newMoveChess);
		this.setBestNextMoveChess(newMoveChess);
	}

	//构造函数
	public TreeBranchNode(int nodeId, int[][] nodeMode, ChessCoordinate[] newMoveChess) {
		this.setNodeId(nodeId);
		this.setNodeMode(nodeMode);
		this.setNewMoveChess(newMoveChess);
		this.setNodeValue(-99999999);
		
		ChessCoordinate[] bestNextMoveChess = new ChessCoordinate[2];
		bestNextMoveChess[0] = new ChessCoordinate(-1, -1);
		bestNextMoveChess[1] = new ChessCoordinate(-1, -1);
		this.setBestNextMoveChess(bestNextMoveChess);
	}

	//添加子节点
	public void addSubNode(GameTreeNode menuComponent) {
		// 设置父节点
		menuComponent.setPMenuComponent(this);

		// 设置节点的深度
		menuComponent.setDepth(this.getDepth() + 1);
		subNodesList.add(menuComponent);
	}

	//删除一个子节点
	public void removeSubNode(GameTreeNode menuComponent) {
		subNodesList.remove(menuComponent);
	}

	//获取子节点
	public List getSubNodes() {
		return subNodesList;
	}

	//打印节点信息，以树的形式展示，所以它包括了所有子节点信息
	public void print() {
		System.out.println(this.getNodeInfo());
	}

	//打印节点本身信息，不递归打印子节点信息
	public String toString() {
		return getSefNodeInfo().toString();
	}

	//递归打印节点信息实现
	protected StringBuffer getNodeInfo() {

		StringBuffer sb = getSefNodeInfo();
		sb.append(System.getProperty("line.separator"));
		//如果有子节点
		for (Iterator iter = subNodesList.iterator(); iter.hasNext();) {
			GameTreeNode node = (GameTreeNode) iter.next();
			//递归打印子节点信息
			sb.append(node.getNodeInfo());

			if (iter.hasNext()) {
				sb.append(System.getProperty("line.separator"));
			}
		}
		return sb;
	}

	//节点本身信息，不含子节点信息
	private StringBuffer getSefNodeInfo() {
		StringBuffer sb = new StringBuffer();
		int boardX = TwoBeatOneBoard.XLen;
		int boardY = TwoBeatOneBoard.YLen;
		int[][] chessModeArr = new int[boardX][boardY];
		chessModeArr = getNodeMode();
		
		//打印缩进
		for (int i = 0; i < this.getDepth(); i++) {
			sb.append(' ');
		}
		sb.append("+--");
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

	//为外界提供遍历组合结构的迭代器
	public Iterator createDepthOrderIterator() {
		return new TreeOutOrder.DepthOrderIterator(this);
	}

	/**
	 * 使用树的先序遍历递归方式查找指定的节点
	 * 
	 * @param treeNode 查找的起始节点
	 * @param treeId 节点编号
	 * @return
	 */
	protected GameTreeNode getNode(GameTreeNode treeNode, int treeId) {

		//如果找到，则停止后续搜索，并把查找到的节点返回给上层调用者
		if (treeNode.getNodeId() == treeId) {//1、先与父节点比对
			return treeNode;
		}

		GameTreeNode tmp = null;

		//如果为分支节点，则遍历子节点
		if (treeNode instanceof TreeBranchNode) {

			for (int i = 0; i < treeNode.getSubNodes().size(); i++) {//2、再与子节点比对
				tmp = getNode((GameTreeNode) treeNode.getSubNodes().get(i), treeId);
				if (tmp != null) {//如果查找到，则返回上层调用者
					return tmp;
				}
			}
		}

		//如果没有找到，返回上层调用者
		return null;
	}

}
