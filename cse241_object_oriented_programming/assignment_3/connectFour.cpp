/*
    AUTHOR: Ahmet Yasir NACAK
    STUDENT ID: 161044042
    CREATION DATE: 13.10.2017
*/

#include "connectFour.h"

/*
	No parameter contructor.
*/
ConnectFour::ConnectFour(){
	playerVsAI = false,
	isGameLive = true,
	gameState = 1,
	currentPlayer = 0,
	height = 30,
	width = 30;
	createGameCells();
}

/*
	Constructor with height and width.
*/
ConnectFour::ConnectFour(int height, int width):
	height(height), width(width){
	playerVsAI = false,
	isGameLive = true,
	gameState = 1,
	currentPlayer = 0;
	createGameCells();
}

/*
	Constructor with filename.
*/
ConnectFour::ConnectFour(const string &fileName){
	isGameLive = true;
	loadGame(fileName);
}

/*
	playGame is the heart of the game. This function acts accordingly to
	the gameState variable of the object.
*/
void ConnectFour::playGame(){
	if(gameState == 1){			//GAME STATE: First menu that reads board size.
		cout << "============" << endl;
		readWidth();
		readHeight();
		gameState = 2;
	} else if(gameState == 2){	//GAME STATE: Menu that reads game type.
		readGameType();
		createGameCells();
		gameState = 3;

	} else if(gameState == 3){	//GAME STATE: In game.
		drawGameField();
		int inputToInt = readMove();
		play(inputToInt);
		drawGameField();
		if(checkVictory()){
			gameState = 4;
			if(!playerVsAI)
				currentPlayer == 0 ? currentPlayer = 1 : currentPlayer = 0;
		} else if(checkDraw()){
			gameState = 4;
			currentPlayer = -1;
		}
		if(playerVsAI && gameState == 3){
			play();
			if(checkVictory()){
				gameState = 4;
				currentPlayer = 2;
			} else if(checkDraw()){
				gameState = 4;
				currentPlayer = -1;
			}
		}
	} else if(gameState == 4){	//GAME STATE: End game.
		cout << "game ended!" << endl
			<< "final board:" << endl;
		drawGameField();
		if(currentPlayer == 2)
			cout << "the winner is computer." << endl;
		else if(currentPlayer == -1)
			cout << "there is no winner." << endl;
		else
			cout << "the winner is player " << currentPlayer+1 << endl; 
		isGameLive = false;
	} else {
		cerr << "This ConnectFour object has reached an unknown state." << endl;
	}
}

/*
	This function checks the isGameLive variable of the object, this variable
	changes when the checkWin or checkDraw return true.
*/
bool ConnectFour::isGameEnded(){
	if(isGameLive)
		return false;
	else return true;
}

/*
	Takes another connectFour instance and compares it for player1(user).
*/
bool ConnectFour::compareGames(ConnectFour other){
	int myValue = 0, otherValue = 0,
		myMoveValues[27], otherMoveValues[27], i, j;
    for(j=0; j<width; j++){
        //Iterate through all moves.
        for(i=0; i<height; i++)
            if(gameCells[i][j].getValue() != '.')
                break;
        if(i!=0){
            gameCells[i-1][j].setValue('X');
            //Calculate the value of each move.
            myMoveValues[j] = allMoveVals(i, j, 'X');
            gameCells[i-1][j].setValue('.');
        } else{//If the move can go outside the game field, make it impossible to do.
            myMoveValues[j] = -100; 
   		}
    }
    for(i=0; i<width; i++)
    	if(myMoveValues[i] > myValue)
    		myValue = myMoveValues[i];
    for(j=0; j<other.getWidth(); j++){
        //Iterate through all moves.
        for(i=0; i<other.getHeight(); i++)
            if(other.gameCells[i][j].getValue() != '.')
                break;
        if(i!=0){
            other.gameCells[i-1][j].setValue('X');
            //Calculate the value of each move.
            otherMoveValues[j] = other.allMoveVals(i, j, 'X');
            other.gameCells[i-1][j].setValue('.');
        } else{
        	//If the move can go outside the game field, make it impossible to do.
            otherMoveValues[j] = -100; 
   		}
    }

    for(i=0; i<other.getWidth(); i++)
    	if(otherMoveValues[i] > otherValue)
    		otherValue = otherMoveValues[i];
    bool result;
    if(myValue > otherValue)
    	result = true;
    else if(otherValue > myValue)
    	result = false;
    else
    	result = false;
    return result;
}

