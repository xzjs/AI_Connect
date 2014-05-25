/**
 * ������֡����ֵ�ÿһ���߲��������ѡ���
 */

package com.org.ouc.TwoBeatOne;

import com.org.ouc.platform.Move;

public class RandomPlayer extends TwoBeatOnePlayer {

	public Move next(int[][] now) {
		int roleType = getTurn();

		int[][] s = now;

		while (true) {
			// ���ѡ��һ��λ�ã�����λ�����Ƿ��и����ֵ�����
			int i = (int) (Math.random() * TwoBeatOneBoard.XLen);
			int j = (int) (Math.random() * TwoBeatOneBoard.YLen);
			if (i == TwoBeatOneBoard.XLen)
				i--;
			if (j == TwoBeatOneBoard.YLen)
				j--;

			// ���ѡ���λ����ǡ���Ǹ����ֵ�����
			if (s[i][j] == roleType) {
				// �Ӹ�λ�õ����������ھ�����ѡһ��û�����ӵ�λ�ã���Ϊ��һ���߲�
				if (i > 0 && s[i - 1][j] == 0)
					return new Move(i, j, i - 1, j);

				if (i < TwoBeatOneBoard.XLen - 1 && s[i + 1][j] == 0)
					return new Move(i, j, i + 1, j);

				if (j < TwoBeatOneBoard.YLen - 1 && s[i][j + 1] == 0)
					return new Move(i, j, i, j + 1);

				if (j > 0 && s[i][j - 1] == 0)
					return new Move(i, j, i, j - 1);
			}
		}

	}

}
