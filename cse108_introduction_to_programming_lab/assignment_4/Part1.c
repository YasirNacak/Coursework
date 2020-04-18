#include <stdio.h>

typedef enum{
	MIN, MAX, SUM
} operation;

int arrOp(int *arr, int size, operation op);

int main2(){
	/*int array[] = {1,2,3,4,5,6,7,-99,200}, size = 9;
	printf("%d\n",arr0p(array,size,MAX));
	printf("%d\n",arr0p(array,size,MIN));
	printf("%d\n",arr0p(array,size,SUM));*/
	return 0;
}

int arrOp(int *arr, int size, operation op){
	int result, sum, minx, maxx, i;
	minx = *arr;
	maxx = *arr;
	if(size < 1){
		result = -3456;
	} else if(op > 2 || op < 0){
		result = -9876;
	} else {

		if(op == 0){
			for(i=1; i<size; i++)
				if(*(arr+i) < minx)
					minx = *(arr+i);
			result = minx;
		}

		else if (op == 1){
			for(i=1; i<size; i++)
				if(*(arr+i) > maxx)
					maxx = *(arr+i);
			result = maxx;
		}

		else if (op == 2){
			for(i=0; i<size; i++)
				sum+=*(arr+i);
			result = sum;
		}

	}
	return result;
}

