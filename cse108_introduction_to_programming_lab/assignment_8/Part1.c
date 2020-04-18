int stringLength(char str[]){
	int i, result = 0;
	for(i=0; str[i]!='\0'; i++)
		result++;
	return result;
}

void toUppercase(char str[]){
	int i, upperLowerDiff;
	upperLowerDiff = 'a' - 'A';
	for(i=0; i<stringLength(str); i++)
		if(str[i] >='a' && str[i]<='z')
			str[i]-=upperLowerDiff;
}

int isAlphabetical(char c){
	int result = 0;
	if(c<='Z'&&c>='A')
		result = 1;
	return result;
}

void hist(char str[], int hist[27]){
	int i;
	for(i=0; i<ALPHABET; i++)
		hist[i] = 0;
	toUppercase(str);
	for(i=0; i<stringLength(str); i++){
		if(isAlphabetical(str[i]))
			hist[str[i]-'A']++;
		else
			hist[26]++;
	}
}
