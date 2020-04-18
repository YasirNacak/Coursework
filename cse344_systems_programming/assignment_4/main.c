/*  
	Author:         Yasir Nacak
  	Student ID:     161044042
  	Last Modified:  31/03/2019 - 10:13PM (MM:DD:YY - HH:MM)
*/

#define _XOPEN_SOURCE       1024
#define BYTE_PER_KILOBYTE 	1024
#define OUTPUT_FILE_PATH	"/tmp/161044042sizes"

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
	Gathers the output text file, adds header and total process count lines
	to it and prints it on the console
*/
void finalize_output();

/*
	Function to check if the string contains only digits
*/
bool is_number(char* str);

bool is_number(char* str) {
	int i;
	for (i = 0; i < strlen(str); i++) {
		if(!isdigit(str[i]))
			return false;
	}
	return true;
}

void finalize_output() {
    int total_child_count = 0;
    FILE* result_file = fopen("immediate", "rt");
    char* line = NULL;
    size_t len = 0;
    ssize_t read;

    fprintf(stdout, "PID\tSIZE\tPATH\n");

    while ((read = getline(&line, &len, result_file)) != -1) {
        if(!(strcmp(line, "\n") == 0))
            fprintf(stdout, "%s", line);
    }

    if (line)
        free(line);

    fclose(result_file);

    total_child_count = get_total_child_count();
    fprintf(stdout, "%d child processes created. Main process is %d\n", total_child_count, getpid());
}

int get_total_child_count() {
    FILE* result_file = fopen("immediate", "rt");
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

/*
    Main method that checks for the parameters passed to it and decides the program flow accordingly
*/
int main(int argc, char** argv) {
    int dirIndex = 1;
    int flagZSet;
    int fifo_fd;

    int child_pid;

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

    mkfifo(OUTPUT_FILE_PATH, 0666);

    switch(child_pid = fork()) {
        case 0: /* Child */
            postOrderApply(argv[dirIndex], sizepathfun);
            break;

        case -1:
            fprintf(stderr, "Child Process Creation Failed.\n");
            break;

        default:
            fifo_fd = open(OUTPUT_FILE_PATH, O_RDONLY);
            char buf[PATH_MAX];
            FILE* outfile = fopen("immediate", "wt");
            while(1) {
                memset(buf, 0, PATH_MAX);
                int rd = read(fifo_fd, buf, PATH_MAX);
                if(rd <= 0) break;
                fprintf(outfile, "%s\n", buf);
            }

            unlink(OUTPUT_FILE_PATH);
            close(fifo_fd);
            fclose(outfile);
            finalize_output();
            remove("immediate");
            break;
    }

    exit(EXIT_SUCCESS);
}

pid_size_path postOrderApply(char* path, int pathfun(char* path1)) {
    pid_size_path result;
    struct dirent* directory_entry;
    DIR* directory = opendir(path);
    struct stat directoryStat;
    int dirSize = 0;
    char fullPath[PATH_MAX];
    char special_file_info[PATH_MAX];

    pid_size_path psp;
    int pipe_fd[2];
    char pipe_input_content[PATH_MAX];
    char pipe_output_content[PATH_MAX];
    char fifo_output_content[PATH_MAX];

    int fifo_fd;

    fifo_fd = open(OUTPUT_FILE_PATH, O_WRONLY);

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
                    pipe(pipe_fd);
                    switch (child_pid = fork())
                    {
                        case 0: /* Child */
                            close(pipe_fd[0]);
                            close(fifo_fd);
                            closedir(directory);
                            psp = postOrderApply(fullPath, sizepathfun);
                            sprintf(pipe_input_content, "%d", psp.size);
                            write(pipe_fd[1], pipe_input_content, (strlen(pipe_input_content) + 1));
                            exit(EXIT_SUCCESS);
                        case -1:
                            fprintf(stderr, "Child Process Creation Failed.\n");
                        default:
                            close(pipe_fd[1]);
                            read(pipe_fd[0], pipe_output_content, sizeof(pipe_output_content));
                            if(isRecursiveSizeMode)
                                dirSize += atoi(pipe_output_content);
                            break;
                    }
	            } else {
	                if( S_ISCHR(directoryStat.st_mode) 	||
	                    S_ISBLK(directoryStat.st_mode) 	||
	                    S_ISFIFO(directoryStat.st_mode) ||
	                    S_ISLNK(directoryStat.st_mode) 	||
	                    S_ISSOCK(directoryStat.st_mode)) {
	                		sprintf(special_file_info, "\n%d\t%d\tSpecial file %s\n", getpid(), -1, directory_entry->d_name);
	                		write(fifo_fd, special_file_info, strlen(special_file_info));
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
    sprintf(fifo_output_content, "%d\t%d\t%s", result.pid, result.size, result.path_name);
    write(fifo_fd, fifo_output_content, strlen(fifo_output_content));
    close(fifo_fd);
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