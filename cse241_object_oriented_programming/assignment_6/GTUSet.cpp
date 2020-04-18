#include "GTUSet.h"

namespace mySTL{
template<typename T>
GTUSet<T>::GTUSet(){
	this->_capacity = 1;
	this->_used = 0;
	std::shared_ptr<T> temp(new T[this->_capacity], std::default_delete<T[]>());
	this->_data = temp;
}

template<typename T>
bool GTUSet<T>::empty() const noexcept{
	// if there are no used elements yet
	if(this->_used == 0)
		return true;
	else return false;
}

template<typename T>
int GTUSet<T>::size() const noexcept{
	// return number of used elements
	return this->_used;
}

template<typename T>
int GTUSet<T>::max_size() const noexcept{
	// return capacity
	return this->_capacity;
}

template<typename T>
void GTUSet<T>::insert(const T &obj) noexcept(false){
	bool isAlreadyIn = false;
	// search container and determine if the obj
	// is alread in it
	for(auto iter = this->begin(); iter != this->end(); ++iter){
		if(*(iter) == obj){
			throw std::invalid_argument("std::invalid::argument: You can not insert an object that is already in the set!");
			isAlreadyIn = true;
		}
	}
	// if the capacity hasn't reached yet
	// put the obj at the end of the container
	if(!isAlreadyIn && this->size() != this->max_size()){
		*(this->end()) = obj;
		this->_used++;
	} else if(!isAlreadyIn && this->size() >= this->max_size()){
		// if the capacity is reached, double it
		// and put the obj then
		this->_capacity *= 2;
		std::shared_ptr<T> temp(new T[this->_capacity], std::default_delete<T[]>());
		
		int i = 0;
		for(auto iter = this->begin(); iter != this->end(); ++iter){
			temp.get()[i] = *(iter);
			i++;
		}

		this->_data = nullptr;
		this->_data = temp;
		*(this->end()) = obj;
		this->_used++;
	}
}

template<typename T>
void GTUSet<T>::erase(const T &obj) noexcept{
	// if the obj is in the container
	// start from the next element of obj
	// and take every element 1 place back
	// then decrease the amount of used by 1 
	bool isAlreadyIn = false;
	for(auto iter = this->begin(); iter != this->end(); ++iter){
		if(*(iter) == obj){
			for(auto iter2 = iter; iter2 != this->end(); ++iter2){
				*(iter2) = *(iter2.getCurrent()+1);
				isAlreadyIn = true;
			}
		}
	}
	if(isAlreadyIn)
		this->_used--;
}

template<typename T>
void GTUSet<T>::clear() noexcept{
	// make number of used elements zero
	this->_used = 0;
}

template<typename T>
int GTUSet<T>::count(const T &obj) const noexcept{
	// iterate through the array and
	// search for occurences of obj
	int result = 0;
	for(auto iter = this->begin(); iter != this->end(); ++iter)
		if(*(iter) == obj)
			result++;
	return result;
}
}