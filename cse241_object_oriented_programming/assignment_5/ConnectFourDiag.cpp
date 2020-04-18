#include "ConnectFourDiag.h"

namespace cfgame{

void ConnectFourDiag::isVictoryOrDraw(){
	auto hasWinner = false, isDraw = false;
    int i, j;
	//Check diagonal from left to right.
    for(i=0; i<_rows-3; i++){
        for(j=0; j<_columns; j++){
            if(_gameCells[i][j].getValue()!=0&&_gameCells[i][j].getValue()!=4&&_gameCells[i][j].getValue()<5){
                if( _gameCells[i+0][j+0].getValue()==_gameCells[i+1][j+1].getValue()&&
                	_gameCells[i+1][j+1].getValue()==_gameCells[i+2][j+2].getValue()&&
                	_gameCells[i+2][j+2].getValue()==_gameCells[i+3][j+3].getValue()){
                	_gameCells[i+0][j+0].setValue(_gameCells[i+0][j+0].getValue() + 4),
                	_gameCells[i+1][j+1].setValue(_gameCells[i+1][j+1].getValue() + 4),
                	_gameCells[i+2][j+2].setValue(_gameCells[i+2][j+2].getValue() + 4),
                	_gameCells[i+3][j+3].setValue(_gameCells[i+3][j+3].getValue() + 4);
                    hasWinner = true;
                }
            }
        }
    }

    //Check diagonal from right to left.
    for(i=0; i<_rows-3; i++){
        for(j=3; j<_columns; j++){
            if(_gameCells[i][j].getValue()!=0&&_gameCells[i][j].getValue()!=4&&_gameCells[i][j].getValue()<5){
                if( _gameCells[i+0][j+0].getValue()==_gameCells[i+1][j-1].getValue()&&
                	_gameCells[i+1][j-1].getValue()==_gameCells[i+2][j-2].getValue()&&
                	_gameCells[i+2][j-2].getValue()==_gameCells[i+3][j-3].getValue()){
                    _gameCells[i+0][j+0].setValue(_gameCells[i+0][j+0].getValue() + 4),
                	_gameCells[i+1][j-1].setValue(_gameCells[i+1][j-1].getValue() + 4),
                	_gameCells[i+2][j-2].setValue(_gameCells[i+2][j-2].getValue() + 4),
                	_gameCells[i+3][j-3].setValue(_gameCells[i+3][j-3].getValue() + 4);
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

int ConnectFourDiag::allMoveVals(int sX, int sY){
	int maxValCurrMove;
	//Diagonal from left to right effectiveness of the move.
    calcMoveVal(sX-4, sY-3, 1, 1, maxValCurrMove);
    calcMoveVal(sX-3, sY-2, 1, 1, maxValCurrMove);
    calcMoveVal(sX-2, sY-1, 1, 1, maxValCurrMove);
    calcMoveVal(sX-1, sY+0, 1, 1, maxValCurrMove);

    //Diagonal from right to left effectiveness of the move.
    calcMoveVal(sX-4, sY+3, 1, -1, maxValCurrMove);
    calcMoveVal(sX-3, sY+2, 1, -1, maxValCurrMove);
    calcMoveVal(sX-2, sY+1, 1, -1, maxValCurrMove);
    calcMoveVal(sX-1, sY+0, 1, -1, maxValCurrMove);
    return maxValCurrMove;
}

}