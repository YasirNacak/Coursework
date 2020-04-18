#include <stdio.h>

void addPadding(double inputArr[], int inputArraySize, double outputArr[], int *outputArraySize, int paddingWidth, void paddingMethod(double[], int, int));
void zeroPadding(double outputArr[], int outputArraySize, int paddingWidth);
void samePadding(double outputArr[], int outputArraySize, int paddingWidth);
void halfPadding(double outputArr[], int outputArraySize, int paddingWidth);


int main(){
    int i;
    double inputArr[] = {5, 6, 7, 8, 9};
    double outputArr[255];
    int outputArrSize = 0;
    addPadding(inputArr, 5 , outputArr, &outputArrSize, 4, samePadding);
    for(i=0;i<outputArrSize;i++)
        printf("%f ", outputArr[i]);
    return 0;
}


void addPadding(double inputArr[], int inputArraySize, double outputArr[], int *outputArraySize, int paddingWidth, void paddingMethod(double[], int, int)){
    int i, j = 0;
    *outputArraySize = inputArraySize + (2 * paddingWidth); /* calculating the size of output array*/
    for(i=0; i<*outputArraySize; i++)   outputArr[i] = 0;   /* initialization of the output array*/
    for(i=paddingWidth; i<inputArraySize+paddingWidth; i++) outputArr[i] = inputArr[j++];   /* filling the output array with elements from input array but with padding width*/
    paddingMethod(outputArr, *outputArraySize, paddingWidth);   /* calling the function for padding*/
}

void zeroPadding(double outputArr[], int outputArraySize, int paddingWidth){
    /* The array is already starts and ends with zeroes because of the initialization in addPadding.
       There's no need to fill the array up with zeroes */
}

void samePadding(double outputArr[], int outputArraySize, int paddingWidth){
    double startElem, finalElem;
    int i;
    startElem = outputArr[paddingWidth];    /* getting the value that needs to be the in the array from index 0 until padding width is reached*/
    finalElem = outputArr[outputArraySize-paddingWidth-1];  /*getting the value that needs to be in the array from index outputSize-paddingWidth until the end is reached*/
    for(i=0; i<paddingWidth; i++)   outputArr[i] = startElem;
    for(i=outputArraySize-paddingWidth; i<outputArraySize; i++)   outputArr[i] = finalElem;
}

void halfPadding(double outputArr[], int outputArraySize, int paddingWidth){
    double startElem, finalElem;
    int i;
    startElem = outputArr[paddingWidth];    /* getting double of the value that needs to be the in the array from index 0 until padding width is reached*/
    finalElem = outputArr[outputArraySize-paddingWidth-1];  /*getting double of the value that needs to be in the array from index outputSize-paddingWidth until the end is reached*/
    for(i=0; i<paddingWidth; i++)   outputArr[i] = (startElem/2);
    for(i=outputArraySize-paddingWidth; i<outputArraySize; i++)   outputArr[i] = (finalElem/2);
}
