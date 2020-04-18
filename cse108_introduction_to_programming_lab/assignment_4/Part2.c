#include <stdio.h>

void alphabeticalSort(char *arr, int size);

int main2(){
	int i;
	char arr[] = {'m','e','r','h','a','b','a','A','l','i'};
	alphabeticalSort(arr,10);
	for (i = 0; i < 10; ++i)
	{
		printf("%c", arr[i]);
	}
	printf("\n");
	return 0;
}

void alphabeticalSort(char *arr, int size){
	int i, j, flagX, flagY, upLowDiff, count;
	char tempswap;
	upLowDiff = 'a' - 'A';
	count = 0;
	for(i=0; i<size; i++){
		j=i;
		count = 0;
		do{
			if(count > 0){
				tempswap = *(arr+j-1);
				*(arr+j-1) = *(arr+j);
				*(arr+j) = tempswap;
				j--;
			}

			flagX = 0;
			flagY = 0;

			if(*(arr+j-1) <= 'Z'){
				flagX = 1;
			} else {
				flagX = 0;
			}

			if(*(arr+j) <= 'Z'){
				flagY = 1;
			} else {
				flagY = 0;
			}

			count++;

		} while(j>0 && (*(arr+j-1))+(upLowDiff*flagX) > (*(arr+j))+(upLowDiff*flagY));
	}
}
