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
#include <signal.h>
#define STRLEN_MAX 	1024
#define HISLEN		1024

volatile sig_atomic_t terminated = 0;

int is_valid_command(char* str) {
	if(strcmp(str, "lsf") 		== 0)	return 1;
	if(strcmp(str, "pwd") 		== 0)	return 1;
	if(strcmp(str, "cd") 		== 0)	return 1;
	if(strcmp(str, "bunedu") 	== 0)	return 1;
	if(strcmp(str, "cat") 		== 0)	return 1;
	if(strcmp(str, "wc") 		== 0)	return 1;
	if(strcmp(str, "help") 		== 0)	return 1;

										return 0;
}

int is_utility(char* str) {
	if(strcmp(str, "pwd")		== 0)	return 1;
	if(strcmp(str, "lsf")		== 0)	return 1;
	if(strcmp(str, "bunedu")	== 0)	return 1;
	if(strcmp(str, "cat")		== 0)	return 1;
	if(strcmp(str, "wc") 		== 0)	return 1;
						return 0;
}

void print_command_list() {
	fprintf(stdout, "Available Commands:\n");
	fprintf(stdout, "pwd\t: Shows current working directory.\n");
	fprintf(stdout, "cd\t: Changes current working directory.\n");
	fprintf(stdout, "help\t: Shows list of available commands.\n");
	fprintf(stdout, "cat\t: Shows content of given file.\n");
	fprintf(stdout, "wc\t: Shows total number of lines in given file.\n");
	fprintf(stdout, "bunedu\t: Shows disk usage of given directory.\n");
	fprintf(stdout, "exit\t: Closes this program.\n");
}

int get_in_redir_index(char* command) {
	int i;
	for (i = 0; i < strlen(command); i++)
		if(command[i] == '<')
			return i;
	return -1;
}

int get_out_redir_index(char* command) {
	int i;
	for (i = 0; i < strlen(command); i++)
		if(command[i] == '>')
			return i;
	return -1;
}

int get_pipe_index(char* command) {
	int i;
	for (i = 0; i < strlen(command); i++)
		if(command[i] == '|')
			return i;
	return -1;	
}
 
void terminate_shell(int signum) {
    fprintf(stdout, "\nSIGTERM received, exiting gracefully.\n");
    terminated = 1;
}

