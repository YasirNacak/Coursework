/*  
	Author:         Yasir Nacak
  	Student ID:     161044042
  	Last Modified:  23/02/2019 - 11:26PM (MM:DD:YY - HH:MM)
*/

#define _XOPEN_SOURCE       1024
#define BYTE_PER_KILOBYTE 	1024
#define OUTPUT_FILE_NAME	"161044042sizes.txt"

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

/*
	Structure to hold information about output of single 
	directory info with pid of the process checking it
*/
typedef struct {
    int pid;
    int size;
    char path_name[PATH_MAX];
} pid_size_path;

/*
	Structure to hold a dynamic list of unique integers
*/
typedef struct {
    int *array;
    size_t used;
    size_t size;
} unique_int_array;

/*
	Structure to hold a dynamic list of pid_size_paths
*/
typedef struct {
    pid_size_path *array;
    size_t used;
    size_t size;
} psp_array;

/*
    Simple boolean type.
*/
typedef enum {false, true} bool;

/*
    Global flag to check if [-z] is passed.
*/
bool isRecursiveSizeMode = false;

/*
	STUDENT_NOsizes.txt file descriptor
*/
int fd;

/*
    Main traversal function. Creates a process each time a subdirectory is reached.

    @param path: starting path to check, goes through all subdirectories of this.
    @param pathfun: function to apply to each regular file found in the given directory.
*/
pid_size_path postOrderApply(char* path, int pathfun(char* path1));

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
bool is_usable_directory_entry(char* direntry);

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
	Initial allocation function for dynamic unique integer array
*/
void init_unique_int_array(unique_int_array* a, size_t initialSize);

/*
	Insetion function for dynamic unique integer array 
*/
void insert_to_unique_int_array(unique_int_array* a, int element);

/*
	Function to free the memory allocated by unique integer array
*/
void free_unique_int_array(unique_int_array* a);

/*
	Initial allocation function for dynamic process_id, size, path array
*/
void init_psp_array(psp_array* a, size_t initialSize);

/*
	Insetion function for dynamic process_id, size, path array 
*/
void insert_to_psp_array(psp_array* a, pid_size_path element);

/*
	Function to free the memory allocated by process_id, size, path array
*/
void free_psp_array(psp_array* a);

/*
	Function that parses output text file and finds the total number of unique pids
*/
int get_total_child_count();

/*
	BONUS PART: gathers the non [-z] output file, parses the pid, size and path info
	finds the top directory of each parsed entry and adds the child directory's size
	to its parents directory
*/
void parse_output_and_find_flagged_sizes();

/*
	Gathers the output text file, adds header and total process count lines
	to it and prints it on the console
*/
void finalize_output();

/*
	Function to check if the string contains only digits
*/
bool is_number(char* str);

/*
    Main method that checks for the parameters passed to it and decides the program flow accordingly
*/
int main(int argc, char** argv) {
    int dirIndex = 1;
    int flagZSet;

    int child_pid, child_status;
    pid_size_path psp;

    char pspInfo[128];

    if (argc < 2 || argc > 3) {
        printUsageAndExit();
    }

    flagZSet =  strcmp(argv[1], "-z");

    if(argc == 3 && flagZSet) {
        printUsageAndExit();
    } else if(argc == 3 && flagZSet == 0) {
        isRecursiveSizeMode = true;
        dirIndex = 2;
    }

    fd = open(OUTPUT_FILE_NAME, O_RDWR|O_CREAT|O_TRUNC, 0666);

    if(fd == -1)
    {
        printf("\n open() failed with error [%s]\n",strerror(errno));
        return -1;
    }

    switch(child_pid = fork()) {
        case 0: /* Child */
            psp = postOrderApply(argv[dirIndex], sizepathfun);
            flock(fd, LOCK_EX);
            sprintf(pspInfo, "%d\t%d\t%s\n", psp.pid, psp.size, psp.path_name);
            write(fd, pspInfo, strlen(pspInfo));
            flock(fd, LOCK_UN);
            close(fd);
            break;

        case -1:
            fprintf(stderr, "Child Process Creation Failed.\n");
            break;

        default:
            waitpid(child_pid, &child_status, 0);
            close(fd);
            if(isRecursiveSizeMode) {
		    	parse_output_and_find_flagged_sizes();
		    }
		    finalize_output();
            break;
    }

    exit(EXIT_SUCCESS);
}

