#include <sys/types.h>
#include <sys/stat.h>

#include <unistd.h>
#include <dirent.h>
#include <fcntl.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#define PATH_MAX			4096
#define BYTE_PER_KILOBYTE 	1024

typedef enum {false, true} bool;

bool isRecursiveSizeMode = false;

int postOrderApply(char* path, int pathfun(char* path1));

int sizepathfun(char* path);

void printUsageAndExit();

bool isUsableDirectoryEntry(char* direntry);

void strcatPath(char* src, char* path, char* addition);

int main(int argc, char** argv) {
    char directory_name[255];
    if(argc == 1) {
        scanf("%s", directory_name);
    } else if(argc == 2) {
        if(strcmp(argv[1], "-z") == 0) {
            isRecursiveSizeMode = true;
            scanf("%s", directory_name);
        } else {
            strcpy(directory_name, argv[1]);
        }
    } else if (argc > 3) {
        printUsageAndExit();
    } else if(argc == 3 && strcmp(argv[1], "-z")) {
        printUsageAndExit();
    } else if(argc == 3 && strcmp(argv[1], "-z") == 0) {
        isRecursiveSizeMode = true;
        strcpy(directory_name, argv[2]);
    }

    postOrderApply(directory_name, sizepathfun);
    return 0;
}

int postOrderApply(char* path, int pathfun(char* path1)) {
    struct dirent* directoryEntry;
    DIR* directory = opendir(path);
    struct stat directoryStat;
    int dirSize = 0;
    char fullPath[PATH_MAX];

    if(directory == NULL) {
        fprintf(stderr, "Can not access directory: %s.\n", path);
        return -1;
    }

    while((directoryEntry = readdir(directory)) != NULL) {
        if(isUsableDirectoryEntry(directoryEntry->d_name)) {
            if (fstatat(dirfd(directory), directoryEntry->d_name, &directoryStat, AT_SYMLINK_NOFOLLOW) >= 0) {
            	strcatPath(fullPath, path, directoryEntry->d_name);

	            if (S_ISDIR(directoryStat.st_mode)) {
	            	if(isRecursiveSizeMode)
	                	dirSize += postOrderApply(fullPath, pathfun);
	                else
	                	postOrderApply(fullPath, pathfun);
	            } else {
	                if( S_ISCHR(directoryStat.st_mode) 	||
	                    S_ISBLK(directoryStat.st_mode) 	||
	                    S_ISFIFO(directoryStat.st_mode) ||
	                    S_ISLNK(directoryStat.st_mode) 	||
	                    S_ISSOCK(directoryStat.st_mode)) {
	                        fprintf(stdout, "Special file %s\n", directoryEntry->d_name);
	                    }
	                else {
	                	int sizePath = pathfun(fullPath);
	                	if(sizePath >= 0)
	                    	dirSize += sizePath;
	                }
	            }
            } else {
            	fprintf(stderr, "Can not collect information from: %s\n", directoryEntry->d_name);
            }
        }
    }

    fprintf(stdout, "%d\t%s.\n", dirSize, path);
    closedir(directory);
    return dirSize;
}

int sizepathfun(char* path) {
    int size;
    struct stat fileStat;
    lstat(path, &fileStat);
    size = fileStat.st_size / BYTE_PER_KILOBYTE;
    if(size >= 0)
    	return size;
    return -1;
}

void printUsageAndExit() {
	fprintf(stderr, "Usage: buNeDu [-z] RootDirectory\n");
    exit(EXIT_SUCCESS);
}

bool isUsableDirectoryEntry(char* d_name) {
	if(strcmp(d_name, ".") == 0 || strcmp(d_name, "..") == 0)
		return false;
    return true;
}

void strcatPath(char* src, char* path, char* addition) {
	strcpy(src, "");
    strcpy(src, path);
    strcat(src, "/");
    strcat(src, addition);
}