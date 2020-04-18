import java.util.Vector;

/**
 * Search tree interface specifically for multidimensional trees
 * @author Yasir
 */
public interface SearchTree<E> {
    boolean add(Vector<E> item);
    boolean contains(Vector<E> target);
    Vector<E> find(Vector<E> target);
    Vector<E> delete(Vector<E> target);
    boolean remove(Vector<E> target);
}
