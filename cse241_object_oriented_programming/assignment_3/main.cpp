/*
    AUTHOR: Ahmet Yasir NACAK
    STUDENT ID: 161044042
    CREATION DATE: 13.10.2017
*/

#include "connectFour.h"

int ConnectFour::livingCell = 0;

int main(){
	int endedGames = 0, previouslySelected = -1;
	bool isRunning = true;
	vector<ConnectFour> allGames(5);
	string multiOrSingle;

	//Multi or Single selection.
	cout << "multi or single?: ";
	cin >> multiOrSingle;

	if(multiOrSingle == "M"){						//Main parameters for multi game mode.
		for(int i=0; i<5; i++){
			allGames[i].playGame();
			allGames[i].playGame();
		}
	}

	//Main game loop
	while(isRunning){
		if(multiOrSingle == "M"){					//Multi game.
			string objNum;
			cout << "select object: ";
			cin >> objNum;
			int objNumToInt = (int)(objNum[0]-'0') - 1;
			if(objNum.length() == 1 && objNumToInt <= 4 && objNumToInt >= 0 && !allGames[objNumToInt].isGameEnded()){
				if(previouslySelected != -1)
					if(allGames[objNumToInt].ConnectFour::compareGames(allGames[previouslySelected]))
						cout << "current game is better than the previously selected" << endl;
					else 
						cout << "current game is worse or it's equal to previously selected" << endl;
				previouslySelected = objNumToInt;
				allGames[objNumToInt].playGame();
			}
			endedGames = 0;
			for(int i=0; i<5; i++){
				if(allGames[i].isGameEnded())
					endedGames++;
				if(endedGames == 5)
					isRunning = false;
			}
			ConnectFour::resetLivingCell();
			for(int i=0; i<5; i++)
				allGames[i].calculateLivingCell();
			ConnectFour::printLivingCell();
		} else if(multiOrSingle == "S"){			//Single game.
			allGames[0].playGame();
			ConnectFour::resetLivingCell();
			allGames[0].calculateLivingCell();
			ConnectFour::printLivingCell();
			if(allGames[0].isGameEnded())
				isRunning = false;
		} else {
			isRunning = false;
		}
	}

	return 0;
}