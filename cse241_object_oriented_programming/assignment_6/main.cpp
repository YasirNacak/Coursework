#include "GTUSet.h"
#include "GTUSet.cpp"
#include "GTUMap.h"
#include <string>

using mySTL::GTUSetBase;
using mySTL::GTUSet;
using mySTL::GTUMap;

// intersection of two sets
template <class T>
std::shared_ptr<GTUSetBase<T> > setIntersection (const GTUSetBase<T> &first, const GTUSetBase<T> &second);

// utility function to print out a set
template <typename T>
void printSet(const GTUSetBase<T> &set_test);

// utility function to print out a map
template <typename T>
void printMap(const GTUSetBase<T> &map_test);

// driver(test) function of written classes
int main(){
	GTUSet<int> set_test;
	set_test.insert(2);
	printSet(set_test);
	set_test.insert(3);
	printSet(set_test);
	set_test.insert(5);
	printSet(set_test);
	set_test.insert(7);
	printSet(set_test);
	set_test.insert(11);
	printSet(set_test);
	set_test.erase(11);
	printSet(set_test);
	set_test.erase(2);
	printSet(set_test);
	std::cout << "\nFinal version of the first set:\n";
	printSet(set_test);

	GTUMap<std::string, std::string> map_test;
	map_test.insert(std::make_pair("Ahmet", "Firstname"));
	map_test.insert(std::make_pair("Yasir", "Middlename"));
	map_test.insert(std::make_pair("Nacak", "Lastname"));

	std::cout << "\nFirst map:\n";
	printMap(map_test);

	GTUSet<int> set_test2;
	set_test2.insert(3);
	set_test2.insert(5);
	set_test2.insert(99);

	std::cout << "\nSecond set:\n";
	printSet(set_test2);

	std::shared_ptr<GTUSetBase<int>> setIntersectionResult; 
	setIntersectionResult = setIntersection(set_test, set_test2);

	std::cout << "\nIntersection of two sets:\n"; 
	printSet(*(setIntersectionResult.get()));

	GTUMap<std::string, std::string> map_test2;
	map_test2.insert(std::make_pair("Ahmet", "Firstname"));
	map_test2.insert(std::make_pair("Yasir", "Not_Middlename"));
	map_test2.insert(std::make_pair("Nacar", "Lastname"));

	std::cout << "\nSecond map:\n";
	printMap(map_test2);

	std::shared_ptr<GTUSetBase<std::pair<std::string, std::string>>> mapIntersectionResult; 
	mapIntersectionResult = setIntersection(map_test, map_test2);

	std::cout << "\nIntersection of two maps:\n";
	printMap(*(mapIntersectionResult.get()));

	std::cout << std::endl;

	// trying to make GTUSet throw exceptions:
	try{
		GTUSet<char> excpt_set;
		excpt_set.insert('a');
		printSet(excpt_set);
		excpt_set.insert('b');
		printSet(excpt_set);
		excpt_set.insert('c');
		printSet(excpt_set);
		// trying to insert an already existing element
		excpt_set.insert('a');
		printSet(excpt_set);
	} catch(const std::exception &xcpt){
		// handles the exception
		std::cout << xcpt.what() << std::endl;
	}

	return 0;
}

template <class T>
std::shared_ptr<GTUSetBase<T> > setIntersection (const GTUSetBase<T>& first, const GTUSetBase<T>& second){
	std::shared_ptr<GTUSetBase<T>> sharedResult(new GTUSet<T>());
	for(auto iterFirst = first.begin(); iterFirst != first.end(); ++iterFirst)
		for(auto iterSecond = second.begin(); iterSecond != second.end(); ++iterSecond)
			if(*(iterFirst) == *(iterSecond))
				sharedResult->insert(*(iterFirst));
	return sharedResult;
}

template <typename T>
void printSet(const GTUSetBase<T> &set_test){
	for(auto it = set_test.begin(); it != set_test.end(); ++it)
		std::cout << *(it) << " ";
	std::cout << std::endl;
}

template <typename T>
void printMap(const GTUSetBase<T> &map_test){
	for(auto iter = map_test.begin(); iter != map_test.end(); ++iter){
		std::cout << (*iter).first << " " << (*iter).second << std::endl;
	}
}