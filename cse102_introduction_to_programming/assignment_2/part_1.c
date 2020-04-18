#include <stdio.h>
void drawFunction(int xAxis, int yAxis, int functionNumber){
    int i = 0;
    while(--yAxis){
        printf("|");
        for(i=1; i<=xAxis; i++)
            fTheFunction(i, functionNumber) == yAxis ? printf("*") : printf(" ");
        printf("\n");
    }
    printf("|");
    while(xAxis--) printf("-");
}
