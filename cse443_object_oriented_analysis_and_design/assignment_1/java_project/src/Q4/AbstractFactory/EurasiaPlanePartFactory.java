package Q4.AbstractFactory;

/**
 * Plane part factory that gets the parts for planes used in eurasia flights
 * @author Yasir
 */
public class EurasiaPlanePartFactory implements TPXPlanePartFactory {
    /**
     * Gets seating covers used for planes used in eurasia flights
     * @return seating cover that fits eurasia market
     */
    @Override
    public SeatingCover GetSeatingCover() {
        return SeatingCover.Linen;
    }

    /**
     * Gets engine injection used for planes used in eurasia flights
     * @return engine injection that fits eurasia market
     */
    @Override
    public EngineInjection GetInjection() {
        return EngineInjection.Turbofan;
    }
}
