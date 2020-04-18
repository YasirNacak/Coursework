int power(int t, int u){
	int result = 1, i;
	for(i=0; i<u; i++)
		result*=t;
	return result;
}

int bin2dec(int bin[]){
	int result=0, i, j=0;
	for(i=LEN-1; i>=0; i--)
		result += power(2,j++) * bin[i];
	return result;
}

int dec2bin(int dec, int bin[]){
	int result=0, i=0, j=0;
	for(j=0; j<LEN; j++)
		bin[j]=0;
	if(dec > 255 || dec < 0)
		result = -1;
	while (dec){
		bin[LEN-i-1] = dec % 2;
		dec /= 2;
		i++;
	}
	return result;
}
