package com.org.ouc.TwoBeatOne.gameTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class TreeOutOrder {

	/**
	 * ������ȱ���������
	 * 
	 * @author YBZ
	 * @data 2011-12-08
	 */
	public static class DepthOrderIterator implements Iterator {
		//ջ,������ȱ������ڵ�,�Ա����
		Stack stack = new Stack();

		public DepthOrderIterator(GameTreeNode rootNode) {
			ArrayList list = new ArrayList();
			list.add(rootNode);

			//�����ڵ��������ջ
			stack.push(list.iterator());
		}

		//�Ƿ�����һԪ��
		public boolean hasNext() {
			//���ջΪ���򷵻�,֤��û�пɱ�����Ԫ��
			if (stack.empty()) {
				return false;
			} else {
				//���ջ��Ϊ��,��ȡ��ջ��Ԫ��(������)
				Iterator iterator = (Iterator) stack.peek();

				//����ʹ�ü�Ԫ��(���������е�Ԫ��,��������״�ṹ��Ԫ��)�ķ�ʽ������
				if (!iterator.hasNext()) {
					//���ȡ���������Ѿ��������,�򵯳�������,�Ա���˵���һ(��)����������������������ȷ�ʽ����
					stack.pop();

					//ͨ���ݹ鷽ʽ������������������δ�������Ľڵ�Ԫ��
					return hasNext();
				} else {
					//����ҵ�����һ��Ԫ��,����true
					return true;
				}
			}
		}

		//ȡ��һԪ��
		public Object next() {
			//���������һ��Ԫ��,����ȡ����Ԫ������Ӧ�ĵ���������,�Ա�ȡ�øýڵ�Ԫ��
			if (hasNext()) {
				Iterator iterator = (Iterator) stack.peek();
				// ��ȡ�ýڵ�Ԫ��
				GameTreeNode component = (GameTreeNode) iterator.next();

				//ֻ�з�֧�ڵ����һ�����ӽڵ���е���
				if (component instanceof TreeBranchNode) {
					stack.push(component.getSubNodes().iterator());
				}

				//���ر����õ��Ľڵ�
				return component;
			} else {
				//���ջΪ��
				return null;
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
}
