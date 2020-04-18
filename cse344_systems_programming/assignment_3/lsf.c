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
		fprintf(stderr, "Usage: lsf\n");
		exit(EXIT_SUCCESS);
	}
	DIR *dir;
	struct dirent *ent;
    struct stat dstat;
	if((dir = opendir (".")) != NULL) {
		while((ent = readdir(dir)) != NULL) {
			if (fstatat(dirfd(dir), ent->d_name, &dstat, AT_SYMLINK_NOFOLLOW) >= 0) {
	            if (!S_ISDIR(dstat.st_mode)) {
	            	if( S_ISCHR(dstat.st_mode) 	||
	                    S_ISBLK(dstat.st_mode) 	||
	                    S_ISFIFO(dstat.st_mode) ||
	                    S_ISLNK(dstat.st_mode) 	||
	                    S_ISSOCK(dstat.st_mode)) {
	                        fprintf(stdout, "S\t");
                    } else {
						fprintf(stdout, "R\t");
                    }

                    if(dstat.st_mode & S_IRUSR) fprintf(stdout, "r");
                    else fprintf(stdout, "-");
                    if(dstat.st_mode & S_IWUSR) fprintf(stdout, "w");
                    else fprintf(stdout, "-");
                    if(dstat.st_mode & S_IXUSR) fprintf(stdout, "x");
                    else fprintf(stdout, "-");

                    if(dstat.st_mode & S_IRGRP) fprintf(stdout, "r");
                    else fprintf(stdout, "-");
                    if(dstat.st_mode & S_IWGRP) fprintf(stdout, "w");
                    else fprintf(stdout, "-");
                    if(dstat.st_mode & S_IXGRP) fprintf(stdout, "x");
                    else fprintf(stdout, "-");

                    if(dstat.st_mode & S_IROTH) fprintf(stdout, "r");
                    else fprintf(stdout, "-");
                    if(dstat.st_mode & S_IWOTH) fprintf(stdout, "w");
                    else fprintf(stdout, "-");
                    if(dstat.st_mode & S_IXOTH) fprintf(stdout, "x");
                    else fprintf(stdout, "-");

                    fprintf(stdout, "\t%li", dstat.st_size);

                    fprintf(stdout, "\t%s\n", ent->d_name);
	            }
            } else {
            	fprintf(stderr, "Can not collect information from: %s\n", ent->d_name);
            }
		}
		closedir(dir);
	} else {
		perror("");
		return EXIT_FAILURE;
	}
}