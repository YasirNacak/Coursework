/*  
	Author:         Yasir Nacak
  	Student ID:     161044042
  	Last Modified:  ??/??/???? - ??:??_M (MM:DD:YY - HH:MM)
*/

#include <pthread.h>
#include <semaphore.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <sys/file.h>
#include <sys/time.h>
#include <unistd.h>
#include <dirent.h>
#include <fcntl.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#define SHORT_STR	64
#define MEDIUM_STR	512
#define LONG_STR	2048
#define PATH_MAX	4096

typedef enum {false, true} bool;

typedef enum {REGULAR, LINK, FIFO, BLOCK, CHARACTER, SOCKET} file_type_t;

typedef struct {
	int src_fd;
	int dst_fd;
	char file_name[SHORT_STR];
	long int file_size;
	file_type_t file_type;
} copied_file_info;

typedef struct {
	char copy_src[PATH_MAX];
	char copy_dst[PATH_MAX];
} copy_dir_names;

typedef struct {
	unsigned long int bytes;
	unsigned int file_count;
} after_copy_info;


copy_dir_names* cdn;
bool is_running = true;
bool is_consumer_running = true;
int buffer_size;
copied_file_info* buffer;
int consumer_threads;
pthread_t producer_id;
pthread_t* consumer_ids;
int buf_total = 0;
int ins_loc = 0;
int rem_loc = 0;
unsigned long int total_bytes = 0;
unsigned int total_file_count = 0;
copied_file_info final_inserted;
copied_file_info current_item;
pthread_mutex_t mutex;
pthread_mutex_t stdout_mutex;
sem_t empty;
sem_t full;
sem_t join;
struct timeval start_tv;
struct timeval end_tv;

DIR* opendirs[2048];
int opendirs_index = 0;

/* FILE TYPE COUNTS */
int chr_count = 0;
int blk_count = 0;
int reg_count = 0;
int lnk_count = 0;
int fifo_count = 0;
int sock_count = 0;

char cwd[PATH_MAX];

int
insert_cfi(copied_file_info item);

int
remove_cfi(copied_file_info* item);

void*
producer(void* param);

int
add_files(char copy_src[PATH_MAX], char copy_dst[PATH_MAX]);

void*
consumer(void* param);

bool
is_cfi_equal(copied_file_info c1, copied_file_info c2);

long int
copy_file();

bool
is_usable_dir_entry(char* d_name);

void
strcat_path(char* src, char* path, char* addition);

int
get_chmod(char *path);

void
print_copied_types();

void
int_handler(int dummy);

int
main(int argc, char* argv[]) {
	int i, j;
	
	if(argc != 5) {
		fprintf(stderr, "Usage:pCp [Consumer Threads] [Buffer Size] [SOURCE] [DESTINATION]\n");
		return -1;
	}

	signal(SIGINT, int_handler);

	gettimeofday(&start_tv, NULL);

	consumer_threads = atoi(argv[1]);
	buffer_size = atoi(argv[2]);
	buffer = malloc(buffer_size * sizeof(copied_file_info));
	cdn = malloc(sizeof(copy_dir_names));
	sprintf(cdn->copy_src, "%s", argv[3]);
	sprintf(cdn->copy_dst, "%s", argv[4]);
	getcwd(cwd, sizeof(cwd));

	pthread_mutex_init(&mutex, NULL);
	pthread_mutex_init(&stdout_mutex, NULL);
	sem_init(&full, 0, buffer_size);
	sem_init(&empty, 0, 0);
	sem_init(&join, 0, 0);

	consumer_ids = malloc(sizeof(pthread_t) * consumer_threads);

	pthread_create(&producer_id, NULL, &producer, (void*)cdn);

	for(i = 0; i < consumer_threads; i++) {
		pthread_create(&consumer_ids[i], NULL, &consumer, 0);
	}

	pthread_join(producer_id, NULL);

	for(i = 0; i < consumer_threads; i++) {
		pthread_join(consumer_ids[i], NULL);
	}
	fprintf(stdout, "Copy done.\n");

	free(consumer_ids);
	free(buffer);
	free(cdn);

	gettimeofday(&end_tv, NULL);
	long elapsed = (end_tv.tv_sec-start_tv.tv_sec) * 1000000 + end_tv.tv_usec-start_tv.tv_usec;
	fprintf(stdout, "Copied %d files containing %ld bytes in %ld milliseconds.\n",
		total_file_count, total_bytes, elapsed / 1000);
	
	print_copied_types();

	return 0;
}

int
insert_cfi(copied_file_info item) {
	buffer[ins_loc] = item;
	ins_loc = (ins_loc + 1) % buffer_size;
	buf_total++;
	return 0;
}

