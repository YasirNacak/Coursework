double testResults(int questionNumbersArr[], int qNArrSize, char answersArr[], int ansArrSize, char keysArr[], int keysArrSize){
	int trueAns=0, falseAns=0;
	int i;
	char totalAns[1005];
	for(i=0; i<keysArrSize; i++)	totalAns[i] = '0';
	for(i=0; i<qNArrSize; i++){
		totalAns[questionNumbersArr[i]-1] = answersArr[i];
	}
	for(i=0; i<keysArrSize; i++){
		if(totalAns[i] == keysArr[i])
			trueAns++;
		else if(totalAns[i] != keysArr[i] && totalAns[i] != '0')
			falseAns++;
	}
	return calculateResult(trueAns, falseAns, keysArrSize);
}

double calculateResult(int trueAns, int falseAns, int totalAns){
	return ((double)trueAns - (double)falseAns * 0.25)/(double)totalAns;
}