/*
	Counts living cells for current game.
*/
int ConnectFour::calculateLivingCell(){
	for(int i=0; i<height; i++)
		for(int j=0; j<width; j++)
			if(gameCells[i][j].getValue() != '.')
				livingCell++;
}

/*
	Prints out the number of living cells.
*/
void ConnectFour::printLivingCell(){
	if(livingCell > 0)
		cout << "Living Cells: " << livingCell << endl;
}

/*
	Resets number of the living cells to 0.
*/

void ConnectFour::resetLivingCell(){
	livingCell = 0;
}

/*
	Creates the gameCells vector.
*/
void ConnectFour::createGameCells(){
	Cell tempCell;
	vector<Cell> tempVec(height);
	gameCells.clear();
	for(int i=0; i<height; i++){
		gameCells.push_back(tempVec);
		for(int j=0; j<width; j++){
			tempCell.setPosition('A' + j);
			tempCell.setRow(i + 1);
			gameCells[i].push_back(tempCell);
		}
	}
}

/*
	Input reader for the board width.
*/
void ConnectFour::readWidth(){
	int boardWidth;
	cout << "please enter a board width: ";
	cin >> boardWidth;

	//Loop below breaks only if a valid input is given.
	while(true){
		if(boardWidth > 3 && boardWidth < 27)
			break;
		else if(!cin || boardWidth <= 3 || boardWidth >= 27){
			cerr << "invalid input, try again." << endl;
			cin.clear();
			cin.ignore(256, '\n');
			cin >> boardWidth;
		}
	}
	width = boardWidth;
}


/*
	Input reader for the board height.
*/
void ConnectFour::readHeight(){
	int boardHeight;
	cout << "please enter a board height: ";
	cin >> boardHeight;

	//Loop below breaks only if a valid input is given.
	while(true){
		if(boardHeight > 3 && boardHeight < 27)
			break;
		else if(!cin || boardHeight <= 3 || boardHeight >= 27){
			cerr << "invalid input, try again." << endl;
			cin.clear();
			cin.ignore(256, '\n');
			cin >> boardHeight;
		}
	}
	height = boardHeight;
}

/*
	Input reader for the game type.
*/
void ConnectFour::readGameType(){
	string gameType;
	cout << "please enter a game type: ";
	cin >> gameType;

	//Loop below breaks only if a valid input is given.
	while(true){
		if(gameType[0] == 'P' && gameType.size() == 1)
			break;
		else if(gameType[0] == 'C' && gameType.size() == 1){
			playerVsAI = true;
			break;
		} else if(!cin || gameType[0] != 'P' || gameType[0] != 'C' || gameType.size() > 1){
			cerr << "invalid input, try again." << endl;
			cin.clear();
			cin.ignore(256, '\n');
			cin >> gameType;
		}
	}
}

/*
	Input reader for an action, an action can be either 
	a letter or one of save and load commands.
*/
int ConnectFour::readMove(){
    string input, filename;
    int inputToInt = -1;

    //Loop below breaks only if a valid input is given.
    while(true){
    	cin >> input;
		if(input[0] >= 'A' && input[0] <= 'Z')
        	inputToInt = static_cast<int>(input[0] - 'A');
        if(input == "SAVE"){
    		cin >> filename;
    		saveGame(filename);
    	} else if(input == "LOAD"){
    		cin >> filename;
    		if(loadGame(filename)){
    			cout << "game loaded" << endl;
    			drawGameField();
    		}
    	} else if(!cin || inputToInt < 0 || inputToInt >= width || input.size() > 1){
            cerr << "invalid input, try again" << endl;
            cin.clear();
            cin.ignore(256, '\n');
        } else
        	break;
    }
    return inputToInt;
}