int
remove_cfi(copied_file_info* item) {
	*item = buffer[rem_loc];
	rem_loc = (rem_loc + 1) % buffer_size;
	return 0;
}

void*
producer(void* param) {
	copy_dir_names* arg = (copy_dir_names*) param;
	add_files((*arg).copy_src, (*arg).copy_dst);
	// fprintf(stdout, "producer done flag has set.\n");
	is_running = false;
}

int
add_files(char copy_src[PATH_MAX], char copy_dst[PATH_MAX]) {
	struct dirent* dir_entry;
    DIR* directory = opendir(copy_src);
    opendirs[opendirs_index++] = directory;
    struct stat dir_stat;
    char new_src[PATH_MAX];
    char new_dst[PATH_MAX];

	struct stat st = {0};
	if(stat(copy_dst, &st) == -1) {
		mkdir(copy_dst, 0700);
	}

    if(directory == NULL) {
        fprintf(stderr, "Can not access directory: %s.\n", copy_src);
        return -1;
    }

    while((dir_entry = readdir(directory)) != NULL) {
        if(is_usable_dir_entry(dir_entry->d_name)) {
            if (fstatat(dirfd(directory), dir_entry->d_name, &dir_stat, AT_SYMLINK_NOFOLLOW) >= 0) {
            	strcat_path(new_src, copy_src, dir_entry->d_name);
            	strcat_path(new_dst, copy_dst, dir_entry->d_name);
	            if (S_ISDIR(dir_stat.st_mode)) {
                	add_files(new_src, new_dst);
	            } else {
	            	copied_file_info cfi;
					memset(&cfi, 0, sizeof(copied_file_info));
	            	char src_file_dir[PATH_MAX];
	            	char dst_file_dir[PATH_MAX];
	            	sprintf(src_file_dir, "%s/%s/%s", cwd, copy_src, dir_entry->d_name);
	            	sprintf(dst_file_dir, "%s/%s/%s", cwd, copy_dst, dir_entry->d_name);
	            	sprintf(cfi.file_name, "%s", dir_entry->d_name);
	            	if(S_ISFIFO(dir_stat.st_mode)) {
	            		mkfifo(dst_file_dir, get_chmod(src_file_dir));
	            		cfi.src_fd = -1;
	            		cfi.dst_fd = -1;

					} else if(S_ISLNK(dir_stat.st_mode)) {
						char buf_lnk[1024];
						ssize_t len;
						if ((len = readlink(src_file_dir, buf_lnk, sizeof(buf_lnk)-1)) != -1)
							buf_lnk[len] = '\0';
						symlink(buf_lnk, dst_file_dir);
						cfi.src_fd = -1;
	            		cfi.dst_fd = -1;
	                } else {
	            		cfi.src_fd = open(src_file_dir, O_RDONLY);
	            		cfi.dst_fd = open(dst_file_dir, 
	            			O_CREAT | O_WRONLY | O_TRUNC,
	            			get_chmod(src_file_dir));
	                }
	                if(cfi.src_fd == -1 || cfi.dst_fd == -1) {
	                	if(S_ISFIFO(dir_stat.st_mode)) {
		                	pthread_mutex_lock(&stdout_mutex);
	                		fprintf(stdout, "Copied %s (FIFO File)\n",
								cfi.file_name);
							pthread_mutex_unlock(&stdout_mutex);
	                		fifo_count++;
	                		total_file_count++;
	                	} else if(S_ISLNK(dir_stat.st_mode)) {
		                	pthread_mutex_lock(&stdout_mutex);
	                		fprintf(stdout, "Copied %s (Link File)\n",
								cfi.file_name);
							pthread_mutex_unlock(&stdout_mutex);
	                		lnk_count++;
	                		total_file_count++;
	                	} else {
		                	// maximum fd exceed case, can not copy item
		                	pthread_mutex_lock(&stdout_mutex);
							fprintf(stdout, "Can not add %s to the copy queue\n", cfi.file_name);
							pthread_mutex_unlock(&stdout_mutex);
	                	}
	                } else {
	                	cfi.file_size = dir_stat.st_size;
						sem_wait(&full);
						pthread_mutex_lock(&mutex);
						if(S_ISCHR(dir_stat.st_mode)) cfi.file_type = CHARACTER;
						if(S_ISBLK(dir_stat.st_mode)) cfi.file_type = BLOCK;
						if(S_ISREG(dir_stat.st_mode)) cfi.file_type = REGULAR;
						if(S_ISSOCK(dir_stat.st_mode)) cfi.file_type = SOCKET;
						insert_cfi(cfi);
						final_inserted.src_fd = cfi.src_fd;
						final_inserted.dst_fd = cfi.dst_fd;
						sprintf(final_inserted.file_name, "%s", cfi.file_name);
						pthread_mutex_unlock(&mutex);
						sem_post(&empty);
	                }
					
	            }
            } else {
            	fprintf(stderr, "Can not collect information from: %s\n", dir_entry->d_name);
            }
        }
    }
    closedir(directory);
    return 1;
}

