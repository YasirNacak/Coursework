package Q4.Factory;

/**
 * TPX Family of planes. Every model of TPX plane has this set of functionality
 * @author Yasir
 */
public class TPXPlane implements Plane{
    /**
     * Name of the plane model
     */
    private String _name;

    /**
     * Text that describes the purpose and usage of the plane
     */
    private String _purposeDescription;

    /**
     * Type of skeleton that the plane has
     */
    private PlaneSkeleton _skeleton;

    /**
     * Number of engines in the plane
     */
    private int _engineCount;

    /**
     * Number of seats in the plane
     */
    private int _seatCount;

    /**
     * Constructor that creates a TPX Plane with given properties
     * @param name Model name
     * @param purposeDescription Usage and purpose description
     * @param skeleton Type of skeleton
     * @param engineCount Number of engines
     * @param seatCount Number of seats
     */
    public TPXPlane(String name, String purposeDescription, PlaneSkeleton skeleton, int engineCount, int seatCount) {
        this._name = name;
        this._purposeDescription = purposeDescription;
        this._skeleton = skeleton;
        this._engineCount = engineCount;
        this._seatCount = seatCount;
    }

    /**
     * Creates the skeleton of the plane
     */
    public void ConstructSkeleton() {
        System.out.println("Constructing " + this._skeleton + " skeleton.");
    }

    /**
     * Places engines of the plane
     */
    public void PlaceEngines() {
        System.out.println("Placing " + this._engineCount + " jet engines.");
    }

    /**
     * Places seats of the plane
     */
    public void PlaceSeats() {
        System.out.println("Placing " + this._seatCount + " seats for passengers.");
    }

    @Override
    /**
     * String representation of a TPX plane. Lists all the properties of the plane
     * @return short string describing this plane
     */
    public String toString() {
        return _name + " " + _purposeDescription + " " + _skeleton.toString() + " " + _engineCount + " " + _seatCount;
    }
}
