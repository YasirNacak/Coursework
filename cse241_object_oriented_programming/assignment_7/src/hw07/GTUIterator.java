package hw07;

/**
 * This interface is the basis of an iterator
 * that can be implemented for any generic container.
 * This has all the necessary methods for an iterator.
 * @author Yasir
 */

public interface GTUIterator<T> {
        /**
            Index-wise location of the iterator.
        */
	int position = 0;
        
        /**
            Returns a boolean value depending on the existence
            of a value at the next index of iterator.
            @return if there is an element or not
        */
	boolean hasNext();
        
        /**
            Returns a boolean value depending on the existence
            of a value at the previous index of iterator.
            @return if there is an element or not
        */
	boolean hasPrevious();
        
        /**
            Getter for position field.
            @return value of position field
        */
	int getPosition();
        
        /**
            Returns the value of the current element and does 
            not move the iterator.
            @return value of the variable that the iterator is on
        */
	T current();
        
        /**
            Returns the value of the next element if it exists
            and pushes iterator to the next address.
            @return value of the variable that the iterator is on
        */
	T next();
        
        /**
            Returns the value of the previous element if it exists
            and pushes iterator to the previous address.
            @return value of the variable that the iterator is on
        */
	T previous();
}

