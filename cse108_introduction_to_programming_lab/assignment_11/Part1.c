int min(int k, int l);
void setRowValues(Score *row, char playerName[], int score);
int readTable(char filename[], Score table[], int *tableSize);
int writeTable(char filename[], Score table[], int tableSize);
void insertRow(Score row, Score table[], int *tableSize);
void insertRowAndSave(char filename[], Score row);

int min(int k, int l){
	int result;
	if(k<l) result = k;
	else result = l;
	return result;
}

void setRowValues(Score *row, char playerName[], int score){
	strcpy(row->name, playerName);
	row->score = score;
}

int readTable(char filename[], Score table[], int *tableSize){
	int result = 0;
	FILE *fileptr;
	Score row;
	fileptr = fopen(filename, "rb");
	if(fileptr == NULL) result = -1;
	while(fread(&row, sizeof(Score), 1, fileptr)){
		insertRow(row, table, tableSize);
	}
	fclose(fileptr);
	return result;
}

int writeTable(char filename[], Score table[], int tableSize){
	int result = 0;
	FILE *fileptr;
	fileptr = fopen(filename, "wb");
	if(fileptr == NULL) result = -1;
	fwrite(table, sizeof(Score), tableSize, fileptr);
	fclose(fileptr);
	return result;
}

void insertRow(Score row, Score table[], int *tableSize){
	int i = 0, insertPos = 0;
	for(i=0; i<*tableSize; i++){
		if(row.score < table[i].score)
			insertPos++;
	}
	if(*tableSize==0) insertPos=0;
	if(*tableSize<10) (*tableSize)++;
	if(*tableSize!=1){
		for(i=min(10, *tableSize); i>insertPos; i--){
			strcpy(table[i].name,table[i-1].name);
			table[i].score = table[i-1].score;
		}
		table[insertPos].score = row.score;
		strcpy(table[insertPos].name,row.name);
	} else {
		table[0].score = row.score;
		strcpy(table[0].name,row.name);
	}
}

void insertRowAndSave(char filename[], Score row){
	Score table[10];
	int tableSize = 0;
	readTable(filename, table, &tableSize);
	insertRow(row, table, &tableSize);
	writeTable(filename, table, tableSize);
}
