/*
    AUTHOR: Ahmet Yasir NACAK
    STUDENT ID: 161044042
    CREATION DATE: 18.09.2017
*/
#include <iostream>
#include <string>

using namespace std;

void resetGameField(char gameField[20][20]);

void drawGameField(int boardSize, char gameField[20][20]);

void takeUserInput(int boardSize, char gameField[20][20], int *currentPlayer, bool playerVsAI);

bool checkWin(int boardSize, char gameField[20][20]);

bool checkDraw(int boardSize, char gameField[20][20]);

void vsComputerCalcMoveVal(int pointX, int pointY, int xIncrease, int yIncrease, char gameField[20][20], int boardSize, int *maxValueCurrentMove);

void vsComputerPlayMove(int boardSize, char gameField[20][20]);

int main(){
    bool isRunning = true, playerVsAI = false;
    int gameState = 0, boardSize = 0,
        currentPlayer = 1, winningPlayer = 0;
    char gameType, gameField[20][20];
    string gameTypeStr;

    //Main game loop
    while(isRunning){

        if(gameState == 0){             //Game State 0 = Intro and board size input
            resetGameField(gameField);
            cout << "Welcome to Connect4!, Please type a board size. (an integer between 4 and 20)" << endl;
            cin >> boardSize;

            //Loop below breaks only if a valid input is given
            while(gameState == 0){
                if(boardSize >= 4 && boardSize <= 20 && boardSize%2 == 0)
                    gameState = 1;
                else if(!cin || boardSize > 20 || boardSize < 4 || boardSize%2 == 1){
                    cout << "Invalid input, please try again." << endl;
                    cin.clear();
                    cin.ignore(256, '\n');
                    cin >> boardSize;
                }
            }

        } else if(gameState == 1){      //Game State 1 = Game type selection
            cout << "===========================" << endl;
            cout << "Please Select a game type" << endl
                << "P.Player vs Player" << endl
                << "C.Player vs Computer" << endl;
            cin >> gameTypeStr;
            gameType = gameTypeStr[0];

            //Loop below breaks only if a valid input is given
            while(gameState == 1){
                if(gameType == 'P' && gameTypeStr.size() == 1){
                    gameState = 2;
                } else if(gameType == 'C' && gameTypeStr.size() == 1){
                    gameState = 3;
                } else if(!cin || gameType != 'P' || gameType != 'C' || gameTypeStr.size() > 1){
                    cout << "Invalid input, please try again." << endl;
                    cin.clear();
                    cin.ignore(256, '\n');
                    cin >> gameTypeStr;
                    gameType = gameTypeStr[0];
                }
            }

        } else if(gameState == 2){      //Game State 2 = In game (Player vs Player)
            drawGameField(boardSize, gameField);
            takeUserInput(boardSize, gameField, &currentPlayer, playerVsAI);
            if(checkWin(boardSize, gameField)){
                winningPlayer = currentPlayer + 1;
                gameState = 4;
            } else if(checkDraw(boardSize, gameField)){
                winningPlayer = 0;
                gameState = 4;
            }

        } else if(gameState == 3){      //Game State 3 = In game (Player vs Computer)
            currentPlayer = 1;
            playerVsAI = true;
            drawGameField(boardSize, gameField);
            takeUserInput(boardSize, gameField, &currentPlayer, playerVsAI);
            if(checkWin(boardSize, gameField)){
                winningPlayer = 1;
                gameState = 4;
            } else {
                vsComputerPlayMove(boardSize, gameField);
                if(checkWin(boardSize, gameField)){
                    winningPlayer = 2;
                    gameState = 4;
                } else if(checkDraw(boardSize, gameField)){
                    winningPlayer = 0;
                    gameState = 4;
                }
            }

        } else if(gameState == 4){      //Game State 4 = End of the game
            cout << "===========================" << endl;
            cout << "Final Game Field:" << endl;
            drawGameField(boardSize, gameField);
            cout << "Game Over!" << endl;
            if(winningPlayer != 0)
                cout << "The Winner of this game: Player " << winningPlayer << endl;
            else
                cout << "It's a draw! There is no winner" << endl;
            gameState = 5;
        } else if(gameState == 5){      //Game State 5 = Quit
            isRunning = false;
        }
    }
    cout << "Thanks for playing!";
    return 0;
}

void resetGameField(char gameField[20][20]){
    //Initalizatite game field
    int i, j;
    for(i=0; i<20; i++){
        for(j=0; j<20; j++){
            gameField[i][j] = '.';
        }
    }
}

