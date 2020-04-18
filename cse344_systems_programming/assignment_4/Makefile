CFLAGS = -Wall

all: HW02

HW02: main.o
	gcc main.o -o buNeDuFPF

main.o: main.c
	gcc -c -g $(CFLAGS) main.c

clear:
	rm *.o buNeDuFPF
