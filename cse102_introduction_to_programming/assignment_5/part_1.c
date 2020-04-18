#include <stdio.h>
#define GRIDSIZE 4

typedef enum {
    mined,empty,flaggedMined,flaggedEmpty,closedEmpty,closedMined
}
cell;

void initGrid(cell grid[][GRIDSIZE]);
void printGrid (cell grid[][GRIDSIZE]);
int openCell(cell grid[][GRIDSIZE], int x, int y);
void flagCell(cell grid[][GRIDSIZE], int x, int y);
int isCellEmpty(cell grid[][GRIDSIZE], int x, int y);
int isLocationLegal(int x, int y);
int asMain();

int main(){
    asMain();
    return 0;
}
void initGrid(cell grid[][GRIDSIZE]){
    grid[0][0] = closedEmpty;
    grid[0][1] = closedMined;
    grid[0][2] = closedMined;
    grid[0][3] = closedEmpty;
    grid[1][0] = closedEmpty;
    grid[1][1] = closedEmpty;
    grid[1][2] = closedMined;
    grid[1][3] = closedMined;
    grid[2][0] = closedEmpty;
    grid[2][1] = closedEmpty;
    grid[2][2] = closedEmpty;
    grid[2][3] = closedEmpty;
    grid[3][0] = closedEmpty;
    grid[3][1] = closedEmpty;
    grid[3][2] = closedEmpty;
    grid[3][3] = closedEmpty;
    /*
        ^
        Sets the values of game grid.
    */
}
void printGrid (cell grid[][GRIDSIZE]){
    int i, j;
    for(i=0; i<GRIDSIZE; i++){
        for(j=0; j<GRIDSIZE; j++){
            switch(grid[i][j]){
                case mined: printf("m"); break;
                case empty: printf("e"); break;
                case flaggedMined: printf("f"); break;
                case flaggedEmpty: printf("f"); break;
                case closedEmpty: printf("."); break;
                case closedMined: printf("."); break;
                default: break;
            }
        }
        printf("\n");
    }
    /*
        ^
        prints the game grid based on the status of the grids.
    */
}
int openCell(cell grid[][GRIDSIZE], int x, int y){
    int result = 1;
    int i, j;
    int xAway = -1, yAway = -1;
    if(isLocationLegal(x,y)){   /*first checks if the given location is legal*/
        for(i=0; i<3; i++){ /*double for's is for checking every grid between the given one*/
            xAway = -1;
            for(j=0; j<3; j++){
                if(isLocationLegal(x+xAway, y+yAway) && grid[x+xAway][y+yAway] == closedEmpty)  /*checks if the given cell is legal and closed*/
                    grid[x+xAway][y+yAway] = empty;
                xAway++;
            }
            yAway++;
        }
    } else {
        result = -2;
    }
    return result;
}
void flagCell(cell grid[][GRIDSIZE], int x, int y){
    if(isLocationLegal(x,y)){
        if(grid[x][y] == closedEmpty)
            grid[x][y] = flaggedEmpty;
        else if(grid[x][y] == closedMined)
            grid[x][y] = flaggedMined;
    }
}
int isCellEmpty(cell grid[][GRIDSIZE], int x, int y){
    int result;
    if(grid[x][y] == empty || grid[x][y] == closedEmpty)
        result = 1;
    else
        result = 0;
    return result;
    /*
        ^
        Checks if the given cell is either empty or closedEmpty
    */
}
int isLocationLegal(int x, int y){
    int result = 1;
    if(x > GRIDSIZE-1 || x < 0 || y > GRIDSIZE-1 || y < 0)
        result = 0;
    return result;
    /*
        ^
        Checks if the player goes out of the boundaries.
    */
}
int asMain(){
    int i, j;
    int x, y, operation, moveCount;
    int totalEmptyCell, openEmptyCell;
    int gameState;
    cell grid[GRIDSIZE][GRIDSIZE];
    /*
        ^
        Variable declarations.
    */

    moveCount = 0;
    totalEmptyCell = 0;
    openEmptyCell = 0;
    gameState = 0;
    /*
        ^
        Sets the variables to their default values. gameState is 0
        when the game needs to continue, 1 when the player wins and
        -1 when the player loses.
    */

    initGrid(grid);
    printGrid(grid);
    for(i=0; i<GRIDSIZE; i++)
        for(j=0; j<GRIDSIZE; j++)
            if(grid[i][j] == closedEmpty)
                totalEmptyCell++;
    /*
        ^
        Initializes the grid and counts total number of empty cells
        which is going to be used later for checking if the player
        win.
    */

    while(gameState == 0){
        printf("current number of moves: %d\n", moveCount);
        openEmptyCell = 0;
        printf("enter a location\n");
        scanf("%d%d",&x,&y);
        moveCount++;
        if(isLocationLegal(x,y)){
            if(grid[x][y] == closedMined || grid[x][y] == closedEmpty){ /*checks if the given cell is legal and empty*/
                printf("press 1 to open a cell and 2 to flag it\n");
                scanf("%d", &operation);
                if(grid[x][y] == closedMined && operation == 1){    /*if player wants to open a mined cell, game state becomes -1 which means the player lost*/
                    gameState = -1;
                } else {
                    switch(operation){  /*does action based on given operation*/
                        case 1: openCell(grid,x,y); break;
                        case 2: flagCell(grid,x,y); break;
                        default: printf("unrecognized move.\n"); break; /*if the given action is not open or flag*/
                    }
                }
            } else if(grid[x][y] == flaggedMined){  /*checks if the given cell is legal and flagged with mined status*/
                grid[x][y] = closedMined;
            } else if(grid[x][y] == flaggedEmpty){  /*checks if the given cell is legal and flagged with empty status*/
                grid[x][y] = closedEmpty;
            } else {
                printf("you can't do anything to this cell.\n");    /*if the given cell has no capacity to do an action*/
            }

        } else {
            printf("illegal location\n");   /*if the given location is illegal*/
        }
        printGrid(grid);
        for(i=0; i<GRIDSIZE; i++)   /*double for's for checking if the player is done with the game*/
            for(j=0; j<GRIDSIZE; j++)
                if(grid[i][j] == empty)
                    openEmptyCell++;
        if(openEmptyCell == totalEmptyCell){
            gameState = 1;
        }
    }
    if(gameState == 1)
        printf("you win.");
    else printf("you lose.");
    return 0;
}
