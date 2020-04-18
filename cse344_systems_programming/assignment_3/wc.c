#include <sys/types.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <sys/file.h>
#include <unistd.h>
#include <dirent.h>
#include <fcntl.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <limits.h>
#include <errno.h>
#include <ctype.h>

#define _XOPEN_SOURCE       1024

int main(int argc, char** argv) {
	int stdin_mode = 0;
    char file_name[255];
	if(argc > 2) {
		fprintf(stderr, "Usage: wc FILENAME\n");
		exit(EXIT_SUCCESS);
	} else if(argc == 1) {
		stdin_mode = 1;
	} else {
		strcpy(file_name, argv[1]);
	}

    FILE* input_file = fopen(file_name, "rt");

    if(stdin_mode == 0) {
    	if(input_file == NULL) {
    		fprintf(stderr, "Can not open file: %s\n", file_name);
    		exit(EXIT_SUCCESS);
    	}
    }

    char* line = NULL;
    size_t len = 0;
    ssize_t read;
    int line_count = 0;

    if(stdin_mode == 0) {
    	while ((read = getline(&line, &len, input_file)) != -1) {
        	line_count++;
    	}
    } else {
    	while ((read = getline(&line, &len, stdin)) != -1) {
        	line_count++;
    	}
    }

    fprintf(stdout, "%d\n", line_count);

    if(line) free(line);
    
    if(!stdin_mode) fclose(input_file);

    return 0;
}