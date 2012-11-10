package com.opensource.tic.tac.toe.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.opensource.tic.tac.toe.game.R;

/**
 * @author abhinav
 *
 */
public class TicTacToe extends Activity {
	
	public static final int SYSTEM_WINNING_TOTAL = -3;
	
	public static final int PLAYER_WINNING_TOTAL = 3;
	
	private boolean computerPlaysFirst = true;
	
	private int playerWinCount = 0;
	
	private int computerWinCount = 0;
	
	private int drawCount = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        prepare();
    }
    
    private void prepare()
    {
    	cleanDisplay();
    	List<Integer> board = new ArrayList<Integer>();
        resetBoard(board);
        List<Button> buttonList = new ArrayList<Button>();
        resetButtons(buttonList);
        setListeners(buttonList, board);
        if(computerPlaysFirst)
        {
        	computerMove(buttonList, board);        	
        }
        computerPlaysFirst = !computerPlaysFirst;
    }
    
    private void cleanDisplay()
    {
    	// result text
    	TextView resultView = (TextView) findViewById(R.id.resultText);
    	resultView.setText("Player to move...");
    	resultView.setBackgroundColor(Color.BLACK);
    	resultView.setTextColor(Color.WHITE);
    	resultView.setTextScaleX(1);
    	resultView.setClickable(false);
    	resultView.setEnabled(false);
    	resultView.setCursorVisible(false);
    	
    	// player win count
    	TextView playerWinCountView = (TextView) findViewById(R.id.playerResultCountText);
    	playerWinCountView.setText("Player wins:	  " + playerWinCount);
    	playerWinCountView.setBackgroundColor(Color.BLUE);
    	playerWinCountView.setTextColor(Color.WHITE);
    	playerWinCountView.setTextScaleX(1);
    	playerWinCountView.setClickable(false);
    	playerWinCountView.setEnabled(false);
    	playerWinCountView.setCursorVisible(false);
    	
    	// computer win count
    	TextView computerWinCountView = (TextView) findViewById(R.id.computerResultCountText);
    	computerWinCountView.setText("Computer wins:	" + computerWinCount);
    	computerWinCountView.setBackgroundColor(Color.RED);
    	computerWinCountView.setTextColor(Color.WHITE);
    	computerWinCountView.setTextScaleX(1);
    	computerWinCountView.setClickable(false);
    	computerWinCountView.setEnabled(false);
    	computerWinCountView.setCursorVisible(false);
    	
    	// draw count
    	TextView drawCountView = (TextView) findViewById(R.id.drawResultCountText);
    	drawCountView.setText("Draw:	         " + drawCount);
    	drawCountView.setBackgroundColor(Color.DKGRAY);
    	drawCountView.setTextColor(Color.WHITE);
    	drawCountView.setTextScaleX(1);
    	drawCountView.setClickable(false);
    	drawCountView.setEnabled(false);
    	drawCountView.setCursorVisible(false);   	
    	
    }
    
    private void resetBoard(List<Integer> board)
    {
    	board.clear();
    	for(int i=0;i<10;i++)
    	{
    		board.add(new Integer(0));
    	}
    }
    
    private void resetButtons(List<Button> buttonList)
    {
    	buttonList.clear();
    	buttonList.add((Button) findViewById(R.id.button0));
    	buttonList.add((Button) findViewById(R.id.button1));
    	buttonList.add((Button) findViewById(R.id.button2));
    	buttonList.add((Button) findViewById(R.id.button3));
    	buttonList.add((Button) findViewById(R.id.button4));
    	buttonList.add((Button) findViewById(R.id.button5));
    	buttonList.add((Button) findViewById(R.id.button6));
    	buttonList.add((Button) findViewById(R.id.button7));
    	buttonList.add((Button) findViewById(R.id.button8));    	
    }
    
    private void setListeners(final List<Button> buttonList, final List<Integer> board)
    {
    	// playing  button listeners
    	for(final Button button : buttonList)
    	{
    		button.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				button.setText("O");
    				button.setBackgroundColor(Color.BLUE);
    				board.set(getPosition(button, buttonList), new Integer(1));
    				
    				boolean isGameOver = computerMove(buttonList, board);
    				if(isGameOver)
    				{
    					disableAllPlayingButtons(buttonList);
    				}
    			}
    		});
    	}
    	// reset button listener
    	Button resetButton = (Button) findViewById(R.id.resetGame);
    	resetButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 setContentView(R.layout.main);
				 prepare();				
			}
		});
    	
    }
    
    private int getPosition(Button button, List<Button> buttonList)
    {
    	int position = 0;
    	for(Button b : buttonList)
    	{
    		if(button.getId() == b.getId()) 
    		{
    			return position;
    		}
    		position++;
    	}    	
    	return -1;
    }
    
    private void disableAllPlayingButtons(List<Button> buttonList)
    {
		for(Button b : buttonList)
		{
			b.setEnabled(false);
		} 
    }
    
    private boolean isGameOver(List<Button> buttonList, List<Integer> board)
    {
    	boolean isGameOver = false;
    	
    	// check for draw
		if(isDraw(buttonList))
    	{
    		// declare draw   
			drawCount++;
    		isGameOver = true;
    		display("Game draw ", isGameOver, Color.BLACK, 0);
    	}
    	
    	// check if player won
		if(hasWon(buttonList, board, PLAYER_WINNING_TOTAL))
		{
    		// declare player win
			playerWinCount++;
    		isGameOver = true;
    		display("Player won ", isGameOver, Color.BLUE, 1);
		}
		
    	// check if computer won
		if(hasWon(buttonList, board, SYSTEM_WINNING_TOTAL))
		{
    		// declare computer win 
			computerWinCount++;
    		isGameOver = true;
    		display("Computer won", isGameOver, Color.RED, 2);
		}
		return isGameOver;
    }

    
    private boolean computerMove(List<Button> buttonList, List<Integer> board)
    {
    	// check for win before move
    	if(isGameOver(buttonList, board))
    	{
    		return true;
    	}
		
    	// attempt winning move
    	boolean moved = move(buttonList, board, SYSTEM_WINNING_TOTAL + 1);
    	if(!moved)
    	{
    		// attempt stopper move
    		moved = move(buttonList, board, PLAYER_WINNING_TOTAL - 1);
    	}
    	if(!moved)
    	{
        	// TODO Someday, switch to heuristic computer move instead of random
        	// TODO let the player have a field day till then
    		
    		// make random move
    		makeRandomMove(buttonList, board);
    	}
    	// check for win post move
    	if(isGameOver(buttonList, board))
    	{
    		return true;
    	}
    	return false;
    }
    
    private void makeRandomMove(List<Button> buttonList, List<Integer> board)
    {
    	Random r = new Random();
    	int randomPosition = r.nextInt(9);
    	while(true)
    	{
    		randomPosition = r.nextInt(9);
			if(board.get(randomPosition) == 0)
			{
				// make random move
				board.set(randomPosition, -1);
				buttonList.get(randomPosition).setText("X");
				buttonList.get(randomPosition).setBackgroundColor(Color.RED);
				break;
			}
    	}
    }
    
    private boolean isDraw(List<Button> buttonList)
    {
    	for(Button button : buttonList)
    	{
    		CharSequence buttonText = button.getText();
    		if("".equals(buttonText) || buttonText == null)
    		{
    			return false;
    		}
    	}
    	return true;
    }
    
    /**
     * @param crucialTotal 2 to make stopper move, -2 to make winning move
     */
    private boolean move(List<Button> buttonList, List<Integer> board, int crucialTotal)
    {
    	if( board.get(0) + board.get(1) + board.get(2) == crucialTotal)
    	{
    		return checkAndMakeCrucialMove(buttonList, board, 0, 1, 2);
    	}
    	else if(board.get(3) + board.get(4) + board.get(5) == crucialTotal)
    	{
    		return checkAndMakeCrucialMove(buttonList, board, 3, 1, 5);
    	}
    	else if(board.get(6) + board.get(7) + board.get(8) == crucialTotal)
    	{
    		return checkAndMakeCrucialMove(buttonList, board, 6, 1, 8);
    	}
    	else if(board.get(0) + board.get(3) + board.get(6) == crucialTotal)
    	{
    		return checkAndMakeCrucialMove(buttonList, board, 0, 3, 6);
    	}
    	else if(board.get(1) + board.get(4) + board.get(7) == crucialTotal)
    	{
    		return checkAndMakeCrucialMove(buttonList, board, 1, 3, 7);
    	}
    	else if(board.get(2) + board.get(5) + board.get(8) == crucialTotal)
    	{
    		return checkAndMakeCrucialMove(buttonList, board, 2, 3, 8);
    	}
    	else if(board.get(0) + board.get(4) + board.get(8) == crucialTotal)
    	{
    		return checkAndMakeCrucialMove(buttonList, board, 0, 4, 8);
    	}
    	else if(board.get(2) + board.get(4) + board.get(6) == crucialTotal)
    	{
    		return checkAndMakeCrucialMove(buttonList, board, 2, 2, 6);
    	}
    	return false;
    		
    }
    
    private boolean checkAndMakeCrucialMove(List<Button> buttonList, List<Integer> board, int start, int increment, int end)
    {
		for(int i=start;i<=end;i=i+increment)
		{
			if(board.get(i) == 0)
			{
				// make crucial move
				board.set(i, -1);
				buttonList.get(i).setText("X");
				buttonList.get(i).setBackgroundColor(Color.RED);
				return true;
			}
		}
		return false;
    }
    
    private boolean hasWon(List<Button> buttonList, List<Integer> board, int crucialTotal)
    {
    	if( board.get(0) + board.get(1) + board.get(2) == crucialTotal)
    	{
    		return true;
    	}
    	else if(board.get(3) + board.get(4) + board.get(5) == crucialTotal)
    	{
    		return true;
    	}
    	else if(board.get(6) + board.get(7) + board.get(8) == crucialTotal)
    	{
    		return true;
    	}
    	else if(board.get(0) + board.get(3) + board.get(6) == crucialTotal)
    	{
    		return true;
    	}
    	else if(board.get(1) + board.get(4) + board.get(7) == crucialTotal)
    	{
    		return true;
    	}
    	else if(board.get(2) + board.get(5) + board.get(8) == crucialTotal)
    	{
    		return true;
    	}
    	else if(board.get(0) + board.get(4) + board.get(8) == crucialTotal)
    	{
    		return true;
    	}
    	else if(board.get(2) + board.get(4) + board.get(6) == crucialTotal)
    	{
    		return true;
    	}
    	return false;    		
    }
    
    private TextView getPlayerWinCountTextView()
    {
    	return (TextView) findViewById(R.id.playerResultCountText);
    }
    
    private TextView getComputerWinCountTextView()
    {
    	return (TextView) findViewById(R.id.computerResultCountText);
    }
    
    private TextView getDrawCountTextView()
    {
    	return (TextView) findViewById(R.id.drawResultCountText);
    }
    
    private void display(String displayString, boolean isGameOver, int color, int whoWon)
    {
    	TextView resultView = (TextView) findViewById(R.id.resultText);
    	resultView.setText(displayString);
    	if(isGameOver)
    	{
    		resultView.setBackgroundColor(Color.BLACK);
    	}
    	if(whoWon == 0)
    	{
    		// draw
    		getDrawCountTextView().setText("Draw:	         " + drawCount);
    	}
    	if(whoWon == 1)
    	{
    		// player won
    		getPlayerWinCountTextView().setText("Player wins:	  " + playerWinCount);
    	}
    	if(whoWon == 2)
    	{
    		// computer won
    		getComputerWinCountTextView().setText("Computer wins:	" + computerWinCount);
    	}
    	
    }

}