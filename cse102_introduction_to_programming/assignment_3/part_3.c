#include <stdio.h>
int charge(int cardType, int* monthlyUse, double* balance){
    int result, old_balance;
    result = 0;
    old_balance = *balance;
    if(*monthlyUse > 0){
        *monthlyUse -= 1;
    } else {
        switch(cardType){
            case 1: *balance -= 2.3;
                    break;
            case 2: *balance -= 1.15;
                    break;
            case 3: *balance -= 1.65;
                    break;
            default: result = -2;
        }
    }
    if(*balance < 0){
        *balance = old_balance;
        result = -1;
    }
    return result;
}

int main(){
    int monthlyUse = 120;
    double balance = 1.3;
    if(charge(1,&monthlyUse, &balance) == 0)
        printf("Remaining monthly use: %d - Remaining Balance: %.2f\n",monthlyUse,balance);
    else
        printf("Insufficient balance.");
    return 0;
}
