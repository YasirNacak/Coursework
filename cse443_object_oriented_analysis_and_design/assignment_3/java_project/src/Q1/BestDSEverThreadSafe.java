package Q1;

/**
 * Thread-Safe Adapter of Q1.BestDSEver Class
 * @author Yasir
 */
public class BestDSEverThreadSafe {
    /**
     * Inner instance of BestDSEver that the function calls will be redirected
     */
    private BestDSEver _bestDSEver;

    /**
     * Constructor that initializes the inner BestDSEver
     */
    public BestDSEverThreadSafe() {
        _bestDSEver = new BestDSEver();
    }

    /**
     * Adds an element to the list.
     * Has synchronized keyword so it is thread safe
     * @param o object to be added
     */
    public synchronized void Add(Object o) {
        _bestDSEver.Add(o);
    }

    /**
     * Removes given element if it exists in the list.
     * Has synchronized keyword so it is thread safe.
     * @param o object to be removed
     */
    public synchronized void Remove(Object o) {
        _bestDSEver.Remove(o);
    }

    /**
     * Returns the element that lies at the given index.
     * @param index index to of the element that wants to be retrieved
     * @return index-th element of the list if it exists
     */
    public synchronized Object Get(int index) {
        return _bestDSEver.Get(index);
    }
}
