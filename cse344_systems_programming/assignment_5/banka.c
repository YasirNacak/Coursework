/*  
	Author:         Yasir Nacak
  	Student ID:     161044042
  	Last Modified:  04/26/2019 - 01:37 PM (MM:DD:YY - HH:MM)
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>
#include <signal.h>
#include <time.h>

#define PNAME "./Banka"
#define USAGE "Usage: %s [Total Running Time].\n"
#define MINARG 2
#define MAXARG 2
#include "usage.h"

#define FIFO_DIR "/tmp/bank_server"
#define FIFO_PERMS 0666
#define BUF_SIZE 2048

#define CHILD_COUNT 4

typedef struct request_t {
	int pid;
	char req_body[BUF_SIZE];
	char response_fifo_dir[BUF_SIZE];
} request;

int is_running = 1;

void
closing_timer_handler(union sigval sv);

void
response_timer_handler(union sigval sv);

void
clear_buffer(char* buffer);

request
parse_request(char* buffer);

pid_t
create_desk(int index, int read_pipe[2], int write_pipe[2]);

void
int_handler(int dummy);

int log_fd;
char** all_reqs;
int req_size = 0, req_index = 0;
pid_t desk_pids[CHILD_COUNT];
char children_active_filename[CHILD_COUNT][BUF_SIZE];

int
main(int argc, char *argv[]) {
	check_usage(argc, argv, MINARG, MAXARG);
	int i, j;
	int service_time = atoi(argv[1]);
	if(service_time < 0) {
		fprintf(stderr, "Usage: %s", USAGE);
		exit(EXIT_FAILURE);
	}

	signal(SIGINT, int_handler);

	log_fd = open("Banka.log", O_CREAT | O_WRONLY | O_TRUNC, S_IRUSR | S_IWUSR);
	char log_date_and_st_info[256];
	time_t current_time = time(NULL);
	struct tm local_time = *localtime(&current_time);
	char* tr_months[] = {
	    "Ocak",
	    "Subat",
	    "Mart",
	    "Nisan",
	    "Mayis",
	    "Haziran",
	    "Temmuz",
	    "Agustos",
	    "Eyluk",
	    "Ekim",
	    "Kasim",
	    "Aralik"
	};
	sprintf(log_date_and_st_info, "%d %s %d tarihinde islem basladi. Bankamiz %d saniye hizmet verecek.\n", 
		local_time.tm_mday, tr_months[local_time.tm_mon], local_time.tm_year + 1900, service_time);
	char log_headers[128] = "clientPid\tprocessNo\tPara\tislem bitis zamani\n";
	char log_headers_sep[128] = "---------\t---------\t----\t------------------\n";
	write(log_fd, log_date_and_st_info, strlen(log_date_and_st_info));
	write(log_fd, log_headers, strlen(log_headers));
	write(log_fd, log_headers_sep, strlen(log_headers_sep));

	srand(time(NULL));
	int total_service_count = 0;
	int service_counts[CHILD_COUNT];
	int pipe_p_read[CHILD_COUNT][2];
	int pipe_d_read[CHILD_COUNT][2];
	int d_busy[CHILD_COUNT] = {0};
	
	for(i = 0; i < CHILD_COUNT; i++) {
		service_counts[i] = 0;
		pipe(pipe_p_read[i]);
		fcntl(pipe_p_read[i][0], F_SETFL, O_NONBLOCK);
		pipe(pipe_d_read[i]);
		desk_pids[i] = create_desk(i, pipe_p_read[i], pipe_d_read[i]);
	}

    /* TIMER */
	char info[] = "Service time finished.";
    struct sigevent sev;
    struct itimerspec trigger;
	timer_t timerid;

    memset(&sev, 0, sizeof(struct sigevent));
    memset(&trigger, 0, sizeof(struct itimerspec));

    sev.sigev_notify = SIGEV_THREAD;
    sev.sigev_notify_function = &closing_timer_handler;
    sev.sigev_value.sival_ptr = &info;

    timer_create(CLOCK_REALTIME, &sev, &timerid);
    trigger.it_value.tv_sec = service_time;
    timer_settime(timerid, 0, &trigger, NULL);

    /* FIFO */
	mkfifo(FIFO_DIR, FIFO_PERMS);
	int fifo_fd = open(FIFO_DIR, O_RDONLY | O_NONBLOCK);
	char buffer[BUF_SIZE], child_res[BUF_SIZE];

	all_reqs = (char**)calloc(BUF_SIZE, sizeof(char*));
	for (i = 0; i < BUF_SIZE; i++) {
	    all_reqs[i] = (char*) calloc(BUF_SIZE, sizeof(char));
	}

	struct timespec zero_time;
	clock_gettime(CLOCK_MONOTONIC_RAW, &zero_time);
	char log_file_content[BUF_SIZE];
	clear_buffer(log_file_content);	
	/* GET REQUESTS, SEND RESPONSES */
	while(is_running) {
		int nbytes = read(fifo_fd, buffer, BUF_SIZE);
		if(nbytes > 0) {
			/* Write upcoming request to list of all requests */
			strcpy(all_reqs[req_size], buffer);
			req_size++;
		}

		if(req_size > req_index) {
			for(i = 0; i < CHILD_COUNT; i++) {
				if(!d_busy[i] && req_size > req_index) {
					int client_pid;
					sscanf(all_reqs[req_index], "%d", &client_pid);
					sprintf(children_active_filename[i], "%s%d", "/tmp/bank_client_", client_pid);
					write(pipe_d_read[i][1], all_reqs[req_index], BUF_SIZE);
					d_busy[i] = 1;
					req_index++;
				}
			}
		}

		for(i = 0; i < CHILD_COUNT; i++) {
			int pipe_nbytes = read(pipe_p_read[i][0], child_res, BUF_SIZE);
			if(pipe_nbytes > 0) {
				total_service_count++;
				service_counts[i]++;
				char res_fifo_dir[BUF_SIZE];
				char client_pid[10];
				int client_pid_index = 0;
				int child_money;
				int desk_index;
				sscanf(child_res, "%d - %s - %d", &desk_index, res_fifo_dir, &child_money);
				int res_fifo_fd = open(res_fifo_dir, O_WRONLY);
				sprintf(child_res, "%d", child_money);
				write(res_fifo_fd, child_res, BUF_SIZE);
				close(res_fifo_fd);
				d_busy[desk_index] = 0;

				int dir_seperator_index = 0;
				for(j = strlen(res_fifo_dir) - 1; j > 0; j--) {
					if(res_fifo_dir[j] == '_') { 
						dir_seperator_index = j;
						break;
					}
				}

				for(j = dir_seperator_index + 1; j < strlen(res_fifo_dir); j++) {
					client_pid[client_pid_index++] = res_fifo_dir[j];
				}
				client_pid[client_pid_index] = '\0';
				// sprintf(client_pid, "%s", res_fifo_dir);

				char process_no[10];
				sprintf(process_no, "process%d", i+1);
				
				struct timespec current_time;
				clock_gettime(CLOCK_MONOTONIC_RAW, &current_time);
				long int stime_msec = ((current_time.tv_sec - zero_time.tv_sec) * 1000000 + 
					(current_time.tv_nsec - zero_time.tv_nsec) / 1000) / 1000;

				clear_buffer(log_file_content);	
				sprintf(log_file_content, "%s\t\t%s\t%d\t%ld\n", client_pid, process_no, child_money, stime_msec);
				write(log_fd, log_file_content, strlen(log_file_content));
			}
		}
	}

	/* RESPOND AWAITING CLIENTS AFTER THE BANK IS CLOSED */
	while(req_index < req_size) {
		request req = parse_request(all_reqs[req_index]);
		if(req.pid == 0 || strlen(all_reqs[req_index]) == 0) break;
		int res_fifo_fd = open(req.response_fifo_dir, O_WRONLY);
		char res_str[BUF_SIZE];
		sprintf(res_str, "%d", -1);
		write(res_fifo_fd, res_str, BUF_SIZE);
		req_index++;
	}

	/* CLEANUP AND FINALIZING */
	for(i = 0; i < CHILD_COUNT; i++) {
		kill(desk_pids[i], SIGKILL);
		if(strcmp(children_active_filename[i], "")) {
			int kill_fd = open(children_active_filename[i], O_WRONLY);
			if(kill_fd != -1) {
				char res_str[BUF_SIZE];
				sprintf(res_str, "%d", -1);
				write(kill_fd, res_str, BUF_SIZE);
				close(kill_fd);
			}
		}
	}

	char log_ending_info[128];
	sprintf(log_ending_info, "\n%d saniye dolmustur %d musteriye hizmet verdik\n", service_time, total_service_count);
	write(log_fd, log_ending_info, strlen(log_ending_info));
	char child_service_count[128];
	clear_buffer(child_service_count);

	for(i = 0; i < CHILD_COUNT; i++) {
		sprintf(child_service_count, "process%d %d\n", i+1, service_counts[i]);
		write(log_fd, child_service_count, strlen(child_service_count));
		clear_buffer(child_service_count);
	}

	for (i = 0; i < BUF_SIZE; i++) {
	    free(all_reqs[i]);
	}
	free(all_reqs);
    timer_delete(timerid);
	unlink(FIFO_DIR);
	close(log_fd);
	return 0;
}

