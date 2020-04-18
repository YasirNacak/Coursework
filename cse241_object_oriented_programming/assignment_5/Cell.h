#ifndef CELL_H
#define CELL_H
#include <iostream>

namespace cfgame{
	// class of game cells
	class Cell{
	public:
		Cell();	// no parameter constructor
		void setPosition(char position);	// setter for _position
		void setRowNumber(int rowNumber);	// setter for _rowNumber
		void setValue(int value);	// setter for _value
		char getPosition();	// getter for _position
		int getRowNumber();	// getter for _rowNumber
		int getValue();	// getter for _value
	private:
		char _position;	// position of the cell (A, B, C..)
		int _rowNumber, _value;	// row number, from 0-input, value, 0 for empty, 1 for X and 2 for O
	};
}

#endif