void finalize_output() {
    int total_child_count = 0;
    FILE* result_file = fopen(OUTPUT_FILE_NAME, "rt");
    char* line = NULL;
    size_t len = 0;
    ssize_t read;

    fprintf(stdout, "PID\tSIZE\tPATH\n");

    while ((read = getline(&line, &len, result_file)) != -1) {
        fprintf(stdout, "%s", line);
    }

    if(line) {
        free(line);
    }

	total_child_count = get_total_child_count();
    fprintf(stdout, "%d child processes created. Main process is %d\n", total_child_count, getpid());

    fclose(result_file);
}

void parse_output_and_find_flagged_sizes() {
    FILE* result_file = fopen(OUTPUT_FILE_NAME, "rt");
    FILE* result_file_temp = fopen("immediate", "wt");
	char* line = NULL;
    size_t len = 0;
    ssize_t read;
    psp_array psp_arr;
    int i, j, k;
    char ch;

    init_psp_array(&psp_arr, 2);

    while ((read = getline(&line, &len, result_file)) != -1) {
        char first_col[PATH_MAX];
        char second_col[PATH_MAX];
        char third_col[PATH_MAX];
        sscanf(line, "%s\t%s\t%s", first_col, second_col, third_col);

        if(is_number(second_col)) {
        	pid_size_path psp_element;
        	psp_element.pid = atoi(first_col);
        	psp_element.size = atoi(second_col);
        	strcpy(psp_element.path_name, third_col);
        	insert_to_psp_array(&psp_arr, psp_element);
        }
    }

    for(i = 0; i < psp_arr.used; i++) {
    	char parent_path[PATH_MAX];
    	strcpy(parent_path, psp_arr.array[i].path_name);
    	for(j = strlen(parent_path) - 1; j >= 0; j--) {
    		if(parent_path[j] == '/')
    			break;
    	}

    	if(j != -1) {
    		parent_path[j] = '\0';
		    for(k = 0; k < psp_arr.used; k++) {
		    	if(strcmp(psp_arr.array[k].path_name, parent_path) == 0) {
		    		psp_arr.array[k].size += psp_arr.array[i].size;
		    	}
		    }
    	}
    }

    fclose(result_file);

    result_file = fopen(OUTPUT_FILE_NAME, "rt");

    while ((ch = fgetc(result_file)) != EOF)
		fputc(ch, result_file_temp);

    fclose(result_file);
    fclose(result_file_temp);

    result_file = fopen(OUTPUT_FILE_NAME, "wt");
    result_file_temp = fopen("immediate", "rt");

    i = 0;

    while ((read = getline(&line, &len, result_file_temp)) != -1) {
    	char first_col[PATH_MAX];
    	sscanf(line, "%s", first_col);
        if(strcmp(first_col, "Special") == 0) {
        	fprintf(result_file, "%s", line);
        } else {
        	fprintf(result_file, "%d\t%d\t%s\n", psp_arr.array[i].pid, psp_arr.array[i].size, psp_arr.array[i].path_name);
        	i++;
        }
    }

    if(line)
    	free(line);
    free_psp_array(&psp_arr);
    fclose(result_file);
    fclose(result_file_temp);
    remove("immediate");
}

int get_total_child_count() {
    FILE* result_file = fopen(OUTPUT_FILE_NAME, "rt");
    char* line = NULL;
    size_t len = 0;
    ssize_t read;
    unique_int_array unique_pids;
    int result;

    init_unique_int_array(&unique_pids, 2);

    while ((read = getline(&line, &len, result_file)) != -1) {
        char first_col[64];
        sscanf(line, "%s", first_col);
        if(is_number(first_col)) {
        	insert_to_unique_int_array(&unique_pids, atoi(first_col));
        }
    }

    if (line)
        free(line);

    result = unique_pids.used;
    free_unique_int_array(&unique_pids);

    fclose(result_file);

    return result;
}

