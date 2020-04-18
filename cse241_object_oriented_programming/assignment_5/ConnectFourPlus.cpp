#include "ConnectFourPlus.h"

namespace cfgame{

// same as the base class
int ConnectFourPlus::readMove(){
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

// checks only horizontally and vertically
void ConnectFourPlus::isVictoryOrDraw(){
	auto hasWinner = false, isDraw = false;
    int i, j;
    //Check from up to down.
    for(i=0; i<_rows-3; i++){
        for(j=0; j<_columns; j++){
            if(_gameCells[i][j].getValue()!=0&&_gameCells[i][j].getValue()!= 4&&_gameCells[i][j].getValue()<5){

                if( _gameCells[i+0][j+0].getValue()==_gameCells[i+1][j+0].getValue()&&
                	_gameCells[i+1][j+0].getValue()==_gameCells[i+2][j+0].getValue()&&
                	_gameCells[i+2][j+0].getValue()==_gameCells[i+3][j+0].getValue()){
					_gameCells[i+0][j+0].setValue(_gameCells[i+0][j+0].getValue() + 4),
                	_gameCells[i+1][j+0].setValue(_gameCells[i+1][j+0].getValue() + 4),
                	_gameCells[i+2][j+0].setValue(_gameCells[i+2][j+0].getValue() + 4),
                	_gameCells[i+3][j+0].setValue(_gameCells[i+3][j+0].getValue() + 4);
                    hasWinner = true;
                }
           	}
        }
    }
    //Check from left to right.
    for(i=0; i<_rows; i++){
        for(j=0; j<_columns-3; j++){
            if(_gameCells[i][j].getValue()!=0&&_gameCells[i][j].getValue()!=4&&_gameCells[i][j].getValue()<5){
                if( _gameCells[i+0][j+0].getValue()==_gameCells[i+0][j+1].getValue()&&
                	_gameCells[i+0][j+1].getValue()==_gameCells[i+0][j+2].getValue()&&
                	_gameCells[i+0][j+2].getValue()==_gameCells[i+0][j+3].getValue()){
                	_gameCells[i+0][j+0].setValue(_gameCells[i+0][j+0].getValue() + 4),
                	_gameCells[i+0][j+1].setValue(_gameCells[i+0][j+1].getValue() + 4),
                	_gameCells[i+0][j+2].setValue(_gameCells[i+0][j+2].getValue() + 4),
                	_gameCells[i+0][j+3].setValue(_gameCells[i+0][j+3].getValue() + 4);
                    hasWinner = true;
                }
           	}
        }
    }
    if(!hasWinner){
	    int emptyCells = 0;
	    for(int i=0; i<_rows; i++)
	        for(int j=0; j<_columns; j++)
	            if(_gameCells[i][j].getValue() == 0)
	                emptyCells++;
	    if(emptyCells == 0)
	        isDraw = true;
	} else {
		_state = 4;
	}
	if(isDraw)
		_state = 5;
}

// calculates only horizontal and vertical effectiveness
int ConnectFourPlus::allMoveVals(int sX, int sY){
	int maxValCurrMove = 0;
	//Horizontal effect of the move.
    calcMoveVal(sX-1, sY-3, 0, 1, maxValCurrMove);
    calcMoveVal(sX-1, sY-2, 0, 1, maxValCurrMove);
    calcMoveVal(sX-1, sY-1, 0, 1, maxValCurrMove);
	calcMoveVal(sX-1, sY+0, 0, 1, maxValCurrMove);

    //Vertical effectiveness of the move.
    calcMoveVal(sX-4, sY+0, 1, 0, maxValCurrMove);
    calcMoveVal(sX-3, sY+0, 1, 0, maxValCurrMove);
    calcMoveVal(sX-2, sY+0, 1, 0, maxValCurrMove);
    calcMoveVal(sX-1, sY+0, 1, 0, maxValCurrMove);
    return maxValCurrMove;
}

// same as the base class
bool ConnectFourPlus::loadFile(const std::string &filename){
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

// same as the base class
void ConnectFourPlus::saveFile(const std::string &filename){
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