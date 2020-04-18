/*  
	Author:         Yasir Nacak
  	Student ID:     161044042
  	Last Modified:  04/25/2019 - 12:18 AM (MM:DD:YY - HH:MM)
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

#define PNAME "./Client"
#define USAGE "Usage: %s [Customer Count].\n"
#define MINARG 2
#define MAXARG 2
#include "usage.h"

#define SRV_FIFO_DIR "/tmp/bank_server"
#define CLT_FIFO_DIR "/tmp/bank_client_%d"
#define FIFO_PERMS 0666
#define BUF_SIZE 2048

int
main(int argc, char *argv[]) {
	check_usage(argc, argv, MINARG, MAXARG);
	int i;
	int customer_count = atoi(argv[1]);
	if(customer_count < 0) {
		fprintf(stderr, "Usage: %s", USAGE);
		exit(EXIT_FAILURE);
	}

	int* children_pid = (int*)malloc(customer_count * sizeof(int));
	fprintf(stdout, "Bank Client Started With: %d Customer(s)\n", customer_count);
	for(i = 0; i < customer_count; i++) {
		children_pid[i] = fork();

		if(children_pid[i] == 0) {
			free(children_pid);
			int srv_fifo_fd = open(SRV_FIFO_DIR, O_WRONLY);

			if(srv_fifo_fd == -1) {
				fprintf(stderr, "%s\n", "BANK IS CLOSED. CLIENT FAILED.");
				exit(EXIT_FAILURE);
			}

			char clt_fifo_name[255];
			sprintf(clt_fifo_name, CLT_FIFO_DIR, getpid());
			mkfifo(clt_fifo_name, FIFO_PERMS);

			char req[BUF_SIZE], res[BUF_SIZE];
			req[0] = '\0', res[0] = '\0';
			sprintf(req, "%d - %s", getpid(), "GIVEMEMONEY");
			write(srv_fifo_fd, req, BUF_SIZE);

			int clt_fifo_fd = open(clt_fifo_name, O_RDONLY);
			read(clt_fifo_fd, res, BUF_SIZE);
			int money = atoi(res);
			if(money >= 0) {
				fprintf(stdout, "Musteri %d %d lira aldi :)\n", getpid(), money);
			} else if (money == -1) {
				fprintf(stdout, "Musteri %d parasini alamadi :(\n", getpid());
			}

			unlink(clt_fifo_name);
			close(srv_fifo_fd);
			close(clt_fifo_fd);
			exit(EXIT_SUCCESS);
		} else if(children_pid[i] == -1) {
			fprintf(stderr, "Child creation failed.\n");
		}
	}

	for(i = 0; i < customer_count; i++) {
		int child_status;
		waitpid(children_pid[i], &child_status, 0);
	}

	free(children_pid);
	return 0;
}