void drawGameField(int boardSize, char gameField[20][20]){
    //Draw game field
    int i, j;
    for(i=0; i<boardSize; i++)
        cout << "==";
    cout << endl;
    for(i=0; i<boardSize; i++)
        cout << (char)(i + 'A') << " ";
    cout << endl;
    for(i=0; i<boardSize; i++){
        for(j=0; j<boardSize; j++){
            cout << gameField[i][j] << " ";
        }
        cout << endl;
    }
}

void takeUserInput(int boardSize, char gameField[20][20], int *currentPlayer, bool playerVsAI){
    //Take a letter as an input and do a move accordingly
    char input;
    string inputStr;
    int i, inputToInt = -1;

    cin >> inputStr;
    input = inputStr[0];
    if(input >= 'A' && input <= 'Z')
        inputToInt = static_cast<int>(input - 'A');
    else if(input >= 'a' && input <= 'z')
        inputToInt = static_cast<int>(input - 'a');

    //Loop below breaks only if a valid input is given
    while(true){
        if(inputToInt >= 0 && inputToInt < boardSize && inputStr.size() == 1)
            break;
        else if(!cin || inputToInt < 0 || inputToInt >= boardSize || inputStr.size() > 1){
            cout << "Invalid input, please try again." << endl;
            cin.clear();
            cin.ignore(256, '\n');
            cin >> inputStr;
            input = inputStr[0];
            if(input >= 'A' && input <= 'Z')
                inputToInt = static_cast<int>(input - 'A');
            else if(input >= 'a' && input <= 'z')
                inputToInt = static_cast<int>(input - 'a');
        }
    }

    for(i=0; i<boardSize; i++)
        if(gameField[i][inputToInt] != '.')
            break;
    if(i!=0){
        if(*currentPlayer == 1)
            gameField[i-1][inputToInt] = 'X';
        else
            gameField[i-1][inputToInt] = 'O';
    }
    if(!playerVsAI)
        *currentPlayer == 1 ? *currentPlayer = 0 : *currentPlayer = 1;
}

bool checkWin(int boardSize, char gameField[20][20]){
    //Check win conditions
    bool isOver = false;
    int i, j;
    //Check up to down
    for(i=0; i<boardSize-3; i++){
        for(j=0; j<boardSize; j++){
            if(gameField[i][j] != '.')
                if(gameField[i][j] == gameField[i+1][j] && gameField[i+1][j] == gameField[i+2][j] && gameField[i+2][j] == gameField[i+3][j]){
                    gameField[i][j] += 'a' - 'A', gameField[i+1][j] += 'a' - 'A', gameField[i+2][j] += 'a' - 'A', gameField[i+3][j] += 'a' - 'A';
                    isOver = true;
                }
        }
    }
    //Check left to right
    for(i=0; i<boardSize; i++){
        for(j=0; j<boardSize-3; j++){
            if(gameField[i][j] != '.')
                if(gameField[i][j] == gameField[i][j+1] && gameField[i][j+1] == gameField[i][j+2] && gameField[i][j+2] == gameField[i][j+3]){
                    gameField[i][j] += 'a' - 'A', gameField[i][j+1] += 'a' - 'A', gameField[i][j+2] += 'a' - 'A', gameField[i][j+3] += 'a' - 'A';
                    isOver = true;
                }
        }
    }
    //Check diagonal right to left
    for(i=0; i<boardSize; i++){
        for(j=0; j<boardSize-3; j++){
            if(gameField[i][j] != '.')
                if(gameField[i][j] == gameField[i+1][j+1] && gameField[i+1][j+1] == gameField[i+2][j+2] && gameField[i+2][j+2] == gameField[i+3][j+3]){
                    gameField[i][j] += 'a' - 'A', gameField[i+1][j+1] += 'a' - 'A', gameField[i+2][j+2] += 'a' - 'A', gameField[i+3][j+3] += 'a' - 'A';
                    isOver = true;
                }
        }
    }
    //Check diagonal left to right
    for(i=0; i<boardSize; i++){
        for(j=3; j<boardSize; j++){
            if(gameField[i][j] != '.')
                if(gameField[i][j] == gameField[i+1][j-1] && gameField[i+1][j-1] == gameField[i+2][j-2] && gameField[i+2][j-2] == gameField[i+3][j-3]){
                    gameField[i][j] += 'a' - 'A', gameField[i+1][j-1] += 'a' - 'A', gameField[i+2][j-2] += 'a' - 'A', gameField[i+3][j-3] += 'a' - 'A';
                    isOver = true;
                }
        }
    }
    return isOver;
}

bool checkDraw(int boardSize, char gameField[20][20]){
    //Check if the game has no winner and no more moves
    bool isDraw = false;
    int i, j, emptyCells = 0;
    for(i=0; i<boardSize; i++)
        for(j=0; j<boardSize; j++)
            if(gameField[i][j] == '.')
                emptyCells++;
    if(emptyCells == 0)
        isDraw = true;
    return isDraw;
}

