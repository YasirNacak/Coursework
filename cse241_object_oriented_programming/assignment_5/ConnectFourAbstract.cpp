#include "ConnectFourAbstract.h"

namespace cfgame{

ConnectFourAbstract::ConnectFourAbstract(){
	_rows = 5, _columns = 5, _currentPlayer = 0,
	_state = 0, _isLive = 1, _isVsComputer = 0;
	createCells();
}

ConnectFourAbstract::ConnectFourAbstract(int rows, int columns): _rows(rows), _columns(columns){
	_currentPlayer = 0, _state = 0, _isLive = 1, _isVsComputer = 0;
	if(_rows < 0) _rows = 5;
	if(_columns < 0 || _columns > 26) _columns = 5;
	createCells();
}

ConnectFourAbstract::ConnectFourAbstract(const ConnectFourAbstract &other){
	_columns = other._columns;
	_rows = other._rows;
	createCells();
	for(int i=0; i<_rows; i++)
		for(int j=0; j<_columns; j++)
			_gameCells[i][j].setValue(other._gameCells[i][j].getValue());
	_state = other._state;
	_currentPlayer = other._currentPlayer;
	_isVsComputer = other._isVsComputer;
	_isLive = other._isLive;
}

ConnectFourAbstract& ConnectFourAbstract::operator=(const ConnectFourAbstract &other){
	if(this == &other)
		return *this;
	for(int i=0; i<_rows; i++)
		delete [] _gameCells[i];
	delete [] _gameCells;
	_columns = other._columns;
	_rows = other._rows;
	createCells();
	for(int i=0; i<_rows; i++)
		for(int j=0; j<_columns; j++)
			_gameCells[i][j].setValue(other._gameCells[i][j].getValue());
	_state = other._state;
	_currentPlayer = other._currentPlayer;
	_isVsComputer = other._isVsComputer;
	_isLive = other._isLive;
	return *this;
}

ConnectFourAbstract::~ConnectFourAbstract(){
	for(int i=0; i<_rows; i++)
		delete [] _gameCells[i];
	delete [] _gameCells;
}

void ConnectFourAbstract::playGame(){
	while(_isLive){
		if(_state == 0)
			readSize();
		if(_state == 1)
			readType();
		if(_state == 2){
			_currentPlayer = 0;
			printGame();
			int cellPosition = readMove();
			play(cellPosition);
			if(_rows > 0) isVictoryOrDraw();
			if(_state == 2){
				_currentPlayer = 2;
				play();
				if(_rows > 0) isVictoryOrDraw();
			}
		} else if(_state == 3){
			printGame();
			int cellPosition = readMove();
			play(cellPosition);
			if(_rows > 0) isVictoryOrDraw();
			printGame();
			if(_state == 3){
				cellPosition = readMove();
				play(cellPosition);
				if(_rows > 0) isVictoryOrDraw();
				printGame();
			}
		}
		if(_state == 4){
			if(!_isVsComputer){
				_currentPlayer == 1 ? _currentPlayer = 0 : _currentPlayer = 1;
				std::cout << "the winner is player" << _currentPlayer+1 << std::endl;
			} else {
				if(_currentPlayer == 2)
					std::cout << "the winner is computer" << std::endl;
				else
					std::cout << "the winner is player" << std::endl;
			}
			printGame();
			_state = 6;
		}
		if(_state == 5){
			std::cout << "game ended in draw." << std::endl;
			printGame();
			_state = 6;
		}
		if(_state == 6){
			_isLive = 0;
		}
	}
}

bool ConnectFourAbstract::isGameEnded() const{
	return !_isLive;
}

void ConnectFourAbstract::createCells(){
	_gameCells = new Cell*[_rows];
	for(int i=0; i<_rows; i++)
		_gameCells[i] = new Cell[_columns];
	for(int i=0; i<_rows; i++){
		for(int j=0; j<_columns; j++){
			_gameCells[i][j].setValue(0);
			_gameCells[i][j].setRowNumber(i);
			_gameCells[i][j].setPosition(char(j+65));
		}
	}
}

void ConnectFourAbstract::readSize(){
	int colCount;
	std::cout << "please enter a board width: ";
	std::cin >> colCount;

	//Loop below breaks only if a valid input is given.
	while(true){
		if(colCount > 3 && colCount < 27)
			break;
		else if(!std::cin || colCount <= 3 || colCount >= 27){
			std::cerr << "invalid input, try again." << std::endl;
			std::cin.clear();
			std::cin.ignore(256, '\n');
			std::cin >> colCount;
		}
	}
	_columns = colCount;

	int rowCount;
	std::cout << "please enter a board height: ";
	std::cin >> rowCount;

	//Loop below breaks only if a valid input is given.
	while(true){
		if(rowCount > 3)
			break;
		else if(!std::cin || rowCount <= 3){
			std::cerr << "invalid input, try again." << std::endl;
			std::cin.clear();
			std::cin.ignore(256, '\n');
			std::cin >> rowCount;
		}
	}
	_rows = rowCount;
	createCells();
	_state = 1;
}

void ConnectFourAbstract::readType(){
	std::string gameType;
	std::cout << "enter a game type:";
	std::cin >> gameType;

	while(true){
		if(gameType[0] == 'P' && gameType.size() == 1){
			_isVsComputer = false;
			_state = 3;
			break;
		}
		else if(gameType[0] == 'C' && gameType.size() == 1){
			_isVsComputer = true;
			_state = 2;
			break;
		} else if(!std::cin || gameType[0] != 'P' || gameType[0] != 'C' || gameType.size() > 1){
			std::cerr << "invalid input, try again." << std::endl;
			std::cin.clear();
			std::cin.ignore(256, '\n');
			std::cin >> gameType;
		}
	}
}

int ConnectFourAbstract::readMove(){
	std::string input, filename;
    int cellPosition = -1;
    std::cout << "enter move:" << std::endl;
    while(true){
    	std::cin >> input;
		if(input[0] >= 'A' && input[0] <= 'Z')
        	cellPosition = static_cast<int>(input[0] - 'A');
        if(std::cin.eof()){
        	_isLive = false;
        	cellPosition = -1;
        	for(int i=0; i<_rows; i++)
				delete [] _gameCells[i];
			delete [] _gameCells;
			_rows = 0, _columns = 0;
        	break;
        } else if(input == "SAVE"){
    		std::cin >> filename;
    		saveFile(filename);
    	} else if(input == "LOAD"){
    		std::cin >> filename;
    		bool oldVsComputer = _isVsComputer;
    		if(loadFile(filename)){
    			std::cout << "game loaded" << std::endl;
    			if(oldVsComputer == false && _isVsComputer == true)
    				play();
    			printGame();
    		}
    	} else if(!std::cin || cellPosition < 0 || cellPosition >= _columns || input.size() > 1){
            std::cerr << "invalid input, try again" << std::endl;
            std::cin.clear();
            std::cin.ignore(256, '\n');
        } else {
        	break;
        }
    }
    return cellPosition;
}

void ConnectFourAbstract::play(int cellPosition){
	int i;
	for(i=0; i<_rows; i++)
	    if(_gameCells[i][cellPosition].getValue() != 0)
	            break;
    if(i!=0){
        if(_currentPlayer == 0)
            _gameCells[i-1][cellPosition].setValue(1);
        else
            _gameCells[i-1][cellPosition].setValue(2);
    }
    if(!_isVsComputer)
        _currentPlayer == 1 ? _currentPlayer = 0 : _currentPlayer = 1;
}

void ConnectFourAbstract::play(){
	int i, j, maxValueIndex = 0, maxValueAllMoves = 0;
	int * moveValues = new int[_columns];
    for(j=0; j<_columns; j++){
        //Iterate through all moves.
        for(i=0; i<_rows; i++)
            if(_gameCells[i][j].getValue() != 0)
                break;
        if(i!=0){
            _gameCells[i-1][j].setValue(2);
            //Calculate the value of each move.
            moveValues[j] = allMoveVals(i, j);
            _gameCells[i-1][j].setValue(0);
        } else{//If the move can go outside the game field, make it impossible to do.
            moveValues[j] = -100; 
    	}
    }
    //Find the move with highest value.
    for(i=0; i<_columns; i++){
        if(moveValues[i] > maxValueAllMoves){
            maxValueAllMoves = moveValues[i];
            maxValueIndex = i;
        }
    }

    //If the game field is completely empty, select middle of the game field to make a move.
    decltype(i) dotCount = 0;
    for(i=0; i<_rows; i++)
        for(j=0; j<_columns; j++)
            if(_gameCells[i][j].getValue() == 0)
                dotCount++;
    if(dotCount == _rows*_columns-1)
        maxValueIndex = (_columns-1)/2;

    //Do the move with highest value.
    for(i=0; i<_rows; i++)
        if(_gameCells[i][maxValueIndex].getValue() != 0)
            break;
    if(i!=0)
        _gameCells[i-1][maxValueIndex].setValue(2);
}

void ConnectFourAbstract::calcMoveVal(int pX, int pY, int xInc, int yInc, int &maxValCurrMove){
	int i, result = 0;
    for(i=0; i<4; i++){
        if(pX >= 0 && pX < _rows && pX >= 0 && pY < _columns){
            if(_gameCells[pX][pY].getValue() == 2)
                result++;
            else if(_gameCells[pX][pY].getValue() == 1)
                result-=2;
        }
        pX+=xInc;
        pY+=yInc;
    }

    if(result == -5) result = 4;
    if(result > maxValCurrMove) maxValCurrMove = result;
}

void ConnectFourAbstract::printGame() const{
	for(int i=0; i<_columns; i++)
		std::cout << char(i+65) << " ";
	std::cout << std::endl;
	for(int i=0; i<_rows; i++){
		for(int j=0; j<_columns; j++){
			switch(_gameCells[i][j].getValue()){
				case 0:
				std::cout << "." << " ";
				break;
				case 1:
				std::cout << "X" << " ";
				break;
				case 2:
				std::cout << "O" << " ";
				break;
				case 5:
				std::cout << "x" << " ";
				break;
				case 6:
				std::cout << "o" << " ";
				break;
				default:
				std::cout << " ";
				break;
			}
		}
		std::cout << std::endl;
	}
}

bool ConnectFourAbstract::loadFile(const std::string &filename){
	bool result;
	std::ifstream file(filename);
	if(file){
		result = true;
		for(int i=0; i<_rows; i++)
			delete [] _gameCells[i];
		delete [] _gameCells;
		file >> _state >> _isVsComputer >> _currentPlayer >> _rows >> _columns;
		createCells();
		for(int i=0; i<_rows; i++){
			for(int j=0; j<_columns; j++){
				int tempValue;
				file >> tempValue;
				_gameCells[i][j].setValue(tempValue);
			}
		}
	} else {
		result = false;
		std::cerr << "can not find a file named: " << filename << std::endl;
	}
	file.close();
	return result;
}

void ConnectFourAbstract:: saveFile(const std::string &filename){
	std::ofstream file(filename);
	file << _state << " " << _isVsComputer << " " << _currentPlayer << " " << _rows << " " << _columns << std::endl;
	for(int i=0; i<_rows; i++){
		for(int j=0; j<_columns; j++)
			file << _gameCells[i][j].getValue() << " ";
		file << std::endl;
	}
	file.close();
}

}