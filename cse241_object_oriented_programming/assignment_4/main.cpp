#include "connectFour.h"

// Resetting static variable.
int ConnectFour::livingCell = 0;

int main(){
	string gameMode;
	ConnectFour * allGames;
	cout << "single or multi: ";
	cin >> gameMode;

	// If single game mode, create an array of 1 object and call playGame on that.
	if(gameMode == "S"){
		allGames = new ConnectFour[1];
		allGames[0].playGame();
	} else if(gameMode == "M"){ // If multi game mode, create an array of 5 objects and call playGame on each of them.
		int liveGames = 5;
		allGames = new ConnectFour[5];
		for(int i=0; i<5; i++)
			allGames[i].setMulti(true);
		for(int i=0; i<5; i++){
			allGames[i].readBoardFile();
			allGames[i].readType();
		}
		while(liveGames != 0){
			liveGames = 0;
			for(int i=0; i<5; i++)
				if(allGames[i].isGameEnded() == false){
					allGames[i].calculateLivingCell();
					liveGames++;
				}
			int selectedGame;
			cout << "select a game: ";
			cin >> selectedGame;
			if(cin && selectedGame > 0 && selectedGame < 6)
				allGames[selectedGame-1].playGame();
			else if(cin.eof()){
				return 0;
			} else{
				cin.clear();
				cin.ignore(255, '\n');
			}
			ConnectFour::printLivingCell();
		}
	} else {
		cerr << "invalid game mode." << endl;
	}
	
	if(gameMode == "S" || gameMode == "M") // Destroy array of ConnectFour objects.
		delete [] allGames;
	return 0;
}