package hw07;
import javafx.util.Pair;

/**
 * A simple map class that has keys and values and extends
 * GTUSet with T as a pair of K and V.
 * @author Yasir
 */

public class GTUMap<K, V> extends GTUSet<Pair<K, V>>{

        /**
         * Constructor that only calls the constructor of the superclass.
         * @param obj the type of the pair array that needs to be created
         */
	public GTUMap(Class<Pair<K, V>[]> obj) {
		super(obj);
	}
	
        @Override
	public GTUIterator<Pair<K, V>> find(Object key){
		GTUIterator<Pair<K, V>> iter = this.begin();
		K val = iter.current().getKey();
		while(iter.hasNext()) {
			if(val == key)
				break;
			val = iter.next().getKey();
		}
		iter.previous();
		return iter;
	}
	
        @Override
	public void erase(Object key) {
		boolean isInSet = false;
		GTUIterator<Pair<K, V>> iter = this.begin();
		while(iter.hasNext()) {
			K val = iter.next().getKey();
			if(val == key) {
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
	public GTUSetInt<Pair<K, V>> intersection(GTUSetInt<Pair<K, V>> obj) {
		GTUSetInt<Pair<K, V>> result = new GTUMap<K, V>(_type);
		GTUIterator<Pair<K, V>> iterThis = this.begin();
		GTUIterator<Pair<K, V>> iterObj = obj.begin();
		while(iterThis.hasNext()){
			Pair<K, V> pairThis = iterThis.next();
			iterObj = obj.begin();
                        while(iterObj.hasNext()){
				Pair<K, V> pairObj = iterObj.next();
				if(pairThis.getKey() == pairObj.getKey() && pairThis.getValue() == pairObj.getValue()) {
					result.insert(pairThis);
				}
			}
		}
		return result;
	}
	
        /**
         * Returns an element of type V that has the
         * key value of given parameter.
         * @param key the key of the value it is searching
         * @return value of the given key.
         */
	public V at (K key) {
                GTUIterator<Pair<K, V>> iter = this.begin();
                while(iter.hasNext()){
                    Pair<K, V> val = iter.next();
                    if(val.getKey() == key){
                        return val.getValue();
                    }
                }
		return null;
	}
        
        @Override
        public String toString(){
                StringBuilder strBld = new StringBuilder();
                GTUIterator<Pair<K, V>> iter = this.begin();

                while(iter.hasNext()) {
                        K key = iter.current().getKey();
                        V val = iter.next().getValue();
                        strBld.append(key);
                        strBld.append(" ");
                        strBld.append(val);
                        strBld.append("\n");
                }
                String result = strBld.toString();
                return result;
        }
}