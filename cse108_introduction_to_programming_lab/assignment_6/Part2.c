#include <stdio.h>
#include <string.h>

int main(){
    char romanstr[100];
    roman(1337,romanstr);
    printf("LEN: %d\n", strlen(romanstr));
    printf("%s",romanstr);
    return 0;
}

char* append(char str1[], char str2[]){
	int i=0, j=0, len2;
	while(str1[i] != '\0')
		i++;
	while(str2[j] != '\0')
		j++;
	len2=j;
	for(j=0; j<len2; j++)
		str1[i++] = str2[j];
	str1[i] = '\0';
	return str1;
}
int roman(int num, char romanstr[]){
	int romanInts[13] = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
	int romanCoverted[13] = {0,0,0,0,0,0,0,0,0,0,0,0,0};
	char romanShort[13][3] = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
	int i, j;
	romanstr[0] = '\0';
	if(num <= 0)	return 0;
	for(i=0;i<12;i++){
		romanCoverted[i] = num / romanInts[i];
		num %= romanInts[i];
	}
	romanCoverted[12] = num;
	for(i=0;i<13;i++)
		for(j=0;j<romanCoverted[i];j++)
			append(romanstr, romanShort[i]);
	return 1;
}
