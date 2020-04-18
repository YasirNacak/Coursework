package Q1;

/**
 * An Object list that is not thread-safe
 * @author Yasir
 */
public class BestDSEver {
    private static final int INITIAL_CAPACITY = 10;

    public int Size = 0;

    private int _currentCapacity;
    private Object[] _values;

    /**
     * Constructs an empty list with an initial capacity
     */
    public BestDSEver(){
        _currentCapacity = INITIAL_CAPACITY;
        _values = new Object[_currentCapacity];
    }

    /**Adds the specified element to the end of the list
     * @param o element to be added to this list
     */
    public void Add(Object o){
        if(this.Size == _currentCapacity){
            Reallocate();
        }

        _values[this.Size] = o;
        this.Size++;
    }

    /**
     * Returns the element at the specified position in the list
     * @param index index of the element to return
     * @return the element at the specified position in this list
     */
    public Object Get(int index){
        if(index < 0 || this.Size <= index){
            throw new ArrayIndexOutOfBoundsException(index);
        }

        return _values[index];
    }

    /**
     * Removes the specified element if it exists.
     * Shifts any subsequent elements to the left.
     * @param o object to be removed
     */
    public void Remove(Object o){
        int index = -1;

        for (int i = 0; i < this.Size; i++) {
            if(_values[i].equals(o)) {
                index = i;
            }
        }

        if(index == -1) {
            throw new ArrayIndexOutOfBoundsException();
        }

        if (Size - index + 1 >= 0) {
            System.arraycopy(_values, index + 1, _values, index + 1 - 1, this.Size - index + 1);
        }

        this.Size--;
    }

    private void Reallocate(){
        _currentCapacity *= 2;
        Object[] enlargedArr = new Object[_currentCapacity];
        System.arraycopy(_values, 0, enlargedArr, 0, _values.length);
        _values = enlargedArr;
    }
}
