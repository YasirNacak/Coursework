#ifndef CONNECTFOUR_H_
#define CONNECTFOUR_H_
#include <iostream>
#include <string>
#include <fstream>

using namespace std;

class ConnectFour{
public: 												// public methods of ConnectFour
	ConnectFour(); 										// no parameter constructor
	ConnectFour(int rows, int columns); 				// constructor with row number and column number
	ConnectFour(const string &filename); 				// constructor with file name
	ConnectFour(const ConnectFour &other); 				// copy constructor
	ConnectFour& operator=(const ConnectFour &other); 	// assignment
	~ConnectFour(); 									// destructor
	void playGame(); 									// uses methods of the class to play a game from start to end
	bool isGameEnded() const; 							// returns true if the game is ended
	bool operator==(const ConnectFour &other) const; 	// compare
	bool operator!=(const ConnectFour &other) const; 	// opposite of compare
	void setMulti(bool isInMulti);						// setter for multi mode
	void readBoardFile(); 								// read input for board configuration
	void readType(); 									// read if the game is gonna be played against computer or another player

	void calculateLivingCell(); 						// calculates living cell amount for the object
	static void printLivingCell(); 						// prints out the living cell amount and resets it

private: // private class of ConnectFour
	class Cell{
	public: 														// public methods of Cell
		Cell(); 													// no parameter constructor
		Cell(const Cell &other); 									// copy constructor
		Cell& operator=(const Cell &other); 						// assignment
		~Cell(); 													// destructor
		bool operator==(const Cell &other) const; 					// compare
		bool operator!=(const Cell &other) const; 					// opposite of compare
		Cell& operator++(); 										// prefix increment
		Cell operator++(int); 										// postfix increment
		Cell& operator--(); 										// prefix decrement
		Cell operator--(int); 										// postfix decrement
		friend istream& operator>>(istream &in, Cell &myCell); 		// stream extraction
		friend ostream& operator<<(ostream &out, const Cell &myCell); // stream inserion
		inline char getPosition() const {return _position;} 		// getter for _position variable
		inline int getRowNumber() const {return _rowNumber;} 		// getter for _rowNumber variable
		inline int getState() const {return _state;} 				// getter for _state variable
		inline void setPosition(char position) {_position = position;} // setter for _position variable
		inline void setRowNumber(int rowNumber) {_rowNumber = rowNumber;} // setter for _rowNumber variable
		inline void setState(int state) {_state = state;} 			// setter for _state variable

	private: 				// private variables of Cell
		char _position; 	// position of the cell (A,B,C,...)
		int  _rowNumber; 	// row number of the cell (1, 2, 3,...)
		int  _state; 		// state of the cell
	};

public: // redecleration of friend methods of inner class
	friend istream& operator>>(istream &in, Cell &myCell); // stream extraction
	friend ostream& operator<<(ostream &out, const Cell &myCell); // stream inserion

private: 					// private variables of ConnectFour
	Cell ** _gameCells; 	// array of cells for game field
	int 	_rows, 			// number of rows
			_columns, 		// number of columns
			_state, 		// state of the game
			_currentPlayer; // who is playing
	bool 	_isVsComputer, 	// is the game being played against computer
		 	_isLive, 		// is game still being played
		 	_isInMulti;		// is a part of multi game system
	
	static int livingCell; // numbers of cells that is not empty or unusable

private: 											// private methods of ConnectFour
	void createCells(); 							// helper function for constructors
	int  readMove(); 								// read a move from one of the players
	void play(); 									// plays the game for computer, which requires no input
	void play(int cellPosition); 					// plays the game for either of the players, requires an input
	int  allMoveVals(int sX, int sY, int mvState); 	// helper function of AI
	void calcMoveVal(int pX, int pY, int xInc, int yInc, int &maxValCurrMove, int mvState); // helper function of AI
	void printGame() const; 						// draws out the game cells
	void isVictoryOrDraw(); 						// changes game state if the game has a victor or it is a draw
	bool loadFile(const string &filename); 			// loads data from a file, overwrites current game
	void saveFile(const string &filename) const; 	// saves current data to a file
	
};
#endif