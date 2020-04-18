package hw07;
import java.lang.reflect.Array;
import java.security.InvalidParameterException;

/**
 * A simple set class that implements GTUSetInt.
 * @author Yasir
 */

public class GTUSet<T> implements GTUSetInt<T>{
        /**
         * The array of all elements in the set
         */
	public T[] _data;
        
        /**
         * Number of filled spots.
         */
	protected int _size;
        
        /**
         * Current limit of the set.
         */
	protected int _capacity;
        
        /**
         * Type of the elements in the set
         */
	protected Class<T[]> _type;
	
        /**
         * Simple constructor that sets the capacity to 2 and
         * creates an empty array of that size.
         * @param obj the type of the pair array that needs to be created
         */
	public GTUSet(Class<T[]> obj) {
		_type = obj;
		_size = 0;
		_capacity = 2;
		_data = obj.cast(Array.newInstance(obj.getComponentType(), _capacity));
	}
	
	public class GTUSetIterator implements GTUIterator<T>{
		int position = 0;
		
		@Override
		public boolean hasNext() {
			if (position < _size)
                return true;
			return false;
		}

		@Override
		public boolean hasPrevious() {
			if (position > 0)
                return true;
			return false;
		}

		@Override
		public T current() {
			return _data[position];
		}
		
		@Override
		public T next() {
            return _data[position++];
		}

		@Override
		public T previous() {
			return _data[position--];
		}

		@Override
		public int getPosition() {
			return position;
		}
	}
	
	@Override
	public boolean empty() {
		boolean result = false;
		if(_size == 0)
			result = true;
		return result;
	}

	@Override
	public int size() {
		return _size;
	}

	@Override
	public int max_size() {
		return _capacity;
	}

	@Override
	public void insert(T obj) throws InvalidParameterException {
		boolean isAlreadyIn = false;
		GTUIterator<T> iter = this.begin();
		while(iter.hasNext()) {
			T val = iter.next();
			if(val == obj)
				isAlreadyIn = true;
		}
		if(!isAlreadyIn) {
			if(_size + 1 == _capacity) {
				T[] tempData = _type.cast(Array.newInstance(_type.getComponentType(), _capacity * 2));
				for(int i=0; i<_size; i++)
					tempData[i] = _data[i];
				_data = _type.cast(Array.newInstance(_type.getComponentType(), _capacity * 2));
				for(int i=0; i<_size; i++)
					_data[i] = tempData[i];
				_capacity *= 2;
			}
			_data[_size] = obj;
			_size++;
		} else if (isAlreadyIn) {
			throw new InvalidParameterException("Exception: Can not insert an already existing element.");
		}
	}

	@Override
	public GTUSetInt<T> intersection(GTUSetInt<T> obj) {
		GTUSetInt<T> result = new GTUSet<T>(_type);
		GTUIterator<T> iterThis = this.begin();
		GTUIterator<T> iterObj = obj.begin();
		while(iterThis.hasNext()){
			T valThis = iterThis.next();
			iterObj = obj.begin();
                        while(iterObj.hasNext()){
				T valObj = iterObj.next();
				if(valThis == valObj) {
					result.insert(valThis);
				}
			}
		}
		return result;
	}

	@Override
	public void erase(Object obj) {
		boolean isInSet = false;
		GTUIterator<T> iter = this.begin();
		while(iter.hasNext()) {
			T val = iter.next();
			if(val == obj) {
				isInSet = true;
				break;
			}
		}
		if(isInSet) {
			for(int i=iter.getPosition(); i<_size; i++)
				_data[i-1] = _data[i];
			_size--;
		}
	}

	@Override
	public void clear() {
		_size = 0;
	}

	@Override
	public GTUIterator<T> find(Object obj) {
		GTUIterator<T> iter = this.begin();
		T val = iter.current();
		while(iter.hasNext()) {
			if(val == obj)
				break;
			val = iter.next();
		}
		iter.previous();
		return iter;
	}

	@Override
	public int count(Object obj) {
		int result = 0;
		GTUIterator<T> iter = this.begin();
		while(iter.hasNext()) {
			T val = iter.next();
			if(val == obj)
				result++;
		}
		return result;
	}

	@Override
	public GTUIterator<T> begin() {
		return new GTUSetIterator();
	}

	@Override
	public GTUIterator<T> end() {
		GTUSetIterator it = new GTUSetIterator();
		while(it.hasNext())
			it.next();
		return it;
	}
        
        @Override
        public String toString(){
                StringBuilder strBld = new StringBuilder();
                GTUIterator<T> iter = this.begin();

                while(iter.hasNext()) {
                        T val = iter.next();
                        strBld.append(val);
                        strBld.append(" ");
                }
                String result = strBld.toString();
                return result;
        }
}