void*
consumer(void* param) {
	long int file_bytes;
	copied_file_info item;

	while(is_consumer_running) {
		sem_wait(&empty);
		if(!is_consumer_running) break;
		pthread_mutex_lock(&mutex);
		remove_cfi(&item);
		pthread_mutex_unlock(&mutex);
		sem_post(&full);

		file_bytes = copy_file(item);
		pthread_mutex_lock(&stdout_mutex);
		fprintf(stdout, "Copied %s (%ld bytes)\n",
			item.file_name, file_bytes);
		total_bytes += file_bytes;
		if(is_cfi_equal(item, final_inserted) && !is_running) {
			// fprintf(stdout, "consumer done flag has set.\n");
			is_consumer_running = false;
		}
		total_file_count++;
		if(item.file_type == REGULAR) reg_count++;
		if(item.file_type == SOCKET) sock_count++;
		if(item.file_type == BLOCK) blk_count++;
		if(item.file_type == CHARACTER) chr_count++;
		pthread_mutex_unlock(&stdout_mutex);
	}

	sem_post(&empty);
}

bool
is_cfi_equal(copied_file_info c1, copied_file_info c2) {
	if(c1.src_fd == c2.src_fd &&
		c1.dst_fd == c2.dst_fd &&
		strcmp(c1.file_name, c2.file_name) == 0)
		return true;
	return false;
}

long int
copy_file(copied_file_info cfi) {
	// todo: optimize
	char copy_buffer[4];
	while(read(cfi.src_fd, copy_buffer, 4) > 0) {
		write(cfi.dst_fd, copy_buffer, 4);
	}
	close(cfi.src_fd);
	close(cfi.dst_fd);
	return cfi.file_size;
}

bool
is_usable_dir_entry(char* d_name) {
	if(strcmp(d_name, ".") == 0 || strcmp(d_name, "..") == 0)
		return false;
    return true;
}

void
strcat_path(char* src, char* path, char* addition) {
	strcpy(src, "");
    strcpy(src, path);
    strcat(src, "/");
    strcat(src, addition);
}

int
get_chmod(char *path){
    struct stat ret;

    if(stat(path, &ret) == -1) {
        return -1;
    }

    return 
	(ret.st_mode & S_IRUSR)|(ret.st_mode & S_IWUSR)|(ret.st_mode & S_IXUSR)|
	(ret.st_mode & S_IRGRP)|(ret.st_mode & S_IWGRP)|(ret.st_mode & S_IXGRP)|
	(ret.st_mode & S_IROTH)|(ret.st_mode & S_IWOTH)|(ret.st_mode & S_IXOTH);
}

void
print_copied_types() {
	fprintf(stdout, "Copied File Types:\n");
	fprintf(stdout, "Regular files:		\t%d\n", reg_count);
	fprintf(stdout, "Link files:		\t%d\n", lnk_count);
	fprintf(stdout, "FIFO files:		\t%d\n", fifo_count);
	fprintf(stdout, "Socket files:		\t%d\n", sock_count);
	fprintf(stdout, "Chr special files:	\t%d\n", chr_count);
	fprintf(stdout, "Blk special files:	\t%d\n", blk_count);
}

void
int_handler(int dummy) {
	fprintf(stdout, "\n================================\n");
	fprintf(stdout,   "UNEXPECTED CANCELLATION OF COPY!\n");
	fprintf(stdout,   "================================\n");
	int i;

	pthread_cancel(producer_id);

	for(i = 0; i < consumer_threads; i++) {
		pthread_cancel(consumer_ids[i]);
	}

	// for(i = 0; i < opendirs_index; i++) {
	// 	closedir(opendirs[i]);
	// }
	
	free(consumer_ids);
	free(buffer);
	free(cdn);

	gettimeofday(&end_tv, NULL);
	long elapsed = (end_tv.tv_sec-start_tv.tv_sec) * 1000000 + end_tv.tv_usec-start_tv.tv_usec;
	fprintf(stdout, "Sucessfully Copied %d files containing %ld bytes in %ld milliseconds. There might be more files copied but they are most likely damaged files.\n",
		total_file_count, total_bytes, elapsed / 1000);
	
	print_copied_types();

	exit(EXIT_SUCCESS);
}