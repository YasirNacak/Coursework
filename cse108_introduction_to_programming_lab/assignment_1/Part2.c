#include <stdio.h>

int readInt();

double readDouble();

char readChar();

double calculateBMI(int height, double weight);

double getInfoAndCalculateBMI();

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

double calculateBMI(int height, double weight){
	double BMI, height_squared, height_in_cm;
	height_in_cm = height * 100;
	height_in_cm /= 10000;
	height_squared = height_in_cm * height_in_cm;
	BMI = weight/height_squared;
	return BMI;
}

double getInfoAndCalculateBMI(){
	int height = readInt();
	double weight = readDouble();
	return calculateBMI(height, weight);
}
