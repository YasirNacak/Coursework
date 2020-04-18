package Q4.AbstractFactory;

/**
 * Base functionality that every plane has
 * @author Yasir
 */
public interface Plane {
    /**
     * Creates the skeleton of the plane
     */
    public void ConstructSkeleton();

    /**
     * Places engines of the plane
     */
    public void PlaceEngines();

    /**
     * Places seats of the plane
     */
    public void PlaceSeats();
}
