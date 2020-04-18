#include <stdio.h>
int dispenseChange(double paid, double due, int *tl1, int *krs50, int *krs25, int *krs10, int *krs5, int *krs1){
    int result;
    double dispense;
    *tl1=0, *krs50=0, *krs25=0, *krs10=0, *krs5=0, *krs1=0;
    if(paid < due || due < 0.01f){
        result = 0;
    } else {
        result = 1;
        dispense = paid - due;
        while(dispense >= 1){
            *tl1 += 1;
            dispense -= 1;
        }
        while(dispense >= 0.5f){
            *krs50 += 1;
            dispense -= 0.5;
        }
        while(dispense >= 0.25f){
            *krs25 += 1;
            dispense -= 0.25;
        }
        while(dispense >= 0.1f){
            *krs10 += 1;
            dispense -= 0.1;
        }
        while(dispense >= 0.05f){
            *krs5 += 1;
            dispense -= 0.05;
        }
        while(dispense >= 0.01f){
            *krs1 += 1;
            dispense -= 0.01;
        }
    }
    return result;
}

int main(){
    double paid, due;
    int tl1=0, krs50=0, krs25=0, krs10=0, krs5=0, krs1=0;
    paid =  5.00;
    due = 4.91;
    dispenseChange(paid, due, &tl1, &krs50, &krs25, &krs10, &krs5, &krs1);
    printf("tl1:%d\nkrs50:%d\nkrs25:%d\nkrs10:%d\nkrs5:%d\nkrs1:%d\n", tl1, krs50, krs25, krs10, krs5, krs1);
    return 0;
}
