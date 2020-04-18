#include "ConnectFourPlusUndo.h"

namespace cfgame{

// same readMove as the base class, except it takes UNDO as an input and calls undoMove
int ConnectFourPlusUndo::readMove(){
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
        } else if(input == "UNDO" && _moves.size() >= 1){
        	undoMove();
        	printGame();
        	std::cout << "enter move:" << std::endl;
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
    _moves.push_back(cellPosition);
    return cellPosition;
}

// loads same as the base class but also loads values of _moves vector
bool ConnectFourPlusUndo::loadFile(const std::string &filename){
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
		_moves.clear();
		int tempMovesSize;
		file >> tempMovesSize;
		for(int i=0; i<tempMovesSize; i++){
			int tmpVal;
			file >> tmpVal;
			_moves.push_back(tmpVal);
		}
	} else {
		result = false;
		std::cerr << "can not find a file named: " << filename << std::endl;
	}
	file.close();
	return result;
}

// saves same as the base class but also saves values of _moves vector
void ConnectFourPlusUndo::saveFile(const std::string &filename){
	std::ofstream file(filename);
	file << _state << " " << _isVsComputer << " " << _currentPlayer << " " << _rows << " " << _columns << std::endl;
	for(int i=0; i<_rows; i++){
		for(int j=0; j<_columns; j++)
			file << _gameCells[i][j].getValue() << " ";
		file << std::endl;
	}
	file << _moves.size() << std::endl;
	for(int i=0; i<_moves.size(); i++)
		file << _moves[i] << " ";
	file.close();
}

// same as play(int cellPosition), only removes a full cell and cellPosition variable is the last value of _moves vector
void ConnectFourPlusUndo::undoMove(){
	int i, cellPosition;
	cellPosition = _moves.back();
	for(i=0; i<_rows; i++)
	    if(_gameCells[i][cellPosition].getValue() != 0)
	            break;
    if(i!=0)
		_gameCells[i][cellPosition].setValue(0);
	_moves.pop_back();
	if(!_isVsComputer)
        _currentPlayer == 1 ? _currentPlayer = 0 : _currentPlayer = 1;
    else play();
}

}