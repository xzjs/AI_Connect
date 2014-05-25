package com.org.ouc.ConnectSix;

import java.util.ArrayList;

import com.org.ouc.TwoBeatOne.MoveResp;
import com.org.ouc.platform.Move;
import com.org.ouc.platform.RuleInterface;

public class ConnectSixRule implements RuleInterface{

	@Override
	public boolean isLegal(Move move) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hasWinner(int[][] status) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean check(int[][] status, Move move, int roleType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<MoveResp> getMoveResps(int[][] status, Move move,
			int roleType) {
		// TODO Auto-generated method stub
		return null;
	}
}
