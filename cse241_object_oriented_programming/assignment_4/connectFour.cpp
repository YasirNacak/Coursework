/*
	Source file for connectFour.h, implementation of
 	non inline methods of the ConnectFour class
 	and inner private class of it, Cell.

 	Author  				Ahmet Yasir NACAK
 	Student_Number			161044042
 	Date_Of_Creation	   	2017-11-04 
*/
#include "connectFour.h"

/*
	No parameter constructor.
*/
ConnectFour::ConnectFour(){
	_rows = 5, _columns = 5, _state = 0, _currentPlayer = 0;
	_isVsComputer = false, _isLive = true, _isInMulti = false;
	createCells();
}

/*
	Constructor with row and column numbers. Does a range
	check for column variable because of the alphabet limit.
	And does a range check for row in case it is given
	a negative integer.
*/
ConnectFour::ConnectFour(int rows, int columns): _rows(rows), _columns(columns){
	_state = 0, _currentPlayer = 0;
	_isVsComputer = false, _isLive = true, _isInMulti = false;
	if(_rows < 0)
		_rows = 5;
	if(_columns < 0 || _columns > 26)
		_columns = 5;
	createCells();
}

/*
	Constructor with file name variable. Reads the file
	and makes all '*' characters as usable empty spaces
	and all ' ' characters as unusable spaces.
*/
ConnectFour::ConnectFour(const string &filename){
	int i = 0, rowCnt = 0, columnCnt = 0;
	ifstream file(filename);
	string tempString, * line;
	while(getline(file, tempString))
		rowCnt++;
	line = new string[rowCnt];
	file.close();
	file.open(filename, ifstream::in);
	while(getline(file, line[i])){
		if(line[i].size() > columnCnt)
			columnCnt = line[i].size();	
		i++;
	}

	for(int i=0; i<rowCnt; i++)
		for(int j=line[i].size(); j<columnCnt; j++)
			line[i][j] = ' ';

	_rows = rowCnt, _columns = columnCnt;
	_state = 1, _currentPlayer = 0;
	_isLive = true, _isVsComputer = false, _isInMulti = false;

	createCells();
	for(int i=0; i<_rows; i++){
		for(int j=0; j<_columns; j++){
			if(line[i][j] == ' ')
				_gameCells[i][j].setState(4);
			else if(line[i][j] == '*')
				_gameCells[i][j].setState(0);
		}
	}
	delete [] line;
	file.close();
}

/*
	Copy constructor.
*/
ConnectFour::ConnectFour(const ConnectFour &other){
	_columns = other._columns;
	_rows = other._rows;
	createCells();
	for(int i=0; i<_rows; i++)
		for(int j=0; j<_columns; j++)
			_gameCells[i][j].setState(other._gameCells[i][j].getState());
	_state = other._state;
	_currentPlayer = other._currentPlayer;
	_isVsComputer = other._isVsComputer;
	_isLive = other._isLive;
	_isInMulti = other._isInMulti;
}

/*
	Overload of assignment operator. Checks if the lvalue
	and rvalue of the assignment are the same so avoids
	deleting.
*/
ConnectFour& ConnectFour::operator=(const ConnectFour &other){
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
			_gameCells[i][j].setState(other._gameCells[i][j].getState());
	_state = other._state;
	_currentPlayer = other._currentPlayer;
	_isVsComputer = other._isVsComputer;
	_isLive = other._isLive;
	_isInMulti = other._isInMulti;
	return *this;
}

/*
	Destructor.
*/
ConnectFour::~ConnectFour(){
	for(int i=0; i<_rows; i++)
		delete [] _gameCells[i];
	delete [] _gameCells;
}

