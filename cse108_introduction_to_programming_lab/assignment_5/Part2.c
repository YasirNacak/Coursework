double testResults2d(char questionNumbersArr[][5], int qNarrSize, char keysArr[], int keysArrSize){
	int trueAns = 0, falseAns = 0;
	int i, j;
	int flag;
	int keysArrInt[1005], ansArrInt[1005];
	double result = 0;
	for(i=0; i<qNarrSize; i++)	ansArrInt[i] = 0;
	for(i=0; i<keysArrSize; i++){
		switch(keysArr[i]){
			case 'A': keysArrInt[i] = 1;break;
			case 'B': keysArrInt[i] = 2;break;
			case 'C': keysArrInt[i] = 3;break;
			case 'D': keysArrInt[i] = 4;break;
			case 'E': keysArrInt[i] = 5;break;
		}
	}
	for(i=0; i<qNarrSize; i++){
		flag = 0;
		for(j=0; j<5; j++){
			if(questionNumbersArr[i][j] == '*' && flag == 1){
				ansArrInt[i] = -1;
			}
			else if(questionNumbersArr[i][j] == '*'){
				ansArrInt[i] = j+1;
				flag = 1;
			}
		}
	}
	for(i=0; i<keysArrSize ; i++){
		if(ansArrInt[i] == keysArrInt[i])
			trueAns++;
		else if(ansArrInt[i] == -1 || (ansArrInt[i] != keysArrInt[i] && ansArrInt[i]!=0))
			falseAns++;
	}
	printf("%d - %d\n", trueAns, falseAns);
	result = ((double)trueAns - (double)falseAns * 0.25)/(double)keysArrSize;
	return result;
}
