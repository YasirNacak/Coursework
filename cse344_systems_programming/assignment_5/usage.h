/*  
	Author:         Yasir Nacak
  	Student ID:     161044042
  	Last Modified:  ??/??/???? - ??:??_M (MM:DD:YY - HH:MM)
*/

#ifndef USAGE_H
#define USAGE_H
#include <stdlib.h>

void print_and_exit() {
	fprintf(stdout, USAGE, PNAME);
	exit(EXIT_FAILURE);
}

void check_usage(int argc, char** argv, int argc_min, int argc_max) {
	if(argc < argc_min || argc > argc_max) {
		print_and_exit();
	} else if(strcmp(argv[0], PNAME) != 0) {
		print_and_exit();
	}
}

#endif