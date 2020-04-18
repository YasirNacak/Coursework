// map class
#ifndef GTUMAP_H
#define GTUMAP_H
#include "GTUSet.h"
#include <utility>

namespace mySTL{
template <typename K, typename V>
class GTUMap: public GTUSet<std::pair<K, V> >{
public:
	// index operator of the map
	// finds an element that matches
	// given parameter and returns
	// an adress of that element
	V& operator [] (const K& k) noexcept{
		for(auto iter = this->begin(); iter!=this->end(); ++iter)
			if((*iter).first == k)
				return (*iter).second;
	}
};
}

#endif