void
closing_timer_handler(union sigval sv) {
	char* s = sv.sival_ptr;
	puts(s);
    is_running = 0;
}

void
response_timer_handler(union sigval sv) {
	char* res_str = sv.sival_ptr;
	char fifo_dir[BUF_SIZE], response[BUF_SIZE];
	int money;
	int pfd1;
	int index;
	
	sscanf(res_str, "%d - %d - %s - %d", &index, &pfd1, fifo_dir, &money);
	sprintf(response, "%d - %s - %d", index, fifo_dir, money);
	
	write(pfd1, response, BUF_SIZE);
}

void
clear_buffer(char* buffer) {
	int i;
	for(i = 0; i < BUF_SIZE; i++)
		buffer[i] = '\0';
}

request
parse_request(char* buffer) {
	request result;
	char pid_str[BUF_SIZE];
	sscanf(buffer, "%s - %s", pid_str, result.req_body);
	result.pid = atoi(pid_str);
	sprintf(result.response_fifo_dir, "/tmp/bank_client_%d", result.pid);
	return result;
}

pid_t
create_desk(int index, int read_pipe[2], int write_pipe[2]) {
	pid_t child_pid = fork();
	if(child_pid == 0) {
		close(read_pipe[0]); /* child will write to this */
		close(write_pipe[1]); /* child will read from this */

		char child_buf[BUF_SIZE];
		while(1) {
			int nbytes = read(write_pipe[0], child_buf, BUF_SIZE);
			request req = parse_request(child_buf);

			if(is_running && nbytes >= 0) {
				char res_str[BUF_SIZE];

				int money = rand() % 100 + 1;
				sprintf(res_str, "%d - %d - %s - %d",  index, read_pipe[1], req.response_fifo_dir, money);

				int desk_resp_time = 1;
				struct sigevent sev;
				struct itimerspec trigger;
				timer_t desk_timer_id;

				memset(&sev, 0, sizeof(struct sigevent));
				memset(&trigger, 0, sizeof(struct itimerspec));

				sev.sigev_notify = SIGEV_THREAD;
				sev.sigev_notify_function = &response_timer_handler;
			    sev.sigev_value.sival_ptr = &res_str;

				timer_create(CLOCK_REALTIME, &sev, &desk_timer_id);
				trigger.it_value.tv_sec = desk_resp_time;
				trigger.it_value.tv_nsec = 500000000;
				timer_settime(desk_timer_id, 0, &trigger, NULL);
			}

			clear_buffer(child_buf);
		}

		exit(EXIT_SUCCESS);
	} else if(child_pid == -1) {
		fprintf(stderr, "Child Process Creation Failed. Exiting...\n");
		exit(EXIT_FAILURE);
	} else {
		close(read_pipe[1]); /* parent will read from this */
		close(write_pipe[0]); /* parent will write to this */
		return child_pid;
	}
}