void vsComputerCalcMoveVal(int pointX, int pointY, int xIncrease, int yIncrease, char gameField[20][20], int boardSize, int *maxValueCurrentMove){
    int i, result = 0;
    for(i=0; i<4; i++){
        if(pointX >= 0 && pointX < boardSize && pointY >= 0 && pointY < boardSize){
            if(gameField[pointX][pointY] == 'O')
                result++;
            else if(gameField[pointX][pointY] == 'X')
                result-=2;
        }
        pointX+=xIncrease;
        pointY+=yIncrease;
    }
    //If there is a move that the enemy can do to finish the game, deny it. Otherwise if the value of the current move is greater than max value, change the max
    if(result == -5)
        result = 4;
    if(result > *maxValueCurrentMove){
        *maxValueCurrentMove = result;
    }
}

void vsComputerPlayMove(int boardSize, char gameField[20][20]){
    int i, j, moveValues[20],
        maxValueCurrentMove = 0, tempValueCurrentMove = 0,
        maxValueIndex = 0, maxValueAllMoves = 0;
    for(j=0; j<boardSize; j++){
        //Iterate through all moves
        for(i=0; i<boardSize; i++)
            if(gameField[i][j] != '.')
                break;
        if(i!=0){
            gameField[i-1][j] = 'O';

            //Calculate the value of each move
            tempValueCurrentMove = 0, maxValueCurrentMove = 0;

            //Horizontal effect of the move
            vsComputerCalcMoveVal(i-1, j-3, 0, 1, gameField, boardSize, &maxValueCurrentMove);
            vsComputerCalcMoveVal(i-1, j-2, 0, 1, gameField, boardSize, &maxValueCurrentMove);
            vsComputerCalcMoveVal(i-1, j-1, 0, 1, gameField, boardSize, &maxValueCurrentMove);
            vsComputerCalcMoveVal(i-1, j, 0, 1, gameField, boardSize, &maxValueCurrentMove);

            //Vertical effectiveness of the move
            vsComputerCalcMoveVal(i-4, j, 1, 0, gameField, boardSize, &maxValueCurrentMove);
            vsComputerCalcMoveVal(i-3, j, 1, 0, gameField, boardSize, &maxValueCurrentMove);
            vsComputerCalcMoveVal(i-2, j, 1, 0, gameField, boardSize, &maxValueCurrentMove);
            vsComputerCalcMoveVal(i-1, j, 1, 0, gameField, boardSize, &maxValueCurrentMove);

            //Diagonal from left to right effectiveness of the move
            vsComputerCalcMoveVal(i-4, j-3, 1, 1, gameField, boardSize, &maxValueCurrentMove);
            vsComputerCalcMoveVal(i-3, j-2, 1, 1, gameField, boardSize, &maxValueCurrentMove);
            vsComputerCalcMoveVal(i-2, j-1, 1, 1, gameField, boardSize, &maxValueCurrentMove);
            vsComputerCalcMoveVal(i-1, j, 1, 1, gameField, boardSize, &maxValueCurrentMove);

            //Diagonal from right to left effectiveness of the move
            vsComputerCalcMoveVal(i-4, j+3, 1, -1, gameField, boardSize, &maxValueCurrentMove);
            vsComputerCalcMoveVal(i-3, j+2, 1, -1, gameField, boardSize, &maxValueCurrentMove);
            vsComputerCalcMoveVal(i-2, j+1, 1, -1, gameField, boardSize, &maxValueCurrentMove);
            vsComputerCalcMoveVal(i-1, j, 1, -1, gameField, boardSize, &maxValueCurrentMove);

            //Write the value of the move to an array and undo the move
            moveValues[j] = maxValueCurrentMove;
            gameField[i-1][j] = '.';
        }
        else
            moveValues[j] = -100; //If the move can go outside the game field, make it impossible to do
    }
    //Do the move with highest value
    for(i=0; i<boardSize; i++){
        if(moveValues[i] > maxValueAllMoves){
            maxValueAllMoves = moveValues[i];
            maxValueIndex = i;
        }
    }

    //If the game field is completely empty, select middle of the game field to make a move
    int dotCount=0;
    for(i=0; i<boardSize; i++)
        for(j=0; j<boardSize; j++)
            if(gameField[i][j]=='.')
                dotCount++;
    if(dotCount == boardSize*boardSize-1)
        maxValueIndex = (boardSize-1)/2;

    for(i=0; i<boardSize; i++)
        if(gameField[i][maxValueIndex] != '.')
            break;
    if(i!=0)
        gameField[i-1][maxValueIndex] = 'O';
}


