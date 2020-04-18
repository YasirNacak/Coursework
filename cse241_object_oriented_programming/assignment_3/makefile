all: HW03

HW03: main.o connectFour.o
	g++ main.o connectFour.o -o HW03

main.o: main.cpp
	g++ -std=c++11 -c main.cpp

connectFour.o: connectFour.cpp
	g++ -std=c++11 -c connectFour.cpp

cleanObj:
	rm *.o

cleanAll:
	rm *.o HW03.exe
