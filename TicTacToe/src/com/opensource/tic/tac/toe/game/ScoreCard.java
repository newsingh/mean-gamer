package com.opensource.tic.tac.toe.game;

/**
 * @author abhinav
 * 
 */
public class ScoreCard {

	private int drawCount = 0;

	private int playerWinCount = 0;

	private int computerWinCount = 0;

	public void resetScoreCard() {
		drawCount = 0;
		playerWinCount = 0;
		computerWinCount = 0;
	}

	public void registerDraw() {
		drawCount += 1;
	}

	public void registerPlayerWin() {
		playerWinCount += 1;
	}

	public void registerComputerWin() {
		computerWinCount += 1;
	}

	public int getDrawCount() {
		return drawCount;
	}

	public int getPlayerWinCount() {
		return playerWinCount;
	}

	public int getComputerWinCount() {
		return computerWinCount;
	}

}
