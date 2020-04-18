#include <stdio.h>

double maxSumOfIncSeq();

double maxSumOfIncSeq(){
	double curr_seq, sum_max, max_seq, sum_seq, n, n_old;
	sum_seq = 0;
	curr_seq = 0;
	n_old = 0;
	max_seq = 0;
	sum_max = 0;
	do{
		scanf("%lf", &n);
		if(n > n_old){
			sum_seq += n;
			curr_seq += 1;
		} else {
			sum_seq = 0;
			curr_seq = 0;
		}
		if(curr_seq > max_seq){
			max_seq = curr_seq;
			sum_max = sum_seq;
		}
		n_old = n;
	}while(n >= 0);
	if(max_seq == 1){
		sum_max = 0;
	}
	return sum_max;
}
