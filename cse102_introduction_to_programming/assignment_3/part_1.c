#include <stdio.h>
int isPrime(int num){
    int result, counter;
    result = 1;
    counter = 2;
    if(num < 2){
        result = 0;
    } else {
        while(counter < num){
            if(num%counter == 0){
                result = 0;
                break;
            }
            counter++;
        }
    }
    return result;
}
int goldbach(int num, int *p1, int *p2){
    int result;
    result = 1;
    if(num%2 == 1 || num < 3){
        result = 0;
    }
    else{
        *p2 = 2;
        *p1 = num;
        while(*p2 < num){
            (*p1) = num - (*p2);
            if(isPrime(*p2) == 1 && isPrime(*p1) == 1){
                break;
            }
            (*p2)++;
        }
    }
    return result;
}

int main(){
    int num = -825, p1, p2;
    if(goldbach(num,&p1,&p2))
        printf("%d = %d + %d",num,p1,p2);
    else
            printf("You should provide even number.");
    return 0;
}
