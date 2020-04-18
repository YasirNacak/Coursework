#include <iostream>
#include <string>
#include "ConnectFourAbstract.h"
#include "ConnectFourPlus.h"
#include "ConnectFourDiag.h"
#include "ConnectFourPlusUndo.h"

int main(){
	// instances of the derived classes from ConnectFourAbstract
	cfgame::ConnectFourPlus CFPlus;
	cfgame::ConnectFourDiag CFDiag;
	cfgame::ConnectFourPlusUndo CFPlusUndo;

	// read input for a game mode (P, D, U)
	std::string gameMode;
	std::cout << "please enter game mode:";
	std::cin >> gameMode;

	// the loop below breaks only if a valid input is given
	while(true){
		if(!std::cin || gameMode.size() > 1 || (gameMode != "P" && gameMode != "D" && gameMode != "U")){
			std::cerr << "invalid input, try again:";
			std::cin.clear();
			std::cin.ignore(255, '\n');
			std::cin>>gameMode;
		} else {
			break;
		}
	}
	// call playGame of an object based on gameMode variable
	if(gameMode == "P"){
		CFPlus.playGame();
	} else if(gameMode == "D"){
		CFDiag.playGame();
	} else if(gameMode == "U"){
		CFPlusUndo.playGame();
	}
	
	return 0;
}