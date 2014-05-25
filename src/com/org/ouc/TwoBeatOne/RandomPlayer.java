/**
 * 随机棋手。棋手的每一个走步都是随机选择的
 */

package com.org.ouc.TwoBeatOne;

import com.org.ouc.platform.Move;

public class RandomPlayer extends TwoBeatOnePlayer {

	public Move next(int[][] now) {
		int roleType = getTurn();

		int[][] s = now;

		while (true) {
			// 随机选择一个位置，看该位置上是否有该棋手的棋子
			int i = (int) (Math.random() * TwoBeatOneBoard.XLen);
			int j = (int) (Math.random() * TwoBeatOneBoard.YLen);
			if (i == TwoBeatOneBoard.XLen)
				i--;
			if (j == TwoBeatOneBoard.YLen)
				j--;

			// 如果选择的位置上恰好是该棋手的棋子
			if (s[i][j] == roleType) {
				// 从该位置的上下左右邻居中任选一个没有棋子的位置，作为下一个走步
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
