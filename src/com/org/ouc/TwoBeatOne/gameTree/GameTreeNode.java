package com.org.ouc.TwoBeatOne.gameTree;

import java.util.Iterator;
import java.util.List;

import com.org.ouc.TwoBeatOne.prediction.ChessCoordinate;


/**
 * �������ڵ����ӿ�
 * 
 * @author YBZ
 * @data 2011-12-08
 */
public abstract class GameTreeNode implements Comparable {

	//���ڵ�
	private GameTreeNode pNode;

	//�ڵ��ţ������޸�
	private int nodeId;

	//�ڵ���֣����޸�
	private int[][] nodeMode;
	
	//�õ��ڵ�������ƶ��������¾����꣬���޸�
	private ChessCoordinate[] newMoveChess = new ChessCoordinate[2];

	//�ڵ�����ֵ�����޸�
	private int evaluateValue;
	
	//��һ�������ƶ��������¾����꣨���ӽڵ�ȷ���������޸�
	private ChessCoordinate[] bestNextMoveChess = new ChessCoordinate[2];
	
	//�ڵ���ȣ���Ĭ��Ϊ0
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

	//����ӽڵ� Ĭ�ϲ�֧�֣�Ҷ�ӽڵ㲻֧�ִ˹���
	public void addSubNode(GameTreeNode menuComponent) {
		throw new UnsupportedOperationException();
	}

	//ɾ���ӽڵ� Ĭ�ϲ�֧�֣�Ҷ�ӽڵ㲻֧�ִ˹���
	public void removeSubNode(GameTreeNode menuComponent) {
		throw new UnsupportedOperationException();
	}

	//�޸Ľڵ����
	public void modiNodeMode(int[][] mode) {
		this.setNodeMode(mode);
	}
	
	//�޸Ľڵ�����ֵ
	public void modiNodeValue(int value) {
		this.setNodeValue(value);
	}

	//��ȡ�ӽڵ� Ĭ�ϲ�֧�֣�Ҷ�ӽڵ㲻֧�ִ˹���
	public List getSubNodes() {
		throw new UnsupportedOperationException();
	}

	//��ӡ�ڵ���Ϣ
	public void print() {
		throw new UnsupportedOperationException();
	}

	//��ȡ�ڵ���Ϣ���ڵ��š���ֺ�����ֵ��
	protected abstract StringBuffer getNodeInfo();

	//�ṩ��ȵ����� Ĭ�ϲ�֧�֣�Ҷ�ӽڵ㲻֧�ִ˹���
	public Iterator createDepthOrderIterator() {
		throw new UnsupportedOperationException();
	}

	/**
	 * �������ڵ�id���ڵ�ǰ�ڵ����ӽڵ�������ָ���Ľڵ�
	 * @param treeId
	 * @return TreeNode
	 */
	public GameTreeNode getTreeNode(int treeId) {
		return getNode(this, treeId);
	}

	/**
	 * ʹ��������������ݹ鷽ʽ����ָ���Ľڵ�
	 * 
	 * @param treeNode ���ҵ���ʼ�ڵ�
	 * @param treeId �ڵ���
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

		//��������ڵ��nodeId�������Ϊ��ͬһ�ڵ�
		return this.getNodeId() == menu.getNodeId();
	}
	
}
