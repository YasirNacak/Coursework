CFLAGS = -ansi -Wall -pedantic-errors

all: HW02

HW02: main.o
	gcc main.o -o buNeDuFork

main.o: main.c
	gcc -c $(CFLAGS) main.c

clear:
	rm *.o buNeDuFork