int main() {
	char working_directory[STRLEN_MAX] = "/";
	char command[STRLEN_MAX], other_command[STRLEN_MAX];
	char cwd[STRLEN_MAX];
	char command_path[STRLEN_MAX];
	char **prev_commands;
	int command_index = 0;
	int i;

	setbuf(stdout, NULL);

	struct sigaction action;
    memset(&action, 0, sizeof(struct sigaction));
    action.sa_handler = terminate_shell;
    sigaction(SIGTERM, &action, NULL);

	prev_commands = malloc(HISLEN * sizeof(char*));
	for (i = 0; i < HISLEN; i++)
	    prev_commands[i] = malloc(STRLEN_MAX * sizeof(char));

    getcwd(cwd, sizeof(cwd));
    getcwd(working_directory, sizeof(cwd));

	while(!terminated) {
		fprintf(stdout, "GTUSHELL ~ %s$ ", working_directory);
		fgets(command, sizeof(command), stdin);
		command[strlen(command) - 1] = '\0';

		if(strcmp(command, "exit") == 0) {
			terminated = 1;
		}

		if(terminated) break;

		if(command[0] == '!') {
			char prev_to_exec[STRLEN_MAX];
			int prev_to_exec_index = 0;
			int arr_index;
			for (i = 1; i < strlen(command); i++) {
				prev_to_exec[prev_to_exec_index] = command[i];
			}
			arr_index = atoi(prev_to_exec);
			if(arr_index > command_index || arr_index <= 0)
				continue;
			
			sprintf(command, "%s", prev_commands[command_index - arr_index]);
		}

		sprintf(prev_commands[command_index], "%s", command);
		command_index++;

		char token[STRLEN_MAX];
		sscanf(command, "%s", token);

		int in_redir_index = get_in_redir_index(command);
		int is_input_redirected = 0;
		char in_redir_file_name[STRLEN_MAX];

		int out_redir_index = get_out_redir_index(command);
		int is_output_redirected = 0;
		char out_redir_file_name[STRLEN_MAX];

		if(in_redir_index != -1) {
			int pos = 0;
			for(i = in_redir_index + 2; i < strlen(command); i++) {
				in_redir_file_name[pos] = command[i];
				pos++;
			}
			in_redir_file_name[pos] = '\0';
			command[in_redir_index - 1] = '\0';
			is_input_redirected = 1;
		}

		if(out_redir_index != -1) {
			int pos = 0;
			for (i = out_redir_index + 2; i < strlen(command); i++) {
				out_redir_file_name[pos] = command[i];
				pos++;
			}
			out_redir_file_name[pos] = '\0';
			command[out_redir_index - 1] = '\0';
			is_output_redirected = 1;
		}

		int pipe_index = get_pipe_index(command);
		int is_piping = 0;
		if(pipe_index != -1) {
			int pos = 0;
			for(i = pipe_index + 2; i < strlen(command); i++) {
				other_command[pos] = command[i];
				pos++;
			}
			other_command[pos] = '\0';
			command[pipe_index - 1] = '\0';
			is_piping = 1;
		}

		sprintf(command_path, "%s/%s", cwd, token);
		if(is_valid_command(token)) {
			if(is_utility(token)) {
				char* argv[STRLEN_MAX];
				int arg_count = 0;
				char *arg;
				arg = strtok (command, " ");
				argv[arg_count] = malloc(STRLEN_MAX);
				sprintf(argv[arg_count], "%s", arg);
				arg_count++;
				while (arg != NULL)
				{
					arg = strtok(NULL, " ");
					if(arg != NULL) {
						argv[arg_count] = malloc(STRLEN_MAX);
						sprintf(argv[arg_count], arg);
						arg_count++;
					}
				}

				int pfd[2];
				if(is_piping) {
					pipe(pfd);
				}

				int child_status;
				pid_t child_pid = fork();
				if(child_pid == 0) {
					if(is_input_redirected) {
						int fd = open(in_redir_file_name, O_RDONLY, 0666);
						dup2(fd, 0);
					}

					if(is_output_redirected) {
						int fd = open(out_redir_file_name, O_WRONLY | O_CREAT | O_TRUNC, 0666);
						dup2(fd, 1);
					}

					if(is_piping) {
						close(pfd[0]);

						dup2(pfd[1], 1);
					}
					execv(command_path, argv);
					perror("execv error: ");
					exit(EXIT_SUCCESS);
				} else {
					waitpid(child_pid, &child_status, 0);

					arg_count--;
					while(arg_count >= 0) {
						for (i = 0; i < STRLEN_MAX; i++)
							argv[arg_count][i] = 0;

						free(argv[arg_count]);
						argv[arg_count] = NULL;
						arg_count--;
					}
				}

				if(is_piping) {
					pid_t other_pid = fork();
					int other_status;

					char* argv_other[STRLEN_MAX];
					int arg_other_count = 0;
					char *arg_other;
					arg_other = strtok(other_command, " ");
					argv_other[arg_other_count] = malloc(STRLEN_MAX);
					sprintf(argv_other[arg_other_count], "%s", arg_other);
					arg_other_count++;
					while (arg_other != NULL)
					{
						arg_other = strtok(NULL, " ");
						if(arg_other != NULL) {
							argv_other[arg_other_count] = malloc(STRLEN_MAX);
							sprintf(argv_other[arg_other_count], "%s", arg_other);
							arg_other_count++;
						}
					}

					if(other_pid == 0) {
						close(pfd[1]);
						dup2(pfd[0], 0);

						execv(other_command, argv_other);
						exit(EXIT_SUCCESS);
					} else {
						close(pfd[1]);
						close(pfd[0]);
						waitpid(other_pid, &other_status, 0);

						arg_other_count--;
						while(arg_other_count >= 0) {
							for (i = 0; i < STRLEN_MAX; i++)
								argv_other[arg_other_count][i] = 0;

							free(argv_other[arg_other_count]);
							argv_other[arg_other_count] = NULL;
							arg_other_count--;
						}
					}
					
				}
			} else {
				if(strcmp(token, "cd") == 0) {
					char param[STRLEN_MAX];
					int chdir_ret;
					sscanf(command, "%s %s", token, param);
					chdir_ret = chdir(param);
					if(chdir_ret == -1) {
						fprintf(stderr, "Can not access directory: %s\n", param);
					}
    				getcwd(working_directory, sizeof(cwd));
				} else if(strcmp(token, "help") == 0) {
					print_command_list();
				}
			}
		} else {
			fprintf(stdout, "%s is not a valid command\n", token);
		}

		sprintf(command, "\0");
		sprintf(token, "\0");
	}

	for (i = 0; i < HISLEN; i++) {
		free(prev_commands[i]);
	}
	free(prev_commands);

	return 0;
}
