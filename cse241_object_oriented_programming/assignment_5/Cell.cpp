#include "Cell.h"

namespace cfgame{

// sets basic values for cell
Cell::Cell(): _position('A'), _rowNumber(0), _value(0){
	// body has left blank intentionally
}

// simple setter
void Cell::setPosition(char position){
	_position = position;
}

// simple setter
void Cell::setRowNumber(int rowNumber){
	_rowNumber = rowNumber;
}

// simple setter
void Cell::setValue(int value){
	_value = value;
}

// simple getter
char Cell::getPosition(){
	return _position; 
}

// simple getter
int Cell::getRowNumber(){
	return _rowNumber;
}

// simple getter
int Cell::getValue(){
	return _value;
}

}