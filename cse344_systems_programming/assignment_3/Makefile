all: HW03

HW03: lsf pwd cat bunedu wc gtushell.o
	gcc gtushell.o -o gtushell

lsf: lsf.o
	gcc lsf.o -o lsf

lsf.o: lsf.c
	gcc -c lsf.c

pwd: pwd.o
	gcc pwd.o -o pwd

pwd.o: pwd.c
	gcc -c pwd.c

cat: cat.o
	gcc cat.o -o cat

cat.o: cat.c
	gcc -c cat.c

wc: wc.o
	gcc wc.o -o wc

wc.o: wc.c
	gcc -c wc.c

bunedu: bunedu.o
	gcc bunedu.o -o bunedu

bunedu.o: bunedu.c
	gcc -c bunedu.c

gtushell.o: gtushell.c
	gcc -c gtushell.c

clear:
	rm *.o gtushell lsf pwd cat wc bunedu
