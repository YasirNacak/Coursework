#include <stdio.h>

double mean();

double mean(){
	int n;
	double numbers, sum, avg;
	sum = 0;
	numbers = 0;
	avg = 0;

	do{
		scanf("%d", &n);
		sum += n;
		numbers += 1;
	}while(n > 0);

	sum-= n;
	numbers -= 1;
	avg = sum / numbers;

	if(numbers == 0){
		avg = 0;
	}
	return avg;
}
