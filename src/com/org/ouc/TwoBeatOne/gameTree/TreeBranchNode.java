package com.org.ouc.TwoBeatOne.gameTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.org.ouc.TwoBeatOne.TwoBeatOneBoard;
import com.org.ouc.TwoBeatOne.prediction.ChessCoordinate;


/**
 * �������ķ�֧�ڵ�
 *
 * @author YBZ
 * @data 2011-12-08
 */
public class TreeBranchNode extends GameTreeNode {

	//�洢�ӽڵ�
	List subNodesList = new ArrayList();
	
	//���캯�������ڵ㣩
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

	//���캯��
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

	//����ӽڵ�
	public void addSubNode(GameTreeNode menuComponent) {
		// ���ø��ڵ�
		menuComponent.setPMenuComponent(this);

		// ���ýڵ�����
		menuComponent.setDepth(this.getDepth() + 1);
		subNodesList.add(menuComponent);
	}

	//ɾ��һ���ӽڵ�
	public void removeSubNode(GameTreeNode menuComponent) {
		subNodesList.remove(menuComponent);
	}

	//��ȡ�ӽڵ�
	public List getSubNodes() {
		return subNodesList;
	}

	//��ӡ�ڵ���Ϣ����������ʽչʾ�������������������ӽڵ���Ϣ
	public void print() {
		System.out.println(this.getNodeInfo());
	}

	//��ӡ�ڵ㱾����Ϣ�����ݹ��ӡ�ӽڵ���Ϣ
	public String toString() {
		return getSefNodeInfo().toString();
	}

	//�ݹ��ӡ�ڵ���Ϣʵ��
	protected StringBuffer getNodeInfo() {

		StringBuffer sb = getSefNodeInfo();
		sb.append(System.getProperty("line.separator"));
		//������ӽڵ�
		for (Iterator iter = subNodesList.iterator(); iter.hasNext();) {
			GameTreeNode node = (GameTreeNode) iter.next();
			//�ݹ��ӡ�ӽڵ���Ϣ
			sb.append(node.getNodeInfo());

			if (iter.hasNext()) {
				sb.append(System.getProperty("line.separator"));
			}
		}
		return sb;
	}

	//�ڵ㱾����Ϣ�������ӽڵ���Ϣ
	private StringBuffer getSefNodeInfo() {
		StringBuffer sb = new StringBuffer();
		int boardX = TwoBeatOneBoard.XLen;
		int boardY = TwoBeatOneBoard.YLen;
		int[][] chessModeArr = new int[boardX][boardY];
		chessModeArr = getNodeMode();
		
		//��ӡ����
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

	//Ϊ����ṩ������Ͻṹ�ĵ�����
	public Iterator createDepthOrderIterator() {
		return new TreeOutOrder.DepthOrderIterator(this);
	}

	/**
	 * ʹ��������������ݹ鷽ʽ����ָ���Ľڵ�
	 * 
	 * @param treeNode ���ҵ���ʼ�ڵ�
	 * @param treeId �ڵ���
	 * @return
	 */
	protected GameTreeNode getNode(GameTreeNode treeNode, int treeId) {

		//����ҵ�����ֹͣ�������������Ѳ��ҵ��Ľڵ㷵�ظ��ϲ������
		if (treeNode.getNodeId() == treeId) {//1�����븸�ڵ�ȶ�
			return treeNode;
		}

		GameTreeNode tmp = null;

		//���Ϊ��֧�ڵ㣬������ӽڵ�
		if (treeNode instanceof TreeBranchNode) {

			for (int i = 0; i < treeNode.getSubNodes().size(); i++) {//2�������ӽڵ�ȶ�
				tmp = getNode((GameTreeNode) treeNode.getSubNodes().get(i), treeId);
				if (tmp != null) {//������ҵ����򷵻��ϲ������
					return tmp;
				}
			}
		}

		//���û���ҵ��������ϲ������
		return null;
	}

}
