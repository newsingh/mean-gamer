package com.opensource.tic.tac.toe.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author abhinav
 */
public class GameEngine {

	// playing board
	List<Integer> board = new ArrayList<Integer>();

	public static final int COMPUTER_WINNING_TOTAL = -3;

	public static final int PLAYER_WINNING_TOTAL = 3;

	public static final int COMPUTER_CANT_MOVE = -100;

	public void resetBoard() {
		board.clear();
		for (int i = 0; i < 9; i++) {
			board.add(0);
		}
	}

	public void movePlayer(int moveIndex) {
		board.set(moveIndex, 1);
	}

	public int moveComputer() {
		if (isGameOver()) {
			// do not move
			return COMPUTER_CANT_MOVE;
		}

		// attempt winning move
		int moveIndex = attemptCrucialComputerMove(COMPUTER_WINNING_TOTAL + 1);
		if (moveIndex == COMPUTER_CANT_MOVE) {
			// attempt stopper move
			moveIndex = attemptCrucialComputerMove(PLAYER_WINNING_TOTAL - 1);
		}
		if (moveIndex == COMPUTER_CANT_MOVE) {
			// TODO Someday, switch to heuristic computer move instead of random
			// TODO let the player have a field day till then

			// random move
			moveIndex = getRandomComputerMove();
		}

		board.set(moveIndex, -1);

		return moveIndex;
	}

	public boolean isGameOver() {
		if (isGameDraw() || hasPlayerWon() || hasComputerWon()) {
			return true;
		}
		return false;
	}

	public boolean isGameDraw() {
		for (int i = 0; i < 9; i++) {
			if (board.get(i) == 0) {
				return false;
			}
		}
		return true;
	}

	public boolean hasPlayerWon() {
		return hasWon(PLAYER_WINNING_TOTAL);
	}

	public boolean hasComputerWon() {
		return hasWon(COMPUTER_WINNING_TOTAL);
	}

	private boolean hasWon(int winningTotal) {
		if (getfirstRowTotal() == winningTotal) {
			return true; // first row
		} else if (getSecondRowTotal() == winningTotal) {
			return true; // second row
		} else if (getThirdRowTotal() == winningTotal) {
			return true; // third row
		} else if (getFirstColumnTotal() == winningTotal) {
			return true; // first column
		} else if (getSecondColumnTotal() == winningTotal) {
			return true; // second column
		} else if (getThirdColumnTotal() == winningTotal) {
			return true; // third column
		} else if (getLeftDiagonalTotal() == winningTotal) {
			return true; // diagonal
		} else if (getRightDiagonalTotal() == winningTotal) {
			return true; // other diagonal
		}
		return false;
	}

	/**
	 * @param crucialTotal
	 *            2 to make stopper move, -2 to make winning move
	 * @return move-index if moved, -1 if not moved
	 */
	private int attemptCrucialComputerMove(int crucialTotal) {
		if (getfirstRowTotal() == crucialTotal) {
			return getCrucialComputerMove(0, 1, 2); // first row
		} else if (getSecondRowTotal() == crucialTotal) {
			return getCrucialComputerMove(3, 1, 5); // second row
		} else if (getThirdRowTotal() == crucialTotal) {
			return getCrucialComputerMove(6, 1, 8); // third row
		} else if (getFirstColumnTotal() == crucialTotal) {
			return getCrucialComputerMove(0, 3, 6); // first column
		} else if (getSecondColumnTotal() == crucialTotal) {
			return getCrucialComputerMove(1, 3, 7); // second column
		} else if (getThirdColumnTotal() == crucialTotal) {
			return getCrucialComputerMove(2, 3, 8); // third column
		} else if (getLeftDiagonalTotal() == crucialTotal) {
			return getCrucialComputerMove(0, 4, 8); // diagonal
		} else if (getRightDiagonalTotal() == crucialTotal) {
			return getCrucialComputerMove(2, 2, 6); // other diagonal
		}
		return COMPUTER_CANT_MOVE;

	}

	/**
	 * Make crucial move
	 * 
	 * @return move-index if moved, COMPUTER_CANT_MOVE if not moved
	 */
	private int getCrucialComputerMove(int start, int increment, int end) {
		for (int i = start; i <= end; i = i + increment) {
			if (board.get(i) == 0) {
				return i;
			}
		}
		// this never happens
		return COMPUTER_CANT_MOVE;
	}

	private int getRandomComputerMove() {
		if (isGameDraw()) {
			return COMPUTER_CANT_MOVE;
		}
		Random r = new Random();
		int randomPosition = r.nextInt(9);
		while (true) {
			randomPosition = r.nextInt(9);
			if (board.get(randomPosition) == 0) {
				break;
			}
		}
		return randomPosition;
	}

	private int getfirstRowTotal() {
		return board.get(0) + board.get(1) + board.get(2);
	}

	private int getSecondRowTotal() {
		return board.get(3) + board.get(4) + board.get(5);
	}

	private int getThirdRowTotal() {
		return board.get(6) + board.get(7) + board.get(8);
	}

	private int getFirstColumnTotal() {
		return board.get(0) + board.get(3) + board.get(6);
	}

	private int getSecondColumnTotal() {
		return board.get(1) + board.get(4) + board.get(7);
	}

	private int getThirdColumnTotal() {
		return board.get(2) + board.get(5) + board.get(8);
	}

	private int getLeftDiagonalTotal() {
		return board.get(0) + board.get(4) + board.get(8);
	}

	private int getRightDiagonalTotal() {
		return board.get(2) + board.get(4) + board.get(6);
	}

}