/*
	Calls calcMoveVal for all possible situations.
*/
int  ConnectFour::allMoveVals(int startX, int startY, char playerMove){
	int maxValueCurrentMove = 0;
	//Horizontal effect of the move.
    calcMoveVal(startX-1, startY-3, 0, 1, maxValueCurrentMove, playerMove);
    calcMoveVal(startX-1, startY-2, 0, 1, maxValueCurrentMove, playerMove);
    calcMoveVal(startX-1, startY-1, 0, 1, maxValueCurrentMove, playerMove);
	calcMoveVal(startX-1, startY+0, 0, 1, maxValueCurrentMove, playerMove);

    //Vertical effectiveness of the move.
    calcMoveVal(startX-4, startY+0, 1, 0, maxValueCurrentMove, playerMove);
    calcMoveVal(startX-3, startY+0, 1, 0, maxValueCurrentMove, playerMove);
    calcMoveVal(startX-2, startY+0, 1, 0, maxValueCurrentMove, playerMove);
    calcMoveVal(startX-1, startY+0, 1, 0, maxValueCurrentMove, playerMove);

    //Diagonal from left to right effectiveness of the moveç
    calcMoveVal(startX-4, startY-3, 1, 1, maxValueCurrentMove, playerMove);
    calcMoveVal(startX-3, startY-2, 1, 1, maxValueCurrentMove, playerMove);
    calcMoveVal(startX-2, startY-1, 1, 1, maxValueCurrentMove, playerMove);
    calcMoveVal(startX-1, startY+0, 1, 1, maxValueCurrentMove, playerMove);

    //Diagonal from right to left effectiveness of the move.
    calcMoveVal(startX-4, startY+3, 1, -1, maxValueCurrentMove, playerMove);
    calcMoveVal(startX-3, startY+2, 1, -1, maxValueCurrentMove, playerMove);
    calcMoveVal(startX-2, startY+1, 1, -1, maxValueCurrentMove, playerMove);
    calcMoveVal(startX-1, startY+0, 1, -1, maxValueCurrentMove, playerMove);	
    return maxValueCurrentMove;
}

/*
	No parameter play function, does a move for the computer.
*/
void ConnectFour::play(){
    int i, j, moveValues[27],
        maxValueIndex = 0, maxValueAllMoves = 0;
    for(j=0; j<width; j++){
        //Iterate through all moves.
        for(i=0; i<height; i++)
            if(gameCells[i][j].getValue() != '.')
                break;
        if(i!=0){
            gameCells[i-1][j].setValue('O');
            //Calculate the value of each move.
            moveValues[j] = allMoveVals(i, j, 'O');
            gameCells[i-1][j].setValue('.');
        } else{//If the move can go outside the game field, make it impossible to do.
            moveValues[j] = -100; 
    	}
    }
    //Find the move with highest value.
    for(i=0; i<width; i++){
        if(moveValues[i] > maxValueAllMoves){
            maxValueAllMoves = moveValues[i];
            maxValueIndex = i;
        }
    }

    //If the game field is completely empty, select middle of the game field to make a move.
    decltype(i) dotCount = 0;
    for(i=0; i<height; i++)
        for(j=0; j<width; j++)
            if(gameCells[i][j].getValue() == '.')
                dotCount++;
    if(dotCount == height*width-1)
        maxValueIndex = (width-1)/2;

    //Do the move with highest value.
    for(i=0; i<height; i++)
        if(gameCells[i][maxValueIndex].getValue() != '.')
            break;
    if(i!=0)
        gameCells[i-1][maxValueIndex].setValue('O');
}

