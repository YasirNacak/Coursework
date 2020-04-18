//  Author:         Yasir Nacak
//  Student ID:     161044042
//  Last Modified:  03/09/2019 - 12:53PM (MM:DD:YY - HH:MM)

#define _XOPEN_SOURCE       1024
#define BYTE_PER_KILOBYTE 	1024

#include <sys/types.h>
#include <sys/stat.h>

#include <unistd.h>
#include <dirent.h>
#include <fcntl.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <limits.h>

/*
    Simple boolean type.
*/
typedef enum {false, true} bool;

/*
    Global flag to check if [-z] is passed.
*/
bool isRecursiveSizeMode = false;

/*
    Main traversal function.

    @param path: starting point of recursion, goes through all subdirectories of this.
    @param pathfun: function to apply to each regular file found in the given directory.
*/
int postOrderApply(char* path, int pathfun(char* path1));

/*
    Size calculation method used in postOrderApply.

    @param path: path to the file that needs it size to be calculated.
*/
int sizepathfun(char* path);

/*
    If the user runs the program wrong, this function prints out the correct 
    way to use the program and quits the process.
*/
void printUsageAndExit();

/*
    Given a directory entry path, this function checks if it is not the directory
    itself or the directory before it. This way, we can stop the program from infinitely 
    recursing back and forth between previous and current directories.

    @param direntry: path to a directory entry
*/
bool isUsableDirectoryEntry(char* direntry);

/*
    This utility method helps to create a string that contains 
    a predefined path and an additional file/directory. It simply adds
    "/" between two strings and makes this addition in a new string so
    the parameter values don't get lost.

    @param src: string that is going to get constructed eventually.
    @param path: starting path, expected value is a valid path string
    @param addition: the directory/file name that is to be concatenated to path with a forward slash before it
*/
void strcatPath(char* src, char* path, char* addition);

/*
    Main method that checks for the parameters passed to it and decides the program flow accordingly
*/
int main(int argc, char** argv) {
    int dirIndex = 1;
    int flagZSet =  strcmp(argv[1], "-z");

    if (argc < 2 || argc > 3) {
        printUsageAndExit();
    } else if(argc == 3 && flagZSet) {
        printUsageAndExit();
    } else if(argc == 3 && flagZSet == 0) {
        isRecursiveSizeMode = true;
        dirIndex = 2;
    }

    postOrderApply(argv[dirIndex], sizepathfun);
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
	            	if(isRecursiveSizeMode) {
                        int postOrderTraverseSize = postOrderApply(fullPath, pathfun);
                        if(postOrderTraverseSize >= 0) {
                            dirSize += postOrderTraverseSize;
                        }
                    }
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