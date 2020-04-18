CFLAGS = -Wall -pedantic-errors

all: HW01

HW01: main.o
	gcc main.o -o buNeDu

main.o: main.c
	gcc -c $(CFLAGS) main.c

clear:
	rm *.o buNeDu
