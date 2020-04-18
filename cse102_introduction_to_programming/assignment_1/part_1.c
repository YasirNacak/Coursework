#include <stdio.h>

int theAge(int day, int month, int year, int today, int this_month, int this_year);

int daysLeft(int day, int month, int today, int this_month);

int theAge(int day, int month, int year, int today, int this_month, int this_year){
    int year_to_day, month_to_days, this_year_to_days, this_month_to_days, day_difference, day_difference_to_years;
    /*converting given months to days*/
    year_to_day = year * 365;
    this_year_to_days = this_year * 365;

    /*converting given years to days*/
    month_to_days = month * 30;
    this_month_to_days = this_month * 30;

    /*calculating exact days of year for given months and days*/
    day += year_to_day + month_to_days;
    today += this_year_to_days + this_month_to_days;

    /*finding difference between two given dates*/
    day_difference = today - day;

    /*converting days difference to years*/
    day_difference_to_years = day_difference / 365;

    /*printing and returning the result*/
    printf("%d", day_difference_to_years);
    return day_difference_to_years;
}

int daysLeft(int day, int month, int today, int this_month){
    int month_to_days, this_month_to_days, day_difference;
    /*converting given months to days*/
    month_to_days = month * 30;
    this_month_to_days = this_month * 30;

    /*calculating exact days of year for given months and days*/
    day += month_to_days;
    today += this_month_to_days;

    /*finding difference between two given dates*/
    day_difference = day - today;

    /*removing the possibility of a negative outcome*/
    day_difference += 365;
    day_difference %= 365;

    /*printing and returning the result*/
    printf("%d", day_difference);
    return day_difference;
}
