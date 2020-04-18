package connectfour;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * This is the ConnectFour class. This class contains
 * all the necessary methods for playing a game of connect four
 * with two players or a player and the computer. It contains 4 GUI
 * stages and 3 of them are the settings for the game and the other
 * is the game itself.
 * @author Yasir
 *
 */
public class ConnectFour{
	/**
	 * Array of Cell classes and it holds
	 * all the cells that are being used in the game.
	 */
	private Cell[][] gameCells;
	
	/**
	 * The size of the game board as well as each dimension of the
	 * gameCells variable.
	 */
	private int boardSize;
	
	/**
	 * 0 if the first player is playing and 1 if the second
	 * player is playing. This variable decides who is the winner
	 * and which kind of cell should the game place on a button click.
	 */
	private int currentPlayer;
	
	/**
	 * Is the game being played against the computer or another player.
	 */
	private boolean isVsComputer;
	
	/**
	 * Has the game reached an end status.
	 */
	private boolean isGameEnded;
	
	/**
	 * Is the computer playing first or the player.
	 */
	private boolean isComputerGoFirst;
	
	/**
	 * Main windows that holds all the buttons, labels etc.
	 * This JFrame is being cleared each time another state of
	 * GUI is reached.
	 */
	private JFrame menuFrame;
	
	/**
	 * Image of the blue player.
	 */
	private Image blueDot;
	
	/**
	 * Image of the blue player when it is one of the cells
	 * that caused the victory.
	 */
	private Image blueDotWin;
	
	/**
	 * Image of the red player.
	 */
	private Image redDot;
	
	/**
	 * Image of the red player when it is one of the cells
	 * that caused the victory.
	 */
	private Image redDotWin;
	
