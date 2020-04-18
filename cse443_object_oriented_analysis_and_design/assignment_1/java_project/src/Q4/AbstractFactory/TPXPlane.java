package Q4.AbstractFactory;

/**
 * TPX Family of planes. Every model of TPX plane has this set of functionality
 * @author Yasir
 */
public class TPXPlane implements Plane {
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
     * Type of the engine injection
     */
    private EngineInjection _injection;

    /**
     * Number of seats in the plane
     */
    private int _seatCount;

    /**
     * Type of the seating cover
     */
    private SeatingCover _seatingCover;

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
     * Constructor for TPX Planes with a specific market
     * @param name Model name
     * @param purposeDescription Usage and purpose description
     * @param skeleton Type of skeleton
     * @param engineCount Number of engines
     * @param injection Type of engine injection
     * @param seatCount Number of seats
     * @param seatingCover Type of seating cover
     */
    public TPXPlane(String name, String purposeDescription, PlaneSkeleton skeleton, int engineCount, EngineInjection injection, int seatCount, SeatingCover seatingCover) {
        this(name, purposeDescription, skeleton, engineCount, seatCount);
        this._injection = injection;
        this._seatingCover = seatingCover;
    }

    /**
     * Creates the skeleton of the plane
     */
    public void ConstructSkeleton() {
        System.out.println("Constructing " + this._skeleton + " skeleton...");
    }

    /**
     * Places engines of the plane
     */
    public void PlaceEngines() {
        System.out.println("Placing " + this._engineCount + " jet engines...");
        System.out.println("Configuring " + this._injection + " injection for the engines...");
    }

    /**
     * Places seats of the plane
     */
    public void PlaceSeats() {
        System.out.println("Placing " + this._seatCount + " seats for passengers...");
        System.out.println("Covering seats up with beautiful " + this._seatingCover + "...");
    }

    /**
     * Sets the type of engine injection this plane has
     * @param injection new type of engine injection
     */
    public void SetEngineInjection(EngineInjection injection) {
        this._injection = injection;
    }

    /**
     * Sets the type of seating cover this plane has
     * @param cover new type of seating cover
     */
    public void SetSeatingCover(SeatingCover cover) {
        this._seatingCover = cover;
    }

    /**
     * String representation of a TPX plane. Lists all the properties of the plane
     * @return short string describing this plane
     */
    @Override
    public String toString() {
        return _name + " " + _purposeDescription + " " + _skeleton.toString() + " " + _engineCount + " " + _injection + " " + _seatCount + " " + _seatingCover;
    }
}
