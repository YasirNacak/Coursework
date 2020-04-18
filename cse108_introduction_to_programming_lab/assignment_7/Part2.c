 #include <stdio.h>

int stringLength(char str[]);
char* reverse(char str[], char rev[]);
char* tostr(int num, char str[]);
char* combine(char str1[], char str2[], char str3[], char res[]);
char* convert(char str[], int num, char res[]);

int main2(){
	char str[255] = "herhalde", str2[255] = "turta", str3[255] = "donmus", res[255];
	int dec = 65109;
	printf("reverse of %s: %s\n", str, reverse(str,res));
	printf("tostr(%d):%s\n", dec, tostr(dec, res));
	printf("combine(%s,%s,%s):%s\n", str, str2, str3, combine(str,str2,str3,res));
	printf("convert(%s,%d):%s\n", str, dec, convert(str,dec,res));
	return 0;
}

int maximum(int a, int b){
	return a>b ? a : b;
}
int stringLength(char str[]){
	int i=0, size=0;
	while(str[i++]!='\0')
		size++;
	return size;
}
char* reverse(char str[], char rev[]){
	int i=0, j=0;
	for(i=stringLength(str)-1; i>=0; i--)
		rev[j++] = str[i];
	/* ^starts from the end of the string, assigns each character to result array from start*/

	rev[j] = '\0';
	return rev;
}
char* tostr(int num, char str[]){
	int tempNum, i=0, j;
	tempNum = num;
	while(num){
		i++;
		num/=10;
	}
	/* ^finds number of digits in given number*/

	for(j=i-1; j>=0; j--){
		str[j] = tempNum%10+'0';
		tempNum /= 10;
	}
	/* ^starting from the rightmost digit in given number and removes digits one by one while assigning them to string in reverse order*/

	str[i] = '\0';
	return str;
}
char* combine(char str1[], char str2[], char str3[], char res[]){
	int maxLength, i, j=0;
	char tempStr1[255], tempStr2[255], tempStr3[255], tempRes[255];
	maxLength = maximum(stringLength(str1),maximum(stringLength(str2),stringLength(str3)));

	for(i=0; i<stringLength(str1); i++) tempStr1[i] = str1[i];
	tempStr1[i] = '\0';
	for(i=0; i<stringLength(str2); i++) tempStr2[i] = str2[i];
	tempStr2[i] = '\0';
	for(i=0; i<stringLength(str3); i++) tempStr3[i] = str3[i];
	tempStr3[i] = '\0';
	/* ^assigning given strings to temporary ones because we don't want to modify them*/

	for(i=stringLength(tempStr1); i<maxLength; i++) tempStr1[i] = '.';
	tempStr1[i] = '\0';
	for(i=stringLength(tempStr2); i<maxLength; i++) tempStr2[i] = '.';
	tempStr2[i] = '\0';
	for(i=stringLength(tempStr3); i<maxLength; i++) tempStr3[i] = '.';
	tempStr3[i] = '\0';
	/* ^makes all of the strings same size with putting '.' symbol at the end up until it reaches maximum length of given 3 strings*/

	for(i=0; i<maxLength*3; i+=3){
		tempRes[i] = tempStr1[j];
		tempRes[i+1] = tempStr2[j];
		tempRes[i+2] = tempStr3[j];
		j++;
	}
	/* ^combines 3 new strings in a temporary one because we have our strings with '.' symbols in them*/

	j=0;
	for(i=0; i<maxLength*3; i++)
		if(tempRes[i] != '.')
			res[j++]=tempRes[i];
	res[j] = '\0';
	/* ^removes '.' symbols and finalizes the result*/

	return res;
}
char* convert(char str[], int num, char res[]){
	char numToStr[255], reverseStr[255];
	combine(str, tostr(num,numToStr), reverse(str,reverseStr), res);
	return res;
}