	/**
	 * No parameter constructor, loads up the image files and
	 * sets the fields to initial values.
	 */
	ConnectFour(){
		this.isVsComputer = false;
		this.isGameEnded = false;
		this.isComputerGoFirst = false;
		this.currentPlayer = 0;
		this.boardSize = 4;
		this.gameCells = new Cell[boardSize][boardSize];
		try {
			blueDot = ImageIO.read(getClass().getResource("resource/blueDot.png"));
			blueDotWin = ImageIO.read(getClass().getResource("resource/blueDotWin.png"));
			redDot = ImageIO.read(getClass().getResource("resource/redDot.png"));
			redDotWin = ImageIO.read(getClass().getResource("resource/redDotWin.png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "One or more image file(s) can not be found", "ERROR", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	/**
	 * Calls the first GUI stage that brings out the game type
	 * selection window. It is simpler to use this method from another method.
	 */
	public void playGame() {
		readGameType();
	}
	
	/**
	 * This is the Cell class that holds the one game cell
	 * It also has a JButton coming with it so every single game
	 * cell is also a button.
	 * @author Yasir
	 *
	 */
	private class Cell{
		/**
		 * No parameter constructor. Simply sets all the fields to initial values
		 * and gives its button its capability. This is an inner class because it
		 * also checks for the private fields of the outer class and calls methods of it.
		 */
		Cell(){
			val = 0;
			rowIndex = 0;
			colIndex = 0;
			cellButton = new JButton("");
			cellButton.setPreferredSize(new Dimension(60, 60));
			cellButton.setBackground(null);
			cellButton.setContentAreaFilled( false );
			cellButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(isVsComputer) {
						play(rowIndex, colIndex);
						play();
					} else {
						play(rowIndex, colIndex);
					}
				}
			});
		}
		
		/**
		 * 0 if the cell is empty.
		 * 1 if the cell has a red checker
		 * 2 if the cell has a blue checker
		 * this field helps a lot of methods to determine
		 * their actions. For example the AI calculates its moves
		 * based on this field and the method checks if the game
		 * has ended also uses this field.
		 */
		public int val;
		
		/**
		 * Which row is the cell in.
		 */
		public int rowIndex;
		
		/**
		 * Which column is the cell in.
		 */
		public int colIndex;
		
		/**
		 * The button component of the cell.
		 */
		public JButton cellButton;
	}

	/**
	 * Creates the necessary components for the window that
	 * asks the player if it wants to play against another player
	 * or the computer. Changes fields according to the button press
	 * and advances to the next game state after that.
	 */
	private void readGameType() {
		menuFrame = new JFrame("Connect Four");
		JPanel mainPanel = new JPanel();
		JPanel menuPanel = new JPanel(new GridLayout(0, 2));
		JLabel infoLabel = new JLabel("Please Select a Game Mode", SwingConstants.CENTER);
		JButton pvpButton = new JButton("Player VS Player");
		JButton pvcButton = new JButton("Player VS Computer");
		
		pvpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isVsComputer = false;
				isGameEnded = false;
				menuFrame.setVisible(false);
				readBoardSize();
			}
		});
		
		pvcButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isVsComputer = true;
				isGameEnded = false;
				menuFrame.setVisible(false);
				readStartingPlayer();
			}
		});
		
	    mainPanel.setLayout(new BorderLayout(10, 10));
	    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    
	    menuPanel.add(pvpButton);
	    menuPanel.add(pvcButton);
	    
	    mainPanel.add(menuPanel, BorderLayout.CENTER);
	    mainPanel.add(infoLabel, BorderLayout.PAGE_START);
	    
	    menuFrame.setResizable(false);
	    menuFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
	    menuFrame.pack();
	    menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    menuFrame.setLocationRelativeTo(null);
	    menuFrame.setVisible(true);
	}
	
	/**
	 * Creates the necessary components for the window
	 * that asks if the computer or the player goes first
	 * in the Player VS Computer game mode. Changes variables
	 * according to the button that pressed and advances to the
	 * next game state after that.
	 */
	private void readStartingPlayer() {
		menuFrame = new JFrame("Connect Four");
		JPanel mainPanel = new JPanel();
		JPanel menuPanel = new JPanel(new GridLayout(0, 2));
		JLabel infoLabel = new JLabel("Please Select Who Goes First", SwingConstants.CENTER);
		JButton pGoesFirst = new JButton("Player");
		JButton cGoesFirst = new JButton("Computer");
		
		pGoesFirst.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isComputerGoFirst = false;
				menuFrame.setVisible(false);
				readBoardSize();
			}
		});
		
		cGoesFirst.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isComputerGoFirst = true;
				menuFrame.setVisible(false);
				readBoardSize();
			}
		});
		
	    mainPanel.setLayout(new BorderLayout(10, 10));
	    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    
	    menuPanel.add(pGoesFirst);
	    menuPanel.add(cGoesFirst);
	    
	    mainPanel.add(menuPanel, BorderLayout.CENTER);
	    mainPanel.add(infoLabel, BorderLayout.PAGE_START);
	    
	    menuFrame.setResizable(false);
	    menuFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
	    menuFrame.pack();
	    menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    menuFrame.setLocationRelativeTo(null);
	    menuFrame.setVisible(true);
	}
	
	/**
	 * Creates the necessary components for the window that
	 * the board size input being taken. Controls errors
	 * for invalid inputs and changes variables according to
	 * the given input and advances to the game state after
	 * that.
	 */
	private void readBoardSize() {
		menuFrame = new JFrame("Connect Four");
		JPanel mainPanel = new JPanel();
		JPanel menuPanel = new JPanel(new GridLayout(0, 2));
		JLabel infoLabel = new JLabel("Please Type a Board Size and Press Okay", SwingConstants.CENTER);
		JTextField boardSizeField = new JTextField();
		JButton okayButton = new JButton("Okay");
		
		okayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int givenInput = -128;
				try {
					Integer.parseInt(boardSizeField.getText());
					givenInput = Integer.parseInt(boardSizeField.getText());
				} catch(Exception xpct) {
					JOptionPane.showMessageDialog(menuFrame, "Please Enter a Number", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				if(!(givenInput < 4)) {
					menuFrame.setVisible(false);
					boardSize = givenInput;
					gameCells = new Cell[boardSize][boardSize];
					for(int i=0; i<boardSize; i++) {
						for(int j=0; j<boardSize; j++) {
							gameCells[i][j] = new Cell();
							gameCells[i][j].rowIndex = i;
							gameCells[i][j].colIndex = j;
						}
					}
					currentPlayer = 0;
					inGame();
				} else {
					JOptionPane.showMessageDialog(menuFrame, "Please Enter a Value Higher Than 4", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
	    mainPanel.setLayout(new BorderLayout(10, 10));
	    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    
	    menuPanel.add(boardSizeField);
	    menuPanel.add(okayButton);
	    
	    mainPanel.add(menuPanel, BorderLayout.CENTER);
	    mainPanel.add(infoLabel, BorderLayout.PAGE_START);
	    
	    menuFrame.setResizable(false);
	    menuFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
	    menuFrame.pack();
	    menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    menuFrame.setLocationRelativeTo(null);
	    menuFrame.setVisible(true);
	}
	
	/**
	 * Creates the necessary components for the main window that 
	 * the actual game is being played. Also plays a move for the
	 * computer if the "Computer Goes First" option is selected
	 * in the game menu.
	 */
	private void inGame() {
		menuFrame = new JFrame("Connect Four");
		JPanel mainPanel = new JPanel();
		JPanel menuPanel = new JPanel(new GridLayout(this.boardSize, this.boardSize));
		
	    mainPanel.setLayout(new BorderLayout(20, 20));
	    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	    
	    for(int i=0; i<this.boardSize; i++) {
	    	for(int j=0; j<this.boardSize; j++) {
	    		menuPanel.add(gameCells[i][j].cellButton);
	    	}
	    }
	    
	    if(isVsComputer && isComputerGoFirst) {
	    	play();
	    }
	    mainPanel.add(menuPanel, BorderLayout.CENTER);
	    
	    menuFrame.setResizable(false);
	    menuFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
	    menuFrame.pack();
	    menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    menuFrame.setLocationRelativeTo(null);
	    menuFrame.setVisible(true);
	}
	
	/**
	 * Plays a move for the player based on the button that
	 * the player clicked.
	 * @param rowIndex row index of the button that the player clicked
	 * @param colIndex column index of the button that the player clicked
	 */
	private void play(int rowIndex, int colIndex) {
		int i;
		for(i=0; i<boardSize; i++) {
			if(gameCells[i][colIndex].val != 0)
				break;
		}
		if(i!=0){
	        if(currentPlayer == 0) {
	        	gameCells[i-1][colIndex].cellButton.setIcon(new ImageIcon(redDot));
	        	gameCells[i-1][colIndex].val = 1;
	        } else {
	        	gameCells[i-1][colIndex].cellButton.setIcon(new ImageIcon(blueDot));
	            gameCells[i-1][colIndex].val = 2;
	        }
	        if(!isGameEnded)
	        	checkWin();
	        if(!isVsComputer) {
		    	if(currentPlayer == 1)
		    		currentPlayer = 0;
		    	else
		    		currentPlayer = 1;
		    }
		}
	}
	
	/**
	 * Starts at a point, increases result value by one
	 * if a friendly cell is in sight and decreases result value
	 * by two if a adversary cell is in sight and changes to another
	 * cell by xIncrease and yIncrease variables.
	 * @param pointX starting x coordinate
	 * @param pointY starting y coordinate
	 * @param xIncrease in which direction should the x coordinate change
	 * @param yIncrease in which direction should the y coordinate change
	 * @param maxValueCurrentMove current maximum value of the moves calculated before this call
	 * @param playerMove is the AI being played by player 1 or player 2
	 * @return maximum effectiveness of the possible cases
	 */
	int calcMoveVal(int pointX, int pointY, int xIncrease, int yIncrease, int maxValueCurrentMove, int playerMove) {
		int i;
		int result = 0;
		int enemyMove;
		if(playerMove == 1) {
			enemyMove = 2;
		} else {
			enemyMove = 1;
		}
	    for(i=0; i<4; i++){
	        if(pointX >= 0 && pointX < boardSize && pointY >= 0 && pointY < boardSize){
	            if(gameCells[pointX][pointY].val == playerMove)
	                result++;
	            else if(gameCells[pointX][pointY].val == enemyMove)
	                result-=2;
	        }
	        pointX+=xIncrease;
	        pointY+=yIncrease;
	    }

	    /*
	    	If there is a move that the opponent can do to finish the game, deny it. 
	      	Otherwise if the value of the current move is greater than max value, change the max.
	    */
	    if(result == -5)
	        result = 4;
	    if(result > maxValueCurrentMove)
			maxValueCurrentMove = result;
	    return maxValueCurrentMove;
	}
	
	/**
	 * Since each move can bring a player closer to winning
	 * by one of the four cases(vertical, horizontal, diagonal left to right
	 * and diagonal right to left), this method takes a cell that being played
	 * imaginary by computer and calculates its effectiveness on that four
	 * cases.
	 * @param startX starting x coordinate of the control
	 * @param startY starting y coordinate of the control
	 * @param playerMove is the AI being played as player 1 or player 2
	 * @return maximum effectiveness of the possible cases
	 */
	private int allMoveVals(int startX, int startY, int playerMove) {
		int maxValueCurrentMove = 0;
		//Horizontal effect of the move.
		maxValueCurrentMove = calcMoveVal(startX-1, startY-3, 0, 1, maxValueCurrentMove, playerMove);
		maxValueCurrentMove = calcMoveVal(startX-1, startY-2, 0, 1, maxValueCurrentMove, playerMove);
		maxValueCurrentMove = calcMoveVal(startX-1, startY-1, 0, 1, maxValueCurrentMove, playerMove);
		maxValueCurrentMove = calcMoveVal(startX-1, startY+0, 0, 1, maxValueCurrentMove, playerMove);

	    //Vertical effectiveness of the move.
		maxValueCurrentMove = calcMoveVal(startX-4, startY+0, 1, 0, maxValueCurrentMove, playerMove);
		maxValueCurrentMove = calcMoveVal(startX-3, startY+0, 1, 0, maxValueCurrentMove, playerMove);
		maxValueCurrentMove = calcMoveVal(startX-2, startY+0, 1, 0, maxValueCurrentMove, playerMove);
		maxValueCurrentMove = calcMoveVal(startX-1, startY+0, 1, 0, maxValueCurrentMove, playerMove);

	    //Diagonal from left to right effectiveness of the move.
		maxValueCurrentMove = calcMoveVal(startX-4, startY-3, 1, 1, maxValueCurrentMove, playerMove);
		maxValueCurrentMove = calcMoveVal(startX-3, startY-2, 1, 1, maxValueCurrentMove, playerMove);
		maxValueCurrentMove = calcMoveVal(startX-2, startY-1, 1, 1, maxValueCurrentMove, playerMove);
		maxValueCurrentMove = calcMoveVal(startX-1, startY+0, 1, 1, maxValueCurrentMove, playerMove);

	    //Diagonal from right to left effectiveness of the move.
		maxValueCurrentMove = calcMoveVal(startX-4, startY+3, 1, -1, maxValueCurrentMove, playerMove);
		maxValueCurrentMove = calcMoveVal(startX-3, startY+2, 1, -1, maxValueCurrentMove, playerMove);
		maxValueCurrentMove = calcMoveVal(startX-2, startY+1, 1, -1, maxValueCurrentMove, playerMove);
		maxValueCurrentMove = calcMoveVal(startX-1, startY+0, 1, -1, maxValueCurrentMove, playerMove);	
	    return maxValueCurrentMove;
	}
	
	/**
	 * Plays a move for the computer. Calculates "value"
	 * of every possible move it can make using helper methods
	 * and decides on a cell.
	 */
	private void play() {
		int i, j, maxValueIndex = 0, maxValueAllMoves = 0;
		int moveValues[];
		moveValues = new int[16];
	    for(j=0; j<boardSize; j++){
	        //Iterate through all moves.
	        for(i=0; i<boardSize; i++)
	            if(gameCells[i][j].val != 0)
	                break;
	        if(i!=0){
	            gameCells[i-1][j].val = 2;
	            //Calculate the value of each move.
	            moveValues[j] = allMoveVals(i, j, 2);
	            gameCells[i-1][j].val = 0;
	        } else{//If the move can go outside the game field, make it impossible to do.
	            moveValues[j] = -100; 
	    	}
	    }
	    //Find the move with highest value.
	    for(i=0; i<boardSize; i++){
	        if(moveValues[i] > maxValueAllMoves){
	            maxValueAllMoves = moveValues[i];
	            maxValueIndex = i;
	        }
	    }
	
	    //If the game field is completely empty, select middle of the game field to make a move.
	    int dotCount = 0;
	    for(i=0; i<boardSize; i++)
	        for(j=0; j<boardSize; j++)
	            if(gameCells[i][j].val == 0)
	                dotCount++;
	    if(dotCount == boardSize*boardSize-1 || dotCount == boardSize*boardSize)
	        maxValueIndex = (boardSize-1)/2;
	
	    //Do the move with highest value.
	    for(i=0; i<boardSize; i++)
	        if(gameCells[i][maxValueIndex].val != 0)
	            break;
	    if(i!=0) {
	        gameCells[i-1][maxValueIndex].val = 2;
	        gameCells[i-1][maxValueIndex].cellButton.setIcon(new ImageIcon(blueDot));
	        currentPlayer = 1;
	        if(!isGameEnded)
	        	checkWin();
	        currentPlayer = 0;
	    }
	}
	
	/**
	 * Controls every possible win or draw condition and if
	 * it finds one, ends the game with an information message
	 * and restarts the game afterwards.
	 */
	private void checkWin() {
		int i, j;
		boolean isOver = false;
	    //Check from up to down.
	    for(i=0; i<boardSize-3; i++){
	        for(j=0; j<boardSize; j++){
	            if(gameCells[i][j].val != 0)
	                if( gameCells[i+0][j+0].val == gameCells[i+1][j+0].val&&
	                	gameCells[i+1][j+0].val == gameCells[i+2][j+0].val&&
	                	gameCells[i+2][j+0].val == gameCells[i+3][j+0].val){
						ImageIcon winImage;
						if(currentPlayer == 0) 	winImage = new ImageIcon(redDotWin);
						else 					winImage = new ImageIcon(blueDotWin);
	                	gameCells[i+0][j+0].cellButton.setIcon(winImage);
	                	gameCells[i+1][j+0].cellButton.setIcon(winImage);
	                	gameCells[i+2][j+0].cellButton.setIcon(winImage);
	                	gameCells[i+3][j+0].cellButton.setIcon(winImage);
	                	isOver = true;
	                }
	        }
	    }
	    //Check from left to right.
	    for(i=0; i<boardSize; i++){
	        for(j=0; j<boardSize-3; j++){
	            if(gameCells[i][j].val != 0)
	                if( gameCells[i+0][j+0].val == gameCells[i+0][j+1].val&&
	                	gameCells[i+0][j+1].val == gameCells[i+0][j+2].val&&
	                	gameCells[i+0][j+2].val == gameCells[i+0][j+3].val){
	                	ImageIcon winImage;
						if(currentPlayer == 0) 	winImage = new ImageIcon(redDotWin);
						else 					winImage = new ImageIcon(blueDotWin);
	                	gameCells[i+0][j+0].cellButton.setIcon(winImage);
	                	gameCells[i+0][j+1].cellButton.setIcon(winImage);
	                	gameCells[i+0][j+2].cellButton.setIcon(winImage);
	                	gameCells[i+0][j+3].cellButton.setIcon(winImage);
	                	isOver = true;
	                }
	        }
	    }
	    
	    //Check diagonal from left to right.
	    for(i=0; i<boardSize-3; i++){
	        for(j=0; j<boardSize-3; j++){
	            if(gameCells[i][j].val != 0)
	                if( gameCells[i+0][j+0].val == gameCells[i+1][j+1].val&&
	                	gameCells[i+1][j+1].val == gameCells[i+2][j+2].val&&
	                	gameCells[i+2][j+2].val == gameCells[i+3][j+3].val){
	                	ImageIcon winImage;
						if(currentPlayer == 0) 	winImage = new ImageIcon(redDotWin);
						else 					winImage = new ImageIcon(blueDotWin);
	                	gameCells[i+0][j+0].cellButton.setIcon(winImage);
	                	gameCells[i+1][j+1].cellButton.setIcon(winImage);
	                	gameCells[i+2][j+2].cellButton.setIcon(winImage);
	                	gameCells[i+3][j+3].cellButton.setIcon(winImage);
	                	isOver = true;
	                }
	        }
	    }
	    
	    //Check diagonal from right to left.
	    for(i=0; i<boardSize-3; i++){
	        for(j=3; j<boardSize; j++){
	            if(gameCells[i][j].val != 0)
	                if( gameCells[i+0][j+0].val == gameCells[i+1][j-1].val&&
	                	gameCells[i+1][j-1].val == gameCells[i+2][j-2].val&&
	                	gameCells[i+2][j-2].val == gameCells[i+3][j-3].val){
	                	ImageIcon winImage;
						if(currentPlayer == 0) 	winImage = new ImageIcon(redDotWin);
						else 					winImage = new ImageIcon(blueDotWin);
	                	gameCells[i+0][j+0].cellButton.setIcon(winImage);
	                	gameCells[i+1][j-1].cellButton.setIcon(winImage);
	                	gameCells[i+2][j-2].cellButton.setIcon(winImage);
	                	gameCells[i+3][j-3].cellButton.setIcon(winImage);
	                	isOver = true;
	                }
	        }
	    }
	    
	    //Counts the empty cells and decides if the game can continue or it is a draw.
	    boolean isDraw = false;
	    int notEmptyCount = 0;
	    for(i=0; i<boardSize; i++) {
	    	for(j=0; j<boardSize; j++) {
	    		if(gameCells[i][j].val != 0)
	    			notEmptyCount++;
	    	}
	    }
	    if(notEmptyCount == boardSize * boardSize)
	    	isDraw = true;
	    
	    //If the game is over, give a message dialogue and return to the starting menu.
	    if(isOver) {
	    	isGameEnded = true;
	    	String endingStr;
	    	if(isVsComputer && currentPlayer == 1) {
	    		endingStr = "The Computer!";
	    	} else {
	    		endingStr = "Player " + Integer.toString(currentPlayer + 1);
	    	}
	    	JOptionPane.showMessageDialog(null, "Victory of " + endingStr, "Game Ended", JOptionPane.INFORMATION_MESSAGE);
	    	menuFrame.setVisible(false);
	    	playGame();
	    } else if(isDraw) {
	    	isGameEnded = true;
	    	JOptionPane.showMessageDialog(null, "Nobody win this one", "Game Ended", JOptionPane.INFORMATION_MESSAGE);
	    	menuFrame.setVisible(false);
	    	playGame();
	    }
	}
}
