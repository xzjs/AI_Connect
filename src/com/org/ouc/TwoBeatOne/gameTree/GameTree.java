package com.org.ouc.TwoBeatOne.gameTree;

import java.util.HashMap;
import java.util.Stack;

import com.org.ouc.TwoBeatOne.prediction.ChessCoordinate;
import com.org.ouc.TwoBeatOne.prediction.EvaluateValue;
import com.org.ouc.TwoBeatOne.prediction.SubNodeMode;



public class GameTree {		//������
	
	private int thinkDepth = 0;	//˼�����
	private TreeBranchNode root;	//���������ڵ�
	private ChessCoordinate[] rootMove = new ChessCoordinate[2];	//�����ƶ��������¾�����
	
	private int nodeId = 0;	//�ڵ���
	private Stack<TreeBranchNode> stack = new Stack<TreeBranchNode>();	//ջ���洢��֧�ڵ�
	private HashMap<Integer,SubNodeMode> subNodeMap = new HashMap<Integer,SubNodeMode>();	//��֧�ڵ㼯��
	
	//���캯��
	public GameTree(int thinkDepth, TreeBranchNode root) {
		this.thinkDepth = thinkDepth;
		this.root = root;
		rootMove[0] = new ChessCoordinate(-1, -1);
		rootMove[1] = new ChessCoordinate(-1, -1);
		createGameTree();
	}
	
	public ChessCoordinate[] getRootMove() {
		return rootMove;
	}
	
	//���ɵ�ǰ��ֵĲ�����
	private void createGameTree() {
		
		SubNodeMode rootSubMode = null;	//���ڵ���ӽڵ����
		
		stack.push(root);
		rootSubMode = new SubNodeMode(1, root.getNodeMode());
		subNodeMap.put(nodeId++, rootSubMode);
		
		while(rootSubMode.hasNotCheckedChess()) {
			createBranch(root);	//���������һ����֧
			createNewSubNode();
		}
		
		rootMove = root.getBestNextMoveChess();
		
	}
	
	//�������һ����֧
	private void createBranch(TreeBranchNode checkingNode) {
		TreeBranchNode parentNode = null;	//���ڵ㣨��ʱ������
		
		SubNodeMode subMode = null;	//ĳ��֧�ڵ���ӽڵ���֣���ʱ������
		int subNodeFlag = 1;	//�ӽڵ��־��1������ڵ㣬-1����С�ڵ�
		
		TreeBranchNode branchNode = null;	//��֧�ڵ㣨��ʱ������
		int[][] nodeMode = null;	//�ڵ���֣���ʱ������
		
		for(int depth = checkingNode.getDepth()+1; depth < thinkDepth; depth++) {
			if(depth%2 == 0)	subNodeFlag = 1;
			else	subNodeFlag = -1;
			
			parentNode = stack.pop();
			(subNodeMap.get(parentNode.getNodeId())).makeRandomPredictMode();
			(subNodeMap.get(parentNode.getNodeId())).checkBeatChess();
			
			nodeMode = (subNodeMap.get(parentNode.getNodeId())).getPredictMode();
			subMode = new SubNodeMode(subNodeFlag, nodeMode);
			
			branchNode = new TreeBranchNode(nodeId, nodeMode, (subNodeMap.get(parentNode.getNodeId())).getNewMoveChess());
			parentNode.addSubNode(branchNode);
			
			stack.push(parentNode);
			stack.push(branchNode);
			
			subNodeMap.put(nodeId++, subMode);
		}
		
		createLeaf();	//���ɸ÷�֧�ĵ�һ��Ҷ�ڵ�
		
	}
	
