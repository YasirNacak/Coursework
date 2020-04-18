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
	if(argc > 1) {
		fprintf(stderr, "Usage: pwd\n");
		exit(EXIT_SUCCESS);
	}
    char cwd[PATH_MAX];
    getcwd(cwd, sizeof(cwd));
    fprintf(stdout, "%s\n", cwd);
    return 0;
}