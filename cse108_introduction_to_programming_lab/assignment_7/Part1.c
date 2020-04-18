#include <stdio.h>

int stringLength(char str[]);
char toLowerCase(char c);
int ifind(char haystack[], char needle[]);
int count(char haystack[], char needle[]);

int main(){
	/*
	char haystack[255] = "deHedeheDeHEDE", needle[255] = "hede";
	printf("Position: %d, Count: %d. Needle: %s Haystack: %s\n", ifind(haystack,needle), count(haystack,needle), haystack, needle);
	*/char haystack[] = "BBBBaABBbbAABbbBhkhlbbbb";
    char needle[] = "BbbB";
	printf("Position: %d, Count: %d. Needle: %s Haystack: %s\n", ifind(haystack,needle), count(haystack,needle), haystack, needle);
	return 0;
}

int stringLength(char str[]){
	int i=0, size=0;
	while(str[i++]!='\0')
		size++;
	return size;
}
char toLowerCase(char c){
	char result = c;
	if(c>='A' && c<='Z')
		result = c+32;
	return result;
}
int ifind(char haystack[], char needle[]){
	int result=-1, i, j;
	int compCount=0;
	for(i=0; i<stringLength(haystack)-stringLength(needle)+1&&result==-1; i++){
		compCount=0;
		for(j=0; j<stringLength(needle); j++){
			if(toLowerCase(needle[j])==toLowerCase(haystack[i+j])){
				compCount++;
			}
		}
		if(compCount == stringLength(needle)){
			result = i;
		}
	}
	return result;
}
int count(char haystack[], char needle[]){
	int result=0, i;

	for(i=0; i<stringLength(haystack)-stringLength(needle)+1; i++){
		if(ifind(haystack+i,needle)!=-1){
			i+=stringLength(needle)-1;
			result++;
		}
	}
	return result;
}