/*
	Single parameter play function, does a move for the play
	using the return value provided by readMove function.
	@param inputToInt: The converted value of the input taken from the user.
	this value is the column of the given move.
*/
void ConnectFour::play(int inputToInt){
	int i;
	for(i=0; i<height; i++)
	    if(gameCells[i][inputToInt].getValue() != '.')
	            break;
    if(i!=0){
        if(currentPlayer == 1)
            gameCells[i-1][inputToInt].setValue('O');
        else
            gameCells[i-1][inputToInt].setValue('X');
    }
    if(!playerVsAI)
        currentPlayer == 1 ? currentPlayer = 0 : currentPlayer = 1;
}

/*	
	Calculates the value of given move based on a given 
	starting point and increment amounts for x and y. 
	@param pointX: X point of the starting coordinate.
	@param pointY: Y point of the starting coordinate.
	@param xIncrease: The amount of increase/decrease of pointX after each step of calculation.
	@param yIncrease: The amount of increase/decrease of pointY after each step of calculation.
	@param maxValueCurrentMove: The maximum value of the move that is being calculated.
*/
void ConnectFour::calcMoveVal(int pointX, int pointY, int xIncrease, int yIncrease, int &maxValueCurrentMove, char playerMove){
	int i, result = 0;
	char enemyMove;
	playerMove == 'X' ? enemyMove = 'O' : enemyMove = 'X';
    for(i=0; i<4; i++){
        if(pointX >= 0 && pointX < height && pointY >= 0 && pointY < width){
            if(gameCells[pointX][pointY].getValue() == playerMove)
                result++;
            else if(gameCells[pointX][pointY].getValue() == enemyMove)
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
}

/*
	Prints out the game field.
*/
void ConnectFour::drawGameField(){
    int i, j;
    for(i=0; i<width; i++)
        cout << "==";
    cout << endl;
    for(i=0; i<width; i++)
        cout << (char)(i + 'A') << " ";
    cout << endl;
    for(i=0; i<height; i++){
        for(j=0; j<width; j++){
            cout << gameCells[i][j].getValue() << " ";
        }
        cout << endl;
    }
}

/*
	Takes some of the variables of the object and
	outputs it to a file.
	@param fileName: Name of the file that is written.
*/
void ConnectFour::saveGame(const string &fileName){
	ofstream gameFile(fileName);
	gameFile << gameState << " " << playerVsAI << " " << currentPlayer << " " << width << " " << height << endl;
	for(int i=0; i<height; i++){
		for(int j=0; j<width; j++)
			gameFile << gameCells[i][j].getValue() << " ";
		gameFile << endl;
	}
	gameFile.close();
}

/*
	Reads out a file and changes variables of the object
	based on the given input.
	@param fileName: Name of the file that is read.
*/
bool ConnectFour::loadGame(const string &fileName){
	bool result;
	ifstream gameFile(fileName);
	
	//Check validity of the file.
	if(gameFile){
		result = true;
		gameFile >> gameState >> playerVsAI >> currentPlayer >> width >> height;
		createGameCells();
		for(int i=0; i<height; i++)
			for(int j=0; j<width; j++){
				char tempChar;
				gameFile >> tempChar;
				gameCells[i][j].setValue(tempChar);
			}
	} else {
		result = false;
		cerr << "can not find a file named: " << fileName << endl;
	}
	gameFile.close();
	return result;
}

/*
	Scans the game field and checks all win conditions for
	each possible cell.
*/
bool ConnectFour::checkVictory(){
    auto isOver = false;
    int i, j;
    //Check from up to down.
    for(i=0; i<height-3; i++){
        for(j=0; j<width; j++){
            if(gameCells[i][j].getValue() != '.')
                if( gameCells[i+0][j+0].getValue() == gameCells[i+1][j+0].getValue()&&
                	gameCells[i+1][j+0].getValue() == gameCells[i+2][j+0].getValue()&&
                	gameCells[i+2][j+0].getValue() == gameCells[i+3][j+0].getValue()){
					gameCells[i+0][j+0].setValue(gameCells[i+0][j+0].getValue() + 'a' - 'A'),
                	gameCells[i+1][j+0].setValue(gameCells[i+1][j+0].getValue() + 'a' - 'A'),
                	gameCells[i+2][j+0].setValue(gameCells[i+2][j+0].getValue() + 'a' - 'A'),
                	gameCells[i+3][j+0].setValue(gameCells[i+3][j+0].getValue() + 'a' - 'A');
                    isOver = true;
                }
        }
    }
    //Check from left to right.
    for(i=0; i<height; i++){
        for(j=0; j<width-3; j++){
            if(gameCells[i][j].getValue() != '.')
                if( gameCells[i+0][j+0].getValue() == gameCells[i+0][j+1].getValue()&&
                	gameCells[i+0][j+1].getValue() == gameCells[i+0][j+2].getValue()&&
                	gameCells[i+0][j+2].getValue() == gameCells[i+0][j+3].getValue()){
                	gameCells[i+0][j+0].setValue(gameCells[i+0][j+0].getValue() + 'a' - 'A'),
                	gameCells[i+0][j+1].setValue(gameCells[i+0][j+1].getValue() + 'a' - 'A'),
                	gameCells[i+0][j+2].setValue(gameCells[i+0][j+2].getValue() + 'a' - 'A'),
                	gameCells[i+0][j+3].setValue(gameCells[i+0][j+3].getValue() + 'a' - 'A');
                    isOver = true;
                }
        }
    }

    //Check diagonal from left to right.
    for(i=0; i<height-3; i++){
        for(j=0; j<width; j++){
            if(gameCells[i][j].getValue() != '.')
                if( gameCells[i+0][j+0].getValue() == gameCells[i+1][j+1].getValue()&&
                	gameCells[i+1][j+1].getValue() == gameCells[i+2][j+2].getValue()&&
                	gameCells[i+2][j+2].getValue() == gameCells[i+3][j+3].getValue()){
                	gameCells[i+0][j+0].setValue(gameCells[i+0][j+0].getValue() + 'a' - 'A'),
                	gameCells[i+1][j+1].setValue(gameCells[i+1][j+1].getValue() + 'a' - 'A'),
                	gameCells[i+2][j+2].setValue(gameCells[i+2][j+2].getValue() + 'a' - 'A'),
                	gameCells[i+3][j+3].setValue(gameCells[i+3][j+3].getValue() + 'a' - 'A');
                    isOver = true;
                }
        }
    }

    //Check diagonal from right to left.
    for(i=0; i<height-3; i++){
        for(j=3; j<width; j++){
            if(gameCells[i][j].getValue() != '.')
                if( gameCells[i+0][j+0].getValue() == gameCells[i+1][j-1].getValue()&&
                	gameCells[i+1][j-1].getValue() == gameCells[i+2][j-2].getValue()&&
                	gameCells[i+2][j-2].getValue() == gameCells[i+3][j-3].getValue()){
                    gameCells[i+0][j+0].setValue(gameCells[i+0][j+0].getValue() + 'a' - 'A'),
                	gameCells[i+1][j-1].setValue(gameCells[i+1][j-1].getValue() + 'a' - 'A'),
                	gameCells[i+2][j-2].setValue(gameCells[i+2][j-2].getValue() + 'a' - 'A'),
                	gameCells[i+3][j-3].setValue(gameCells[i+3][j-3].getValue() + 'a' - 'A');
                    isOver = true;
                }
        }
    }
    return isOver;
}

/*
	Scans the game field and checks for a full game field
	without any winners.
*/
bool ConnectFour::checkDraw(){
    bool isDraw = false;
    int emptyCells = 0;
    for(int i=0; i<height; i++)
        for(int j=0; j<width; j++)
            if(gameCells[i][j].getValue() == '.')
                emptyCells++;
    if(emptyCells == 0)
        isDraw = true;
    return isDraw;
}

/*Inner class cell's functions*/
ConnectFour::Cell::Cell(){
	cellPosition = 'A',
	cellRow = 0,
	cellValue = '.';
}

ConnectFour::Cell::Cell(char cellPosition, int cellRow, char cellValue):
	cellPosition(cellPosition), cellRow(cellRow), cellValue(cellValue){
	/*Body left intentionally empty.*/
}