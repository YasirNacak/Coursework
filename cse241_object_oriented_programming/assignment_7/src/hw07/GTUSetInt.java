package hw07;
import java.security.InvalidParameterException;

/**
 * This interface is the base of set class and
 * others that implement it. It includes all the necessary
 * basic methods for a simple set.
 * @author Yasir
 */

public interface GTUSetInt<T> {
        /**
         * Controls if the end and begin iterators pointing
         * the same place.
         * @return a boolean if the size is zero
         */
	boolean empty();
        
        /**
         * Getter method for _size variable.
         * @return the value of _size
         */
	int size();
        
        /**
         * Getter method for _capacity variable.
         * @return the value of _capacity
         */
	int max_size();
        
        /**
         * Takes a variable and tries to insert
         * it into the set and fails if the element already
         * exists. Also extends the capacity of the set if 
         * the limit is reached.
         * @param obj the object that is trying to be inserted
         * @throws InvalidParameterException if the element already exists
         */
	void insert(T obj) throws InvalidParameterException;
        
        /**
         * Takes another set and compares its elements to
         * this set and takes all the intersecting elements
         * and inserts them to another set then returns it
         * @param obj the other set
         * @return intersection of the sets
         */
	GTUSetInt<T> intersection(GTUSetInt<T> obj);
        
        /**
         * Takes a variable and tries to find it in
         * the set and erase it from the set. Does nothing if the
         * element is nonexistent in the set.
         * @param obj the object that trying to be deleted
         */
	void erase(Object obj);
        
        /**
         * Returns the iterator that shows the end of the
         * set to the beginning, thus clearing the set.
         */
	void clear();
        
        /**
         * Returns an iterator that is at the position of
         * given object.
         * @param obj the object to search
         * @return the iterator that is showing the object its looking for
         */
	GTUIterator<T> find(Object obj);
        
        /**
         * Returns how many times does the set have a given object.
         * @param obj the object that 
         * @return 
         */
	int count(Object obj);
        
        /**
         * Returns an iterator that is showing the start of the set.
         * @return an iterator at 0 index
         */
	GTUIterator<T> begin();
        
        /**
         * Returns an iterator that is showing the end of the set.
         * @return an iterator at _size index
         */
	GTUIterator<T> end();
}
