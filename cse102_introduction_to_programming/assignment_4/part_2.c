#include <stdio.h>

typedef enum _paddingType { ZERO, HALF, SAME} PaddingType;

void addPadding(double inputArr[], int inputArraySize, double outputArr[], int *outputArraySize, int paddingWidth, void paddingMethod(double[], int, int));
void zeroPadding(double outputArr[], int outputArraySize, int paddingWidth);
void samePadding(double outputArr[], int outputArraySize, int paddingWidth);
void halfPadding(double outputArr[], int outputArraySize, int paddingWidth);
int convolution(double inputArr[], int inputArraySize, double kernelArr[], int kernelArraySize, double outputArr[], int *outputArraySize, int stride, PaddingType padding);

int main(){
    int i;
    int test = 0;
    int kernelSize;
    double outputArr[255];
    int outputArrSize = 0;
    /*double inputArr[] = {1,3,5,7,9,11,13};
    double kernelArr[] = {2,4,6};
    kernelSize = 3;
    convolution(inputArr, 7, kernelArr, kernelSize, outputArr, &outputArrSize, 2, ZERO);*/
    double inputArr[] = {3, 5, 7, 9, 11, 13, 15};
    double kernelArr[] = {-1, 2, 0.5, 4, 1.7};
    kernelSize = 5;
    convolution(inputArr, 7, kernelArr, kernelSize, outputArr, &outputArrSize, 1, SAME);
    for(i=0; i<outputArrSize; i++){
        printf("%f ", outputArr[i]);
    }
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

int convolution(double inputArr[], int inputArraySize, double kernelArr[], int kernelArraySize, double outputArr[], int *outputArraySize, int stride, PaddingType padding){
    int newInputSize, paddingWidth;
    int i, j;
    double paddedArray[255], tempArr[255];
    if(kernelArraySize > inputArraySize)    return -1; /*error condition*/
    newInputSize = inputArraySize + kernelArraySize - 1; /*calculating the size of new array that can be convoluted and result the same size as input array*/
    paddingWidth = (newInputSize - inputArraySize) / 2; /*calculating padding width required for the array we need to be convolute*/
    switch(padding){    /*setting up the padding for new array that we are going to convolute later on*/
        case ZERO: addPadding(inputArr, inputArraySize, paddedArray, &newInputSize, paddingWidth, zeroPadding);break;
        case SAME: addPadding(inputArr, inputArraySize, paddedArray, &newInputSize, paddingWidth, samePadding);break;
        case HALF: addPadding(inputArr, inputArraySize, paddedArray, &newInputSize, paddingWidth, halfPadding);break;
        default: return -1;
    }
    newInputSize = 2 * paddingWidth + inputArraySize;
    *outputArraySize = inputArraySize;
    for(i=0; i<*outputArraySize; i++)   tempArr[i] = 0; /*resetting all elements of output array*/
    /*convolution*/
    for(i=0; i<*outputArraySize; i+=stride)
        for(j=0; j<kernelArraySize; j++)
            tempArr[i] += paddedArray[i+j]*kernelArr[j];
    j=0;
    for(i=0; i<*outputArraySize; i++)
        if(tempArr[i]>0)
            outputArr[j++] = tempArr[i];
    *outputArraySize = j;
    return 0;
}
