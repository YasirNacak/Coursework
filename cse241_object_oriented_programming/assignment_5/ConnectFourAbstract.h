#ifndef CONNECT_FOUR_ABSTRACT
#define CONNECT_FOUR_ABSTRACT
#include "Cell.h"
#include <iostream>
#include <string>
#include <fstream>

namespace cfgame{
	// base abstract class
	class ConnectFourAbstract{
	public:
		ConnectFourAbstract(); // no parameter constructor
		ConnectFourAbstract(int rows, int columns);	// constructor with row and column values
		ConnectFourAbstract(const ConnectFourAbstract &other);	// copy constructor
		ConnectFourAbstract& operator=(const ConnectFourAbstract &other);	// assignment overload
		~ConnectFourAbstract();	// destructor

		void playGame();	// main function, calls the others
		bool isGameEnded() const;	// returns opposite of _isLive variable
	protected:
		int 	_rows, 	// number of rows
				_columns,	// number of columns
				_currentPlayer,	// 0 for first player, 1 for second player
				_state;	// game state, main argument for playGame function
		bool 	_isLive,	// if the game has ended
				_isVsComputer;	// is the game being played against computer
		Cell ** _gameCells;	// all cells in the game

	protected:	
		void createCells();	// create dynamicly allocated cell variables
		void readSize();	// read input for row and column values
		void readType();	// read PvP or PvC
		virtual int readMove();	// read a move (a letter, save, load)
		void play(int cellPosition);	// play a move for player based on the return value of readMove
		void play();	// play a move for computer
		virtual int allMoveVals(int sX, int sY) = 0;	// helper method for computer
		void calcMoveVal(int pX, int pY, int xInc, int yInc, int &maxValCurrMove);	// helper method for computer
		void printGame() const;	// uses standart output to print out values based on gameCells
		virtual void isVictoryOrDraw() = 0;	// check if the game has a winner or is ended in draw
		virtual bool loadFile(const std::string &filename);	// delete the game that is currently being played and replace it with a file
		virtual void saveFile(const std::string &filename);	// save the current state of the game to a file
	};
}

#endif	