void
int_handler(int dummy) {
	int i;
	fprintf(stdout, "%s\n", "SIGINT HANDLER");
	/* RESPOND AWAITING CLIENTS AFTER THE BANK IS CLOSED */
	while(req_index < req_size) {
		request req = parse_request(all_reqs[req_index]);
		if(req.pid == 0 || strlen(all_reqs[req_index]) == 0) break;
		int res_fifo_fd = open(req.response_fifo_dir, O_WRONLY);
		char res_str[BUF_SIZE];
		sprintf(res_str, "%d", -1);
		write(res_fifo_fd, res_str, BUF_SIZE);
		req_index++;
	}

	/* CLEANUP AND FINALIZING */
	for(i = 0; i < CHILD_COUNT; i++) {
		if(strcmp(children_active_filename[i], "")) {
			int kill_fd = open(children_active_filename[i], O_WRONLY);
			if(kill_fd != -1) {
				char res_str[BUF_SIZE];
				sprintf(res_str, "%d", -1);
				write(kill_fd, res_str, BUF_SIZE);
				close(kill_fd);
			}
		}
	}

	char log_ending_info[128];
	sprintf(log_ending_info, "\nbanka beklenmedik bir sekilde durduruldu\n");
	write(log_fd, log_ending_info, strlen(log_ending_info));
	char child_service_count[128];
	clear_buffer(child_service_count);

	for (i = 0; i < BUF_SIZE; i++) {
	    free(all_reqs[i]);
	}
	free(all_reqs);

	unlink(FIFO_DIR);
	close(log_fd);
	exit(EXIT_SUCCESS);
}