/*
	Mainly uses all the other methods of the class to
	play a game from start to end. Includes some extra for
	multi game mode.
*/
void ConnectFour::playGame(){
	if(_state == 0)
		readBoardFile();
	if(_state == 1)
		readType();
	while(_isLive && _state != 0 && _state != 1){
		if(_state == 2){
			_currentPlayer = 0;
			printGame();
			int cellPosition = readMove();
			play(cellPosition);
			isVictoryOrDraw();
			if(_state == 2){
				_currentPlayer = 2;
				play();
				isVictoryOrDraw();
			}
		} else if(_state == 3){
			printGame();
			int cellPosition = readMove();
			play(cellPosition);
			isVictoryOrDraw();
			printGame();
			if(_state == 3){
				cellPosition = readMove();
				play(cellPosition);
				isVictoryOrDraw();
				printGame();
			}
		}
		if(_state == 4){
			if(!_isVsComputer){
				_currentPlayer == 1 ? _currentPlayer = 0 : _currentPlayer = 1;
				cout << "the winner is player" << _currentPlayer+1 << endl;
			} else {
				if(_currentPlayer == 2)
					cout << "the winner is computer" << endl;
				else
					cout << "the winner is player" << endl;
			}
			printGame();
			_state = 6;
		}
		if(_state == 5){
			cout << "game ended in draw." << endl;
			printGame();
			_state = 6;
		}
		if(_state == 6){
			_isLive = 0;
		}
		if(_isInMulti){
			break;
		} else {
			calculateLivingCell();
			printLivingCell();
		}
	}
}

/*
	Checks the isLive variable and returns a boolean value
	based on opposite of that variable.
*/
bool ConnectFour::isGameEnded() const{
	return !_isLive;
}

/*
	Are equal operator for two instances of ConnectFour.
	First, compares rows and columns of two objects. If they
	are equal, proceeds to check each cell of given objects.
*/
bool ConnectFour::operator==(const ConnectFour &other) const{
	bool result = true;
	if(_rows == other._rows && _columns == other._columns){
		for(int i=0; i<_rows; i++){
			for(int j=0; j<_columns; j++){
				if(_gameCells[i][j] != other._gameCells[i][j])
					result = false;
			}
		}
	} else result = false;
	return result;
}

/*
	Not equal operator. Calls are equal operator and returns reverse of this.
*/
bool ConnectFour::operator!=(const ConnectFour &other) const{
	return !(*this == other);
}
/*
	Setter for multi mode variable for the object.
*/
void ConnectFour::setMulti(bool isInMulti){
	_isInMulti = isInMulti;
}

/*
	Calculator for static variable. Goes through given object and finds all 
	the cells that are either 'X' or 'O'.
*/
void ConnectFour::calculateLivingCell(){
	if(_isLive)
		for(int i=0; i<_rows; i++)
			for(int j=0; j<_columns; j++)
				if(_gameCells[i][j].getState() != 0 && _gameCells[i][j].getState() != 4)
					livingCell++;
}

/*
	Prints out the static variable and resets the value for further calculations.
*/
void ConnectFour::printLivingCell(){
	if(livingCell > 0)
		cout << "living cells: " << livingCell << endl;
	livingCell = 0;
}

/*
	This method uses new operator to create cell objects based on row and column
	variables. Sets each of the cells state to unusable.
*/
void ConnectFour::createCells(){
	_gameCells = new Cell*[_rows];
	for(int i=0; i<_rows; i++)
		_gameCells[i] = new Cell[_columns];
	for(int i=0; i<_rows; i++){
		for(int j=0; j<_columns; j++){
			_gameCells[i][j].setState(4);
			_gameCells[i][j].setRowNumber(i);
			_gameCells[i][j].setPosition(char(j+65));
		}
	}
}

/*
	Input reader method for board file. Checks for given file name and if it does
	not exist, calls the no parameter constructor for the object.
*/
void ConnectFour::readBoardFile(){
	string inFile;
	bool tempMulti = _isInMulti;
	cout << "enter a board config file:" << endl;
	cin >> inFile;
	ifstream file(inFile);
	if(file){
		ConnectFour temp(inFile);
		*this = temp;
		_state = 1;
		_isInMulti = tempMulti;
		file.close();
	} else {
		cerr << "could not find a file named " << inFile << "." << endl;
		ConnectFour temp;
		*this = temp;
		for(int i=0; i<_rows; i++)
			for(int j=0; j<_columns; j++)
				_gameCells[i][j].setState(0);
		_state = 1;
		_isInMulti = tempMulti;
	}
}

/*
	Input reader method for game type. Loops until a vaild input is given.
*/
void ConnectFour::readType(){
	string gameType;
	cout << "enter a game type:" << endl;
	cin >> gameType;

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
		} else if(!cin || gameType[0] != 'P' || gameType[0] != 'C' || gameType.size() > 1){
			cerr << "invalid input, try again." << endl;
			cin.clear();
			cin.ignore(256, '\n');
			cin >> gameType;
		}
	}
}

/*
	Input reader for move. Loops until a vaild input is given.	
*/
int ConnectFour::readMove(){
	string input, filename;
    int cellPosition = -1;
    cout << "enter move:" << endl;
    while(true){
    	cin >> input;
		if(input[0] >= 'A' && input[0] <= 'Z')
        	cellPosition = static_cast<int>(input[0] - 'A');
        if(cin.eof()){
        	_isLive = false;
        	cellPosition = -1;
        	break;
        } else if(input == "SAVE"){
    		cin >> filename;
    		saveFile(filename);
    	} else if(input == "LOAD"){
    		cin >> filename;
    		bool oldVsComputer = _isVsComputer;
    		if(loadFile(filename)){
    			cout << "game loaded" << endl;
    			if(oldVsComputer == false && _isVsComputer == true)
    				play();
    			printGame();
    		}
    	} else if(!cin || cellPosition < 0 || cellPosition >= _columns || input.size() > 1){
            cerr << "invalid input, try again" << endl;
            cin.clear();
            cin.ignore(256, '\n');
        } else {
        	break;
        }
    }
    return cellPosition;
}

/*
	No parameter play method. Does a move for the computer.
*/
void ConnectFour::play(){ 
	int i, j, maxValueIndex = 0, maxValueAllMoves = 0, 
		emptyCells = 0, totalCells = 0, startingPosition = 0;
	int * moveValues = new int[_columns];
    for(j=0; j<_columns; j++){
    	startingPosition = 0;
    	for(i=0; i<_rows; i++){
    		if(_gameCells[i][j].getState() == 0){
    			startingPosition = i;
    			break;
    		}
    	}
        //Iterate through all moves.
	    for(i=startingPosition; i<_rows; i++)
			if(_gameCells[i][j].getState() != 0)
				break;
	    if(i!=0){
    		//Calculate the value of each move.
	        _gameCells[i-1][j].setState(3);
            moveValues[j] = allMoveVals(i, j, 3);
            _gameCells[i-1][j].setState(0);
        } else {
            moveValues[j] = -100; 
        }
    }
    startingPosition = 0;
    //Find the move with highest value.
    for(i=0; i<_columns; i++){
        if(moveValues[i] > maxValueAllMoves){
            maxValueAllMoves = moveValues[i];
            maxValueIndex = i;
        }
    }
    
    //If the game field is completely empty, select middle of the game field to make a move.
    for(i=0; i<_rows; i++)
        for(j=0; j<_columns; j++)
            if(_gameCells[i][j].getState() == 0)
                emptyCells++;
	for(i=0; i<_rows; i++)
        for(j=0; j<_columns; j++)
            if(_gameCells[i][j].getState() != 4)
                totalCells++;                 
    if(emptyCells == --totalCells)
        maxValueIndex = (_columns-1)/2;
    //Do the move with highest value.
    for(i=0; i<_rows; i++){
    	if(_gameCells[i][maxValueIndex].getState() == 0){
    		startingPosition = i;
    		break;
    	}
    }
    for(i=startingPosition; i<_rows; i++)
        if(_gameCells[i][maxValueIndex].getState() != 0)
            break;
    if(i!=0)
        _gameCells[i-1][maxValueIndex].setState(3);
    delete [] moveValues;
}

/*
	Play method with a parameter. Does a move for a player based on the return value
	of readMove method.
*/
void ConnectFour::play(int cellPosition){
	int i, startingPosition = -1;
	for(i=0; i<_rows; i++){
		if(_gameCells[i][cellPosition].getState() == 0){
			startingPosition = i;
			break;
		}
	}
	if(startingPosition != -1){
		for(i=startingPosition; i<_rows; i++)
		    if(_gameCells[i][cellPosition].getState() != 0)
		            break;
	    if(i!=0 && startingPosition != -1){
	        if(_currentPlayer == 1)
	            _gameCells[i-1][cellPosition].setState(2);
	        else if(_currentPlayer == 0)
	            _gameCells[i-1][cellPosition].setState(1);
	    }
	    if(!_isVsComputer)
	        _currentPlayer == 1 ? _currentPlayer = 0 : _currentPlayer = 1;
	}
}

