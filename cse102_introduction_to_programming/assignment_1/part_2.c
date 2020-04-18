#include <stdio.h>
/*defining the constant PI*/
#define PI 3.14

float diameter(float circumference);

float ageOfTree(float diameter, float growth_factor);

float diameter(float circumference){
    float dia;
    /*calculating the diameter*/
    dia = circumference / PI;

    /*returning the result*/
    return dia;
}

float ageOfTree(float diameter, float growth_factor){
    float age;
    /*calculating the age*/
    age = diameter * growth_factor;

    /*returning the result*/
    return age;
}