bool is_number(char* str) {
	int i;
	for (i = 0; i < strlen(str); i++) {
		if(!isdigit(str[i]))
			return false;
	}
	return true;
}

pid_size_path postOrderApply(char* path, int pathfun(char* path1)) {
    pid_size_path result;
    struct dirent* directory_entry;
    DIR* directory = opendir(path);
    struct stat directoryStat;
    int dirSize = 0;
    char fullPath[PATH_MAX];
    char special_file_info[PATH_MAX];

    if(directory == NULL) {
        fprintf(stderr, "Can not access directory: %s.\n", path);
        exit(EXIT_FAILURE);
    }

    while((directory_entry = readdir(directory)) != NULL) {
        if(is_usable_directory_entry(directory_entry->d_name)) {
            if (fstatat(dirfd(directory), directory_entry->d_name, &directoryStat, AT_SYMLINK_NOFOLLOW) >= 0) {
            	strcatPath(fullPath, path, directory_entry->d_name);

	            if (S_ISDIR(directoryStat.st_mode)) {
                    int child_pid;
                    int child_status;
                    switch (child_pid = fork())
                    {
                        case 0: /* Child */
                            closedir(directory);
                            strcpy(path, fullPath);
                            directory = opendir(fullPath);
                            dirSize = 0;
                            break;
                        case -1:
                            fprintf(stderr, "Child Process Creation Failed.\n");
                        default:
                            waitpid(child_pid, &child_status, 0);
                            break;
                    }
	            } else {
	                if( S_ISCHR(directoryStat.st_mode) 	||
	                    S_ISBLK(directoryStat.st_mode) 	||
	                    S_ISFIFO(directoryStat.st_mode) ||
	                    S_ISLNK(directoryStat.st_mode) 	||
	                    S_ISSOCK(directoryStat.st_mode)) {
	                		sprintf(special_file_info, "Special file %s\n", directory_entry->d_name);
	                		write(fd, special_file_info, strlen(special_file_info));
	                    }
	                else {
	                	int sizePath = pathfun(fullPath);
	                	if(sizePath >= 0)
	                    	dirSize += sizePath;
	                }
	            }
            } else {
            	fprintf(stderr, "Can not collect information from: %s\n", directory_entry->d_name);
            }
        }
    }

    result.pid = getpid();
    result.size = dirSize;
    strcpy(result.path_name, path);
    closedir(directory);
    return result;
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
	fprintf(stderr, "Usage: buNeDuFork [-z] RootDirectory\n");
    exit(EXIT_SUCCESS);
}

bool is_usable_directory_entry(char* d_name) {
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

void init_unique_int_array(unique_int_array* a, size_t initialSize) {
    a->array = (int*)malloc(initialSize * sizeof(int));
    a->used = 0;
    a->size = initialSize;
}

void insert_to_unique_int_array(unique_int_array* a, int element) {
	int i;
    for(i = 0; i < a->used; i++) {
        if(a->array[i] == element) {
            return;
        }
    }

    if (a->used == a->size) {
        a->size *= 2;
        a->array = (int*)realloc(a->array, a->size * sizeof(int));
    }

    a->array[a->used++] = element;
}

void free_unique_int_array(unique_int_array* a) {
    free(a->array);
    a->array = NULL;
    a->used = a->size = 0;
}

void init_psp_array(psp_array* a, size_t initialSize) {
    a->array = (pid_size_path*)malloc(initialSize * sizeof(pid_size_path));
    a->used = 0;
    a->size = initialSize;
}

void insert_to_psp_array(psp_array* a, pid_size_path element) {
    if (a->used == a->size) {
        a->size *= 2;
        a->array = (pid_size_path*)realloc(a->array, a->size * sizeof(pid_size_path));
    }

    a->array[a->used++] = element;
}

void free_psp_array(psp_array* a) {
    free(a->array);
    a->array = NULL;
    a->used = a->size = 0;
}