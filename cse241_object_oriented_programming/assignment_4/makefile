all: HW04

HW04: main.o connectFour.o
	g++ main.o connectFour.o -o exe

main.o: main.cpp
	g++ -std=c++11 -c main.cpp

connectFour.o: connectFour.cpp
	g++ -std=c++11 -c connectFour.cpp

cleanObj:
	rm *.o

cleanAll:
	rm *.o exe