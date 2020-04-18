#ifndef CONNECT_FOUR_DIAG
#define CONNECT_FOUR_DIAG
#include "ConnectFourAbstract.h"

namespace cfgame{
	// derived class from ConnectFourAbstract, win condition is only diagonal
	class ConnectFourDiag: public ConnectFourAbstract{
	protected:
		void isVictoryOrDraw();	// checks if the game has a winner, only diagonally
		int allMoveVals(int sX, int sY);	// helper method for ai, only checks diagonally
	};
}

#endif