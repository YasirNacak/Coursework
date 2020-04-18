#ifndef CONNECT_FOUR_PLUSUNDO
#define CONNECT_FOUR_PLUSUNDO
#include "ConnectFourPlus.h"
#include <vector>

namespace cfgame{
	// derived class from ConnectFourPlus, win condition is only horizontal and vertical and has capability to undo moves
	class ConnectFourPlusUndo: public ConnectFourPlus{
	protected:
		virtual int readMove();	// read a move (a letter, save, load and undo)
		virtual bool loadFile(const std::string &filename);	// delete the game that is currently being played and replace it with a file
		virtual void saveFile(const std::string &filename);	// save the current state of the game to a file
	private:
		void undoMove();	// undoes a move based on _moves variable
		std::vector <int> _moves;	// list of every move made
	};
}

#endif