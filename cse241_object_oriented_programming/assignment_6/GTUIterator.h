#ifndef GTUITERATOR_H
#define GTUITERATOR_H
#include <memory>

namespace mySTL{
template <typename T>
class GTUIterator{
public:
	// basic constructor
	GTUIterator(T *current) noexcept{
		_current = current;
	}
	// assignment overload
	GTUIterator& operator = (const GTUIterator &other) noexcept{
		_current = other._current;
		return *this;
	}
	// indirection operator overload
	T& operator * () const noexcept{
		return *_current;
	}
	// arrow member operator overload
	T* operator -> () const noexcept{
		return _current;
	}
	// is equal operator (checks if two are the same)
	bool operator == (const GTUIterator &other) const noexcept{
		return this->_current == other._current;	
	}
	// opposite of is equal operator
	bool operator != (const GTUIterator &other) const noexcept{
		return !(*this==other);
	}
	// address increment (prefix)
	GTUIterator& operator ++ () noexcept{
		_current++;
		return *this;
	}
	// address increment (postfix)
	GTUIterator& operator ++ (int ignoreable) noexcept{
		GTUIterator tmp = *this;
		_current++;
		return tmp;
	}
	// address decrement (prefix)
	GTUIterator& operator -- () noexcept{
		_current--;
		return *this;
	}
	// address decrement (postfix)
	GTUIterator& operator -- (int ignoreable) noexcept{
		GTUIterator tmp = *this;
		_current--;
		return tmp;
	}
	// basic getter
	T* getCurrent() const noexcept{
		return _current;
	}
private:
	// data member
	T* _current;
};
}

#endif