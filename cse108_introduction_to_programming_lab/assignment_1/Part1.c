#include <stdio.h>

int readInt();

double readDouble();

char readChar();

int readInt(){
	int int_input;
	scanf(" %d",&int_input);
	return int_input;
}

double readDouble(){
	double double_input;
	scanf(" %lf",&double_input);
	return double_input;
}

char readChar(){
	char char_input;
	scanf(" %c",&char_input);
	return char_input;
}
