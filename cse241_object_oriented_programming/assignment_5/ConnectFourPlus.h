#ifndef CONNECT_FOUR_PLUS
#define CONNECT_FOUR_PLUS
#include "ConnectFourAbstract.h"

namespace cfgame{
	// derived class from ConnectFourAbstract, win condition is only horizontal and vertical
	class ConnectFourPlus: public ConnectFourAbstract{
	protected:
		virtual int readMove();	// read a move (a letter, save, load)
		void isVictoryOrDraw(); // checks if the game has a winner, only horizontally and vertically
		int allMoveVals(int sX, int sY);	// helper method for ai, only checks horizontally and vertically
		virtual bool loadFile(const std::string &filename);	// delete the game that is currently being played and replace it with a file
		virtual void saveFile(const std::string &filename);	// save the current state of the game to a file
	};
}


#endif