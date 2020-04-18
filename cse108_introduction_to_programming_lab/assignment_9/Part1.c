int isStuck(TILES maze[100][100], int currentTile[]){
	int result = 0;
	if(maze[currentTile[0]-1][currentTile[1]] == WALL)
		result++;
	if(maze[currentTile[0]+1][currentTile[1]] == WALL)
		result++;
	if(maze[currentTile[0]][currentTile[1]-1] == WALL)
		result++;
	if(maze[currentTile[0]][currentTile[1]+1] == WALL)
		result++;
	if(result == 4)
		result = 1;
	else
		result = 0;
	return result;
}



int isExit(TILES maze[100][100], int currentTile[]){
	int result = 0;
	if(maze[currentTile[0]-1][currentTile[1]] == BORDER)
		result++;
	if(maze[currentTile[0]+1][currentTile[1]] == BORDER)
		result++;
	if(maze[currentTile[0]][currentTile[1]-1] == BORDER)
		result++;
	if(maze[currentTile[0]][currentTile[1]+1] == BORDER)
		result++;
	if(result > 0)
		result = 1;
	return result;
}



int exitFromMaze(TILES maze[100][100], int currentTile[2]){
	int result = 0;
	int aroundTiles[2];
	if(maze[currentTile[0]][currentTile[1]] != EMPTY)
		return -1;
	else if(!isStuck(maze, currentTile)){
		maze[currentTile[0]][currentTile[1]] = USED;
		result += isExit(maze, currentTile);

		aroundTiles[0] = currentTile[0]-1;
		aroundTiles[1] = currentTile[1];
		if(maze[aroundTiles[0]][aroundTiles[1]] == EMPTY)
			result += exitFromMaze(maze, aroundTiles);

		aroundTiles[0] = currentTile[0]+1;
		aroundTiles[1] = currentTile[1];
		if(maze[aroundTiles[0]][aroundTiles[1]] == EMPTY)
			result += exitFromMaze(maze, aroundTiles);

		aroundTiles[0] = currentTile[0];
		aroundTiles[1] = currentTile[1]+1;
		if(maze[aroundTiles[0]][aroundTiles[1]] == EMPTY)
			result += exitFromMaze(maze, aroundTiles);

		aroundTiles[0] = currentTile[0];
		aroundTiles[1] = currentTile[1]-1;
		if(maze[aroundTiles[0]][aroundTiles[1]] == EMPTY)
			result += exitFromMaze(maze, aroundTiles);
	}
	return result;
}