	//�������һ��Ҷ�ڵ�
	private void createLeaf() {
		TreeBranchNode parentNode = null;	//���ڵ㣨��ʱ������
		int parentFlag = 1;		//���ڵ��־��1������ڵ㣬-1����С�ڵ�
		
		TreeLeafNode leafNode = null;	//Ҷ�ڵ㣨��ʱ������
		int[][] nodeMode = null;	//�ڵ���֣���ʱ������
		EvaluateValue nodeValue = null;	//�ڵ�����ֵ����ʱ������
		
		if((thinkDepth-1)%2 == 0)	parentFlag = 1;
		else	parentFlag = -1;
		
		parentNode = stack.pop();
		(subNodeMap.get(parentNode.getNodeId())).makeRandomPredictMode();
		
		nodeMode = (subNodeMap.get(parentNode.getNodeId())).getPredictMode();
		leafNode = new TreeLeafNode(nodeId++, nodeMode, (subNodeMap.get(parentNode.getNodeId())).getNewMoveChess());
						
		//����Ҷ�ڵ�����ֵ
		nodeValue = new EvaluateValue(parentFlag, leafNode.getNodeMode(), leafNode.getNewMoveChess()[1]);
		leafNode.setNodeValue(nodeValue.getEvaluateValue());
		
		(subNodeMap.get(parentNode.getNodeId())).checkBeatChess();
		leafNode.setNodeMode((subNodeMap.get(parentNode.getNodeId())).getPredictMode());
		
		/*
		(leafNode.getNodeMode()).println();
		System.out.println("nodeValue: "+leafNode.getNodeValue()+"\n");
		*/
		
		if(parentNode.getNodeValue() == -99999999) {	//�÷�֧�ϵ�һ������չ��Ҷ�ڵ�
			parentNode.setNodeValue(leafNode.getNodeValue());
			parentNode.setBestNextMoveChess(leafNode.getNewMoveChess());
		}
		else {
			if(parentFlag == 1) {	//���ڵ�Ϊ����ڵ�
				if(leafNode.getNodeValue() > parentNode.getNodeValue()) {	//���ڵ�����ֵ��Ҫ����
					parentNode.setNodeValue(leafNode.getNodeValue());
					parentNode.setBestNextMoveChess(leafNode.getNewMoveChess());
				}
			}
			else {	//���ڵ�Ϊ��С�ڵ�
				if(leafNode.getNodeValue() < parentNode.getNodeValue()) {	//���ڵ�����ֵ��Ҫ����
					parentNode.setNodeValue(leafNode.getNodeValue());
					parentNode.setBestNextMoveChess(leafNode.getNewMoveChess());
				}
			}
		}
		
		parentNode.addSubNode(leafNode);
		stack.push(parentNode);
		
	}
	
	//���н��������ȡ����������µ��ӽڵ�
	private void createNewSubNode() {
		
		TreeBranchNode checkingNode = null;	//���ڵ㣨��ʱ������
		int checkingNodeDepth = 0;	//���ڵ����
		int checkingNodeFlag = 1;		//���ڵ��־��1������ڵ㣬-1����С�ڵ�
		
		while(stack.peek() != root) {
			checkingNode = stack.peek();
			checkingNodeDepth = checkingNode.getDepth();
			
			if(checkingNodeDepth%2 == 0)	checkingNodeFlag = 1;
			else	checkingNodeFlag = -1;
			
			while((subNodeMap.get(checkingNode.getNodeId())).hasNotCheckedChess()) {	//���ڵ㻹��δ��չ���ӽڵ�
				if(!alphaBetaPruning(checkingNode, checkingNodeFlag)) {
					if(checkingNodeDepth < thinkDepth-1) {	//�����չ��һ���֧�ڵ�
						createBranch(checkingNode);
					}
					else
						createLeaf();	//�����չҶ�ڵ�
				}
				else {	//��֦
					//System.out.println("YBZ��֦��");
					break;
				}
			}
			
			//���ڵ�����δ��չ���ӽڵ㣬������ֵ��ȷ�������ϲ����ȴ���
			if((checkingNode.getPMenuComponent()).getNodeValue() == -99999999) {	//�÷�֧�ϵ�һ������չ���ӽڵ�
				(checkingNode.getPMenuComponent()).setNodeValue(checkingNode.getNodeValue());
				(checkingNode.getPMenuComponent()).setBestNextMoveChess(checkingNode.getNewMoveChess());
			}
			else {
				if(checkingNodeFlag == 1) {	//���ڵ�Ϊ����ڵ�
					if(checkingNode.getNodeValue() < (checkingNode.getPMenuComponent()).getNodeValue()) {	//�ϲ����Ƚڵ�����ֵ��Ҫ����
						(checkingNode.getPMenuComponent()).setNodeValue(checkingNode.getNodeValue());
						(checkingNode.getPMenuComponent()).setBestNextMoveChess(checkingNode.getNewMoveChess());
					}
				}
				else {	//���ڵ�Ϊ��С�ڵ�
					if(checkingNode.getNodeValue() > (checkingNode.getPMenuComponent()).getNodeValue()) {	//�ϲ����Ƚڵ�����ֵ��Ҫ����
						(checkingNode.getPMenuComponent()).setNodeValue(checkingNode.getNodeValue());
						(checkingNode.getPMenuComponent()).setBestNextMoveChess(checkingNode.getNewMoveChess());
					}
				}
			}
			
			stack.pop();
		}

	}
	
	//���Alpha��Beta��֦����
	private boolean alphaBetaPruning(TreeBranchNode node, int flag) {
		if(flag == 1) {	//�ýڵ�Ϊ����ڵ�
			if(node.getNodeValue() >= (node.getPMenuComponent()).getNodeValue())	//�ýڵ㲻����չ��Beta��֦
				return true;
			else	//�ýڵ������չ
				return false;
		}
		else {	//�ýڵ�Ϊ��С�ڵ�
			if(node.getNodeValue() <= (node.getPMenuComponent()).getNodeValue())	//�ýڵ㲻����չ��Alpha��֦
				return true;
			else	//�ýڵ������չ
				return false;
		}
	}
	
}
