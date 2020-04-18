int stringLength(char str[]){
	int i, result=0;
	for(i=0; str[i]!='\0'; i++)
		result++;
	return result;
}

int min(int a, int b){
	return a < b ? a : b;
}

char* interleave(char str1[], char str2[], char res[]){
	int i, j;
	int minlength;
	j=0;
	minlength = min(stringLength(str1),stringLength(str2));
	for(i=0; i<minlength; i++){
		res[j] = str1[i];
		res[j+1] = str2[i];
		j+=2;
	}
	if(minlength == stringLength(str1) && minlength != stringLength(str2)){
		for(i=0; i<stringLength(str2)-stringLength(str1); i++){
			res[j++] = str2[minlength+i];
		}
	} else if(minlength == stringLength(str2) && minlength != stringLength(str1)){
		for(i=0; i<stringLength(str1)-stringLength(str2); i++){
			res[j++] = str1[minlength+i];
		}
	}
	return res;
}
