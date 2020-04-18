#include <stdio.h>
#define TRUE 1
#define FALSE 0
void drawRectangle(int width, int height, int startingPoint, int printLastLine);

void drawDoubleCircle(int radius, int startingPoint, int whellDistance);

void drawCar();

void drawRectangle(int width, int height, int startingPoint, int printLastLine){
    int i,j;
    for(i=0; i<height; i++){
        for(j=0; j<startingPoint; j++){
            printf(" ");
        }
        if(i==0 || (i==height-1 && printLastLine == TRUE)){
            for(j=0; j<width; j++){
                printf("*");
            }
        } else {
            for(j=0; j<width; j++){
                if(j==0 || j==width-1){
                    printf("*");
                }
                else{
                    printf(" ");
                }
            }
        }
        printf("\n");
    }
}

void drawDoubleCircle(int radius, int startingPoint, int whellDistance){
    int i,j,starCount;
    starCount = 1;
    radius = radius * 2 + 1;
    while(starCount < radius+1){

        if(starCount != 3 && radius-2 != starCount){
            for(i=0; i<startingPoint; i++){
                printf(" ");
            }
            for(i=0; i<(radius-starCount); i++){
                printf(" ");
            }
            for(i=0; i<starCount; i++){
                if(i!=starCount-1){

                    printf("* ");
                } else {
                    printf("*");
                }
            }
            for(i=0; i<(2*(radius-starCount)+whellDistance); i++){
                printf(" ");
            }
            for(i=0; i<starCount; i++){
                if(i!=starCount-1){

                    printf("* ");
                } else {
                    printf("*");
                }
            }
            printf("\n");
        } else if(starCount != 3) {
            for(j=0; j<2; j++){
                for(i=0; i<startingPoint; i++){
                    printf(" ");
                }
                for(i=0; i<(radius-starCount); i++){
                    printf(" ");
                }
                for(i=0; i<starCount; i++){
                    if(i!=starCount-1){
                        printf("* ");
                    } else {
                        printf("*");
                    }
                }
                for(i=0; i<(2*(radius-starCount)+whellDistance); i++){
                    printf(" ");
                }
                for(i=0; i<starCount; i++){
                    if(i!=starCount-1){

                        printf("* ");
                    } else {
                        printf("*");
                    }
                }
                printf("\n");
            }
        }
        starCount+=2;
    }
    starCount -= 4;
    while(starCount > 0){
        if(starCount != 3 && radius-2 != starCount){
            for(i=0; i<startingPoint; i++){
                printf(" ");
            }
            for(i=0; i<(radius-starCount); i++){
                printf(" ");
            }
            for(i=0; i<starCount; i++){
                if(i!=starCount-1){
                    printf("* ");
                } else {
                    printf("*");
                }
            }
            for(i=0; i<(2*(radius-starCount)+whellDistance); i++){
                printf(" ");
            }
            for(i=0; i<starCount; i++){
                if(i!=starCount-1){

                    printf("* ");
                } else {
                    printf("*");
                }
            }
            printf("\n");
        } else if(starCount != 3) {
            for(j=0; j<2; j++){
                for(i=0; i<startingPoint; i++){
                    printf(" ");
                }
                for(i=0; i<(radius-starCount); i++){
                    printf(" ");
                }
                for(i=0; i<starCount; i++){
                    if(i!=starCount-1){
                        printf("* ");
                    } else {
                        printf("*");
                    }
                }
                for(i=0; i<(2*(radius-starCount)+whellDistance); i++){
                    printf(" ");
                }
                for(i=0; i<starCount; i++){
                    if(i!=starCount-1){

                        printf("* ");
                    } else {
                        printf("*");
                    }
                }
                printf("\n");
            }
        }
        starCount-=2;
    }
}

void drawCar(){
    drawRectangle(30,7,30,0);
    drawRectangle(70,10,11,1);
    drawDoubleCircle(6,15,24);
}
