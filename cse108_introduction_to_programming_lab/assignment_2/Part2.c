#include <stdio.h>

double minimum();

double maximum();

double minimum(){
	double n, defmin;
	defmin = 100005;
	do{
		scanf("%lf", &n);
		if(n < defmin && n > 0){
			defmin = n;
		}
	}while(n > 0);
	if(defmin == 100005)
		defmin = 0;
	return defmin;
}

double maximum(){
	double n, defmax;
	defmax = 0;
	do{
		scanf("%lf", &n);
		if(n > defmax && n > 0){
			defmax = n;
		}
	}while(n > 0);
	return defmax;
}