/*
	A helper function for computer. Calls another method and calculates maximum value
	of a cell after computer tried to play on this cell.
*/
int ConnectFour::allMoveVals(int sX, int sY, int mvState){
	int maxValCurrMove = 0;
	//Horizontal effect of the move.
    calcMoveVal(sX-1, sY-3, 0, 1, maxValCurrMove, mvState);
    calcMoveVal(sX-1, sY-2, 0, 1, maxValCurrMove, mvState);
    calcMoveVal(sX-1, sY-1, 0, 1, maxValCurrMove, mvState);
	calcMoveVal(sX-1, sY+0, 0, 1, maxValCurrMove, mvState);

    //Vertical effectiveness of the move.
    calcMoveVal(sX-4, sY+0, 1, 0, maxValCurrMove, mvState);
    calcMoveVal(sX-3, sY+0, 1, 0, maxValCurrMove, mvState);
    calcMoveVal(sX-2, sY+0, 1, 0, maxValCurrMove, mvState);
    calcMoveVal(sX-1, sY+0, 1, 0, maxValCurrMove, mvState);

    //Diagonal from left to right effectiveness of the move.
    calcMoveVal(sX-4, sY-3, 1, 1, maxValCurrMove, mvState);
    calcMoveVal(sX-3, sY-2, 1, 1, maxValCurrMove, mvState);
    calcMoveVal(sX-2, sY-1, 1, 1, maxValCurrMove, mvState);
    calcMoveVal(sX-1, sY+0, 1, 1, maxValCurrMove, mvState);

    //Diagonal from right to left effectiveness of the move.
    calcMoveVal(sX-4, sY+3, 1, -1, maxValCurrMove, mvState);
    calcMoveVal(sX-3, sY+2, 1, -1, maxValCurrMove, mvState);
    calcMoveVal(sX-2, sY+1, 1, -1, maxValCurrMove, mvState);
    calcMoveVal(sX-1, sY+0, 1, -1, maxValCurrMove, mvState);
    return maxValCurrMove;
}

/*
	Helper method of computer. Starts from given x and y coordinates and increments for each friend
	cell and decrements for each enemy cell.
*/
void ConnectFour::calcMoveVal(int pX, int pY, int xInc, int yInc, int &maxValCurrMove, int mvState){
	int i, result = 0, enmState;
	mvState == 1 ? enmState = 3 : enmState = 1;
    for(i=0; i<4; i++){
        if(pX >= 0 && pX < _rows && pX >= 0 && pY < _columns){
            if(_gameCells[pX][pY].getState() == mvState)
                result++;
            else if(_gameCells[pX][pY].getState() == enmState)
                result-=2;
        }
        pX+=xInc;
        pY+=yInc;
    }

    if(result == -5) result = 4;
    if(result > maxValCurrMove) maxValCurrMove = result;
}

/*
	Prints out the game field using startdart output.
*/
void ConnectFour::printGame() const{
	for(int i=0; i<_columns; i++)
		cout << char(i+'A') << " ";
	cout << endl;
	for(int i=0; i<_rows; i++){
		for(int j=0; j<_columns; j++)
			cout << _gameCells[i][j] << " ";
		cout << endl;
	}
}

/*
	Searches game field for victory or draw situations. This method could be shorter
	but made long intentionally for good reading.
*/
void ConnectFour::isVictoryOrDraw(){
	auto hasWinner = false, isDraw = false;
    int i, j;
    //Check from up to down.
    for(i=0; i<_rows-3; i++){
        for(j=0; j<_columns; j++){
            if(_gameCells[i][j].getState()!=0&&_gameCells[i][j].getState()!= 4&&_gameCells[i][j].getState()<5){

                if( _gameCells[i+0][j+0].getState()==_gameCells[i+1][j+0].getState()&&
                	_gameCells[i+1][j+0].getState()==_gameCells[i+2][j+0].getState()&&
                	_gameCells[i+2][j+0].getState()==_gameCells[i+3][j+0].getState()){
					_gameCells[i+0][j+0].setState(_gameCells[i+0][j+0].getState() + 4),
                	_gameCells[i+1][j+0].setState(_gameCells[i+1][j+0].getState() + 4),
                	_gameCells[i+2][j+0].setState(_gameCells[i+2][j+0].getState() + 4),
                	_gameCells[i+3][j+0].setState(_gameCells[i+3][j+0].getState() + 4);
                    hasWinner = true;
                }
           	}
        }
    }
    //Check from left to right.
    for(i=0; i<_rows; i++){
        for(j=0; j<_columns-3; j++){
            if(_gameCells[i][j].getState()!=0&&_gameCells[i][j].getState()!=4&&_gameCells[i][j].getState()<5){
                if( _gameCells[i+0][j+0].getState()==_gameCells[i+0][j+1].getState()&&
                	_gameCells[i+0][j+1].getState()==_gameCells[i+0][j+2].getState()&&
                	_gameCells[i+0][j+2].getState()==_gameCells[i+0][j+3].getState()){
                	_gameCells[i+0][j+0].setState(_gameCells[i+0][j+0].getState() + 4),
                	_gameCells[i+0][j+1].setState(_gameCells[i+0][j+1].getState() + 4),
                	_gameCells[i+0][j+2].setState(_gameCells[i+0][j+2].getState() + 4),
                	_gameCells[i+0][j+3].setState(_gameCells[i+0][j+3].getState() + 4);
                    hasWinner = true;
                }
           	}
        }
    }

    //Check diagonal from left to right.
    for(i=0; i<_rows-3; i++){
        for(j=0; j<_columns; j++){
            if(_gameCells[i][j].getState()!=0&&_gameCells[i][j].getState()!=4&&_gameCells[i][j].getState()<5){
                if( _gameCells[i+0][j+0].getState()==_gameCells[i+1][j+1].getState()&&
                	_gameCells[i+1][j+1].getState()==_gameCells[i+2][j+2].getState()&&
                	_gameCells[i+2][j+2].getState()==_gameCells[i+3][j+3].getState()){
                	_gameCells[i+0][j+0].setState(_gameCells[i+0][j+0].getState() + 4),
                	_gameCells[i+1][j+1].setState(_gameCells[i+1][j+1].getState() + 4),
                	_gameCells[i+2][j+2].setState(_gameCells[i+2][j+2].getState() + 4),
                	_gameCells[i+3][j+3].setState(_gameCells[i+3][j+3].getState() + 4);
                    hasWinner = true;
                }
            }
        }
    }

    //Check diagonal from right to left.
    for(i=0; i<_rows-3; i++){
        for(j=3; j<_columns; j++){
            if(_gameCells[i][j].getState()!=0&&_gameCells[i][j].getState()!=4&&_gameCells[i][j].getState()<5){
                if( _gameCells[i+0][j+0].getState()==_gameCells[i+1][j-1].getState()&&
                	_gameCells[i+1][j-1].getState()==_gameCells[i+2][j-2].getState()&&
                	_gameCells[i+2][j-2].getState()==_gameCells[i+3][j-3].getState()){
                    _gameCells[i+0][j+0].setState(_gameCells[i+0][j+0].getState() + 4),
                	_gameCells[i+1][j-1].setState(_gameCells[i+1][j-1].getState() + 4),
                	_gameCells[i+2][j-2].setState(_gameCells[i+2][j-2].getState() + 4),
                	_gameCells[i+3][j-3].setState(_gameCells[i+3][j-3].getState() + 4);
                    hasWinner = true;
                }
            }
        }
    }
    if(!hasWinner){
	    int emptyCells = 0;
	    for(int i=0; i<_rows; i++)
	        for(int j=0; j<_columns; j++)
	            if(_gameCells[i][j].getState() == 0)
	                emptyCells++;
	    if(emptyCells == 0)
	        isDraw = true;
	} else {
		_state = 4;
	}
	if(isDraw)
		_state = 5;
}

