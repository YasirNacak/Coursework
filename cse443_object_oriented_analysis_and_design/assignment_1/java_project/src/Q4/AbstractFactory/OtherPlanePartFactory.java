package Q4.AbstractFactory;

/**
 * Plane part factory that gets the parts for planes used in non-specified markets
 * @author Yasir
 */
public class OtherPlanePartFactory implements TPXPlanePartFactory {
    /**
     * Gets seating covers used for planes used in non-specified market planes
     * @return seating cover that fits any non-specified market planes
     */
    @Override
    public SeatingCover GetSeatingCover() {
        return SeatingCover.Leather;
    }

    /**
     * Gets engine injection used for planes used in non-specified market planes
     * @return engine injection that fits any non-specified market planes
     */
    @Override
    public EngineInjection GetInjection() {
        return EngineInjection.GearedTurbofan;
    }
}
