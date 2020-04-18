all: HW06

HW06: main.o GTUIterator.o GTUSetBase.o GTUSet.o GTUMap.o
	g++ main.o GTUIterator.o GTUSetBase.o GTUSet.o GTUMap.o -o exe

main.o: main.cpp
	g++ -std=c++11 -c main.cpp

GTUIterator.o: GTUIterator.cpp
	g++ -std=c++11 -c GTUIterator.cpp

GTUSetBase.o: GTUSetBase.cpp
	g++ -std=c++11 -c GTUSetBase.cpp

GTUSet.o: GTUSet.cpp
	g++ -std=c++11 -c GTUSet.cpp

GTUMap.o: GTUMap.cpp
	g++ -std=c++11 -c GTUMap.cpp

clean:
	rm *.o