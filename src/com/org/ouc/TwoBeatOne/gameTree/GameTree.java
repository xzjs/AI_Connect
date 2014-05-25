package com.org.ouc.TwoBeatOne.gameTree;

import java.util.HashMap;
import java.util.Stack;

import com.org.ouc.TwoBeatOne.prediction.ChessCoordinate;
import com.org.ouc.TwoBeatOne.prediction.EvaluateValue;
import com.org.ouc.TwoBeatOne.prediction.SubNodeMode;



public class GameTree {		//博弈树
	
	private int thinkDepth = 0;	//思考深度
	private TreeBranchNode root;	//博弈树根节点
	private ChessCoordinate[] rootMove = new ChessCoordinate[2];	//决定移动的棋子新旧坐标
	
	private int nodeId = 0;	//节点编号
	private Stack<TreeBranchNode> stack = new Stack<TreeBranchNode>();	//栈，存储分支节点
	private HashMap<Integer,SubNodeMode> subNodeMap = new HashMap<Integer,SubNodeMode>();	//分支节点集合
	
	//构造函数
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
	
	//生成当前棋局的博弈树
	private void createGameTree() {
		
		SubNodeMode rootSubMode = null;	//根节点的子节点棋局
		
		stack.push(root);
		rootSubMode = new SubNodeMode(1, root.getNodeMode());
		subNodeMap.put(nodeId++, rootSubMode);
		
		while(rootSubMode.hasNotCheckedChess()) {
			createBranch(root);	//随机生成下一条分支
			createNewSubNode();
		}
		
		rootMove = root.getBestNextMoveChess();
		
	}
	
	//随机生成一条分支
	private void createBranch(TreeBranchNode checkingNode) {
		TreeBranchNode parentNode = null;	//父节点（临时变量）
		
		SubNodeMode subMode = null;	//某分支节点的子节点棋局（临时变量）
		int subNodeFlag = 1;	//子节点标志：1：极大节点，-1：极小节点
		
		TreeBranchNode branchNode = null;	//分支节点（临时变量）
		int[][] nodeMode = null;	//节点棋局（临时变量）
		
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
		
		createLeaf();	//生成该分支的第一个叶节点
		
	}
	
	//随机生成一个叶节点
	private void createLeaf() {
		TreeBranchNode parentNode = null;	//父节点（临时变量）
		int parentFlag = 1;		//父节点标志：1：极大节点，-1：极小节点
		
		TreeLeafNode leafNode = null;	//叶节点（临时变量）
		int[][] nodeMode = null;	//节点棋局（临时变量）
		EvaluateValue nodeValue = null;	//节点评价值（临时变量）
		
		if((thinkDepth-1)%2 == 0)	parentFlag = 1;
		else	parentFlag = -1;
		
		parentNode = stack.pop();
		(subNodeMap.get(parentNode.getNodeId())).makeRandomPredictMode();
		
		nodeMode = (subNodeMap.get(parentNode.getNodeId())).getPredictMode();
		leafNode = new TreeLeafNode(nodeId++, nodeMode, (subNodeMap.get(parentNode.getNodeId())).getNewMoveChess());
						
		//计算叶节点评价值
		nodeValue = new EvaluateValue(parentFlag, leafNode.getNodeMode(), leafNode.getNewMoveChess()[1]);
		leafNode.setNodeValue(nodeValue.getEvaluateValue());
		
		(subNodeMap.get(parentNode.getNodeId())).checkBeatChess();
		leafNode.setNodeMode((subNodeMap.get(parentNode.getNodeId())).getPredictMode());
		
		/*
		(leafNode.getNodeMode()).println();
		System.out.println("nodeValue: "+leafNode.getNodeValue()+"\n");
		*/
		
		if(parentNode.getNodeValue() == -99999999) {	//该分支上第一个被扩展的叶节点
			parentNode.setNodeValue(leafNode.getNodeValue());
			parentNode.setBestNextMoveChess(leafNode.getNewMoveChess());
		}
		else {
			if(parentFlag == 1) {	//父节点为极大节点
				if(leafNode.getNodeValue() > parentNode.getNodeValue()) {	//父节点评价值需要更新
					parentNode.setNodeValue(leafNode.getNodeValue());
					parentNode.setBestNextMoveChess(leafNode.getNewMoveChess());
				}
			}
			else {	//父节点为极小节点
				if(leafNode.getNodeValue() < parentNode.getNodeValue()) {	//父节点评价值需要更新
					parentNode.setNodeValue(leafNode.getNodeValue());
					parentNode.setBestNextMoveChess(leafNode.getNewMoveChess());
				}
			}
		}
		
		parentNode.addSubNode(leafNode);
		stack.push(parentNode);
		
	}
	
	//“有界的深度优先”策略生成新的子节点
	private void createNewSubNode() {
		
		TreeBranchNode checkingNode = null;	//检测节点（临时变量）
		int checkingNodeDepth = 0;	//检测节点深度
		int checkingNodeFlag = 1;		//检测节点标志：1：极大节点，-1：极小节点
		
		while(stack.peek() != root) {
			checkingNode = stack.peek();
			checkingNodeDepth = checkingNode.getDepth();
			
			if(checkingNodeDepth%2 == 0)	checkingNodeFlag = 1;
			else	checkingNodeFlag = -1;
			
			while((subNodeMap.get(checkingNode.getNodeId())).hasNotCheckedChess()) {	//检测节点还有未扩展的子节点
				if(!alphaBetaPruning(checkingNode, checkingNodeFlag)) {
					if(checkingNodeDepth < thinkDepth-1) {	//随机扩展下一层分支节点
						createBranch(checkingNode);
					}
					else
						createLeaf();	//随机扩展叶节点
				}
				else {	//剪枝
					//System.out.println("YBZ剪枝！");
					break;
				}
			}
			
			//检测节点已无未扩展的子节点，其评价值已确定，向上层祖先传递
			if((checkingNode.getPMenuComponent()).getNodeValue() == -99999999) {	//该分支上第一个被扩展的子节点
				(checkingNode.getPMenuComponent()).setNodeValue(checkingNode.getNodeValue());
				(checkingNode.getPMenuComponent()).setBestNextMoveChess(checkingNode.getNewMoveChess());
			}
			else {
				if(checkingNodeFlag == 1) {	//检测节点为极大节点
					if(checkingNode.getNodeValue() < (checkingNode.getPMenuComponent()).getNodeValue()) {	//上层祖先节点评价值需要更新
						(checkingNode.getPMenuComponent()).setNodeValue(checkingNode.getNodeValue());
						(checkingNode.getPMenuComponent()).setBestNextMoveChess(checkingNode.getNewMoveChess());
					}
				}
				else {	//检测节点为极小节点
					if(checkingNode.getNodeValue() > (checkingNode.getPMenuComponent()).getNodeValue()) {	//上层祖先节点评价值需要更新
						(checkingNode.getPMenuComponent()).setNodeValue(checkingNode.getNodeValue());
						(checkingNode.getPMenuComponent()).setBestNextMoveChess(checkingNode.getNewMoveChess());
					}
				}
			}
			
			stack.pop();
		}

	}
	
	//检查Alpha，Beta剪枝条件
	private boolean alphaBetaPruning(TreeBranchNode node, int flag) {
		if(flag == 1) {	//该节点为极大节点
			if(node.getNodeValue() >= (node.getPMenuComponent()).getNodeValue())	//该节点不再扩展，Beta剪枝
				return true;
			else	//该节点继续扩展
				return false;
		}
		else {	//该节点为极小节点
			if(node.getNodeValue() <= (node.getPMenuComponent()).getNodeValue())	//该节点不再扩展，Alpha剪枝
				return true;
			else	//该节点继续扩展
				return false;
		}
	}
	
}
