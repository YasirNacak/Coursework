#ifndef _CONNECTFOUR_H__
#define _CONNECTFOUR_H__
#include <iostream>
#include <fstream>
#include <string>
#include <vector>

using namespace std;

class ConnectFour{
public:
	ConnectFour();
	ConnectFour(int height, int width);
	ConnectFour(const string &fileName);

	void playGame();
	bool isGameEnded();
	bool compareGames(ConnectFour other);
	int calculateLivingCell();
	static void printLivingCell();
	static void resetLivingCell();
	const inline int getWidth() const{
		return width;
	}
	const inline int getHeight() const{
		return height;
	}
private:
	class Cell{
	public:
		Cell();
		Cell(char cellPosition, int cellRow, char cellValue);
		const inline char getValue() const{
			return cellValue;
		}
		const inline char getPosition() const{
			return cellPosition;
		}
		const inline int  getRow() const{
			return cellRow;
		}
		void setValue(char cellVal){
			cellValue = cellVal;
		}
		void setPosition(char cellPos){
			cellPosition = cellPos;
		}
		void setRow(int cellRw){
			cellRow = cellRw;
		}
	private:
		char cellValue, cellPosition;
		int cellRow;
	};
private:
	vector<vector<Cell>> gameCells;
	int width, height, gameState, currentPlayer;
	static int livingCell;
	bool playerVsAI, isGameLive;
private:
	int  allMoveVals(int startX, int startY, char playerMove);
	void createGameCells();
	void readWidth();
	void readHeight();
	void readGameType();
	int  readMove();
	void play();
	void play(int inputToInt);
	void calcMoveVal(int pointX, int pointY, int xIncrease, int yIncrease, int &maxValueCurrentMove, char playerMove);
	void drawGameField();
	void saveGame(const string &fileName);
	bool loadGame(const string &fileName);
	bool checkVictory();
	bool checkDraw();
};

#endif