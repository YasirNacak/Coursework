// set class
#ifndef GTUSET_H
#define GTUSET_H
#include "GTUSetBase.h"

namespace mySTL{
template<typename T>
class GTUSet: public GTUSetBase<T>{
public:
	// constructor
	GTUSet();
	// is the container empty
	bool empty() const noexcept;
	// used amount of the container
	int size() const noexcept;
	// capacity of the container
	int max_size() const noexcept;
	// push back a new element to set if
	// it is not already in the container
	void insert(const T &obj) noexcept(false);
	// remove an element from the container
	void erase(const T &obj) noexcept;
	// make the amount of used elements zero
	void clear() noexcept;
	// number of occurences of an object
	int count(const T &obj) const noexcept;
	// find a specific element and return
	// its position
	GTUIterator<T> find(const T &obj) const noexcept{
		GTUIterator<T> iter = this->begin();
		for(iter = this->begin(); iter != this->end(); ++iter)
			if(*(iter) == obj)
				break;
		return iter;
	}
	// reference to start of the container
	GTUIterator<T> begin() const noexcept{
		return (this->_data).get();
	}
	// reference to end of the container
	GTUIterator<T> end() const noexcept{
		return (this->_data).get() + this->_used;
	}
};
}
#endif