// abstract set class
#ifndef GTUSETBASE_H
#define GTUSETBASE_H
#include <memory>
#include <iostream>
#include <exception>
#include "GTUIterator.h"
#include "GTUIterator.cpp"

namespace mySTL{
template<typename T>
class GTUSetBase{
public:
	// is the container empty
	virtual bool empty() const noexcept = 0;
	// used amount of the container
	virtual int size() const noexcept = 0;
	// capacity of the container
	virtual int max_size() const noexcept = 0;
	// push back a new element to set if
	// it is not already in the container
	virtual void insert(const T &obj) noexcept(false) = 0;
	// remove an element from the container
	virtual void erase(const T &obj) noexcept = 0;
	// make the amount of used elements zero
	virtual void clear() noexcept = 0;
	// find a specific element and return
	// its position
	virtual GTUIterator<T> find(const T &obj) const noexcept = 0;
	// number of occurences of an object
	virtual int count(const T &obj) const noexcept = 0;
	// reference to start of the container
	virtual GTUIterator<T> begin() const noexcept = 0;
	// reference to end of the container
	virtual GTUIterator<T> end() const noexcept = 0;

protected:
	int _capacity, // total capacity of container
		_used; // used amount of capacity
	std::shared_ptr<T>	_data; // actual data in container
};
}
#endif