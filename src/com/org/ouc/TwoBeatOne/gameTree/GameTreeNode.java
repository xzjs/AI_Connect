package com.org.ouc.TwoBeatOne.gameTree;

import java.util.Iterator;
import java.util.List;

import com.org.ouc.TwoBeatOne.prediction.ChessCoordinate;


/**
 * 博弈树节点抽象接口
 * 
 * @author YBZ
 * @data 2011-12-08
 */
public abstract class GameTreeNode implements Comparable {

	//父节点
	private GameTreeNode pNode;

	//节点编号，不能修改
	private int nodeId;

	//节点棋局，能修改
	private int[][] nodeMode;
	
	//得到节点棋局所移动的棋子新旧坐标，能修改
	private ChessCoordinate[] newMoveChess = new ChessCoordinate[2];

	//节点评价值，能修改
	private int evaluateValue;
	
	//下一步最优移动的棋子新旧坐标（由子节点确定），能修改
	private ChessCoordinate[] bestNextMoveChess = new ChessCoordinate[2];
	
	//节点深度，根默认为0
	private int depth;

	public GameTreeNode getPMenuComponent() {
		return pNode;
	}

	public void setPMenuComponent(GameTreeNode menuComponent) {
		pNode = menuComponent;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public int[][] getNodeMode() {
		return nodeMode;
	}

	public void setNodeMode(int[][] mode) {
		this.nodeMode = mode;
	}
	
	public ChessCoordinate[] getNewMoveChess() {
		return newMoveChess;
	}
	
	public void setNewMoveChess(ChessCoordinate[] newMoveChess) {
		for(int i = 0; i < 2; i++) {
			this.newMoveChess[i] =  new ChessCoordinate(newMoveChess[i].getChessX(), newMoveChess[i].getChessY());
		}
	}
	
	public int getNodeValue() {
		return evaluateValue;
	}

	public void setNodeValue(int value) {
		this.evaluateValue = value;
	}
	
	public ChessCoordinate[] getBestNextMoveChess() {
		return bestNextMoveChess;
	}
	
	public void setBestNextMoveChess(ChessCoordinate[] bestMoveChess) {
		for(int i = 0; i < 2; i++) {
			this.bestNextMoveChess[i] = new ChessCoordinate(bestMoveChess[i].getChessX(), bestMoveChess[i].getChessY());
		}
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	//添加子节点 默认不支持，叶子节点不支持此功能
	public void addSubNode(GameTreeNode menuComponent) {
		throw new UnsupportedOperationException();
	}

	//删除子节点 默认不支持，叶子节点不支持此功能
	public void removeSubNode(GameTreeNode menuComponent) {
		throw new UnsupportedOperationException();
	}

	//修改节点棋局
	public void modiNodeMode(int[][] mode) {
		this.setNodeMode(mode);
	}
	
	//修改节点评价值
	public void modiNodeValue(int value) {
		this.setNodeValue(value);
	}

	//获取子节点 默认不支持，叶子节点不支持此功能
	public List getSubNodes() {
		throw new UnsupportedOperationException();
	}

	//打印节点信息
	public void print() {
		throw new UnsupportedOperationException();
	}

	//获取节点信息（节点编号、棋局和评价值）
	protected abstract StringBuffer getNodeInfo();

	//提供深度迭代器 默认不支持，叶子节点不支持此功能
	public Iterator createDepthOrderIterator() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 根据树节点id，在当前节点与子节点中搜索指定的节点
	 * @param treeId
	 * @return TreeNode
	 */
	public GameTreeNode getTreeNode(int treeId) {
		return getNode(this, treeId);
	}

	/**
	 * 使用树的先序遍历递归方式查找指定的节点
	 * 
	 * @param treeNode 查找的起始节点
	 * @param treeId 节点编号
	 * @return
	 */
	protected GameTreeNode getNode(GameTreeNode treeNode, int treeId) {
		throw new UnsupportedOperationException();
	}

	public int compareTo(Object o) {

		GameTreeNode temp = (GameTreeNode) o;

		return this.getNodeId() > temp.getNodeId() ? 1 : (this.getNodeId() < temp
				.getNodeId() ? -1 : 0);
	}

	public boolean equals(Object menuComponent) {

		if (!(menuComponent instanceof GameTreeNode)) {
			return false;
		}
		GameTreeNode menu = (GameTreeNode) menuComponent;

		//如果两个节点的nodeId相等则认为是同一节点
		return this.getNodeId() == menu.getNodeId();
	}
	
}