/*
	Resets the game field and fills it up based on the contents of given file.
*/
bool ConnectFour:: loadFile(const string &filename){
	bool result;
	ifstream file(filename);
	
	//Check validity of the file.
	if(file){
		result = true;
		for(int i=0; i<_rows; i++)
			delete [] _gameCells[i];
		delete [] _gameCells;
		file >> _state >> _isVsComputer >> _currentPlayer >> _rows >> _columns;
		createCells();
		for(int i=0; i<_rows; i++)
			for(int j=0; j<_columns; j++){
				int tempState;
				file >> tempState;
				_gameCells[i][j].setState(tempState);
			}

	} else {
		result = false;
		cerr << "can not find a file named: " << filename << endl;
	}
	file.close();
	return result;
}

/*
	Prints out important variables and game field of the object. Uses same format
	as loadFile method.
*/
void ConnectFour:: saveFile(const string &filename) const{
	ofstream file(filename);
	file << _state << " " << _isVsComputer << " " << _currentPlayer << " " << _rows << " " << _columns << endl;
	for(int i=0; i<_rows; i++){
		for(int j=0; j<_columns; j++)
			file << _gameCells[i][j].getState() << " ";
		file << endl;
	}
	file.close();
}

/*
	No parameter constructor of Cell.
*/
ConnectFour::Cell::Cell(){
	_position = 'A';
	_rowNumber = 0;
	_state = 4;
}

/*
	Copy constructor of Cell.
*/
ConnectFour::Cell::Cell(const ConnectFour::Cell &other):
	_state(other._state), _position(other._position), _rowNumber(other._rowNumber){
	// this has left blank intentionally
}

/*
	Overload of assignment of Cell.
*/
ConnectFour::Cell& ConnectFour::Cell::operator=(const ConnectFour::Cell &other){
	_state = other._state, _position = other._position, _rowNumber = other._rowNumber;
	return *this;
}

/*
	Destructor of Cell.
*/
ConnectFour::Cell::~Cell(){
	// this has left blank intentionally
}

/*
	Are equal operator of Cell. Compares all variables of Cell object.
*/
bool ConnectFour::Cell::operator==(const ConnectFour::Cell &other) const{
	if(_state == other._state && _position == other._position && _rowNumber == other. _rowNumber)
		return true;
	return false;
}

/*
	Not equal operator of Cell. Returns opposite of are equal operator.
*/
bool ConnectFour::Cell::operator!=(const ConnectFour::Cell &other) const{
	return !(*this == other);
}

/*
	Prefix increment.
*/
ConnectFour::Cell& ConnectFour::Cell::operator++(){
	if(_state == 0) _state = 1;
	else if(_state == 1) _state = 2;
	else if(_state == 2) _state = 3;
	else if(_state == 3) _state = 1;
	return *this;
}

/*
	Postfix increment.
*/
ConnectFour::Cell ConnectFour::Cell::operator++(int){
	ConnectFour::Cell temp(*this);
	if(_state == 0) _state = 1;
	else if(_state == 1) _state = 2;
	else if(_state == 2) _state = 3;
	else if(_state == 3) _state = 1;
	return temp;
}

/*
	Prefix decrement.
*/
ConnectFour::Cell& ConnectFour::Cell::operator--(){
	if(_state == 0) _state = 3;
	else if(_state == 1) _state = 0;
	else if(_state == 2) _state = 1;
	else if(_state == 3) _state = 2;
	return *this;
}

/*
	Postfix decrement.
*/
ConnectFour::Cell ConnectFour::Cell::operator--(int){
	ConnectFour::Cell temp(*this);
	if(_state == 0) _state = 3;
	else if(_state == 1) _state = 0;
	else if(_state == 2) _state = 1;
	else if(_state == 3) _state = 2;
	return temp;
}

/*
	Stream extraction overload of Cell class. Takes all private
	variables of cell as input.
*/
istream& operator>>(istream &in, ConnectFour::Cell &myCell){
	in >> myCell._state >> myCell._rowNumber >> myCell._position;
	return in;
}

/*
	Stream insertion overload of Cell class. Prints out a character
	based on _state variable of the object.
*/
ostream& operator<<(ostream &out, const ConnectFour::Cell &myCell){
	int tempState = myCell.getState(); 
	if(tempState == 0)
		out << '.';
	else if(tempState == 1)
		out << 'X';
	else if(tempState == 2 || tempState == 3)
		out << 'O';
	else if(tempState == 5)
		out << 'x';
	else if(tempState == 6 || tempState == 7)
		out << 'o';
	else
		out << ' ';
	return out;
}