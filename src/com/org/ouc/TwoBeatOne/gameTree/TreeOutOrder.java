package com.org.ouc.TwoBeatOne.gameTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class TreeOutOrder {

	/**
	 * 深度优先遍历迭代器
	 * 
	 * @author YBZ
	 * @data 2011-12-08
	 */
	public static class DepthOrderIterator implements Iterator {
		//栈,用来深度遍历树节点,以便回溯
		Stack stack = new Stack();

		public DepthOrderIterator(GameTreeNode rootNode) {
			ArrayList list = new ArrayList();
			list.add(rootNode);

			//将根节点迭代器入栈
			stack.push(list.iterator());
		}

		//是否有下一元素
		public boolean hasNext() {
			//如果栈为空则返回,证明没有可遍历的元素
			if (stack.empty()) {
				return false;
			} else {
				//如果栈不为空,则取出栈顶元素(迭代器)
				Iterator iterator = (Iterator) stack.peek();

				//这里使用简单元素(即线性排列的元素,而不是树状结构的元素)的方式来遍历
				if (!iterator.hasNext()) {
					//如果取出迭代器已经遍历完成,则弹出迭代器,以便回退到上一(父)迭代器继续开妈以深度优先方式遍历
					stack.pop();

					//通过递归方式继续遍历父迭代器还未遍历到的节点元素
					return hasNext();
				} else {
					//如果找到了下一个元素,返回true
					return true;
				}
			}
		}

		//取下一元素
		public Object next() {
			//如果还有下一个元素,则先取到该元素所对应的迭代器引用,以便取得该节点元素
			if (hasNext()) {
				Iterator iterator = (Iterator) stack.peek();
				// 获取该节点元素
				GameTreeNode component = (GameTreeNode) iterator.next();

				//只有分支节点需进一步对子节点进行迭代
				if (component instanceof TreeBranchNode) {
					stack.push(component.getSubNodes().iterator());
				}

				//返回遍历得到的节点
				return component;
			} else {
				//如果栈为空
				return null;
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
}
