package Q4.AbstractFactory;

/**
 * Plane part factory that gets the parts for planes used in domestic flights
 * @author Yasir
 */
public class DomesticPlanePartFactory implements TPXPlanePartFactory {
    /**
     * Gets seating covers used for planes used in domestic flights
     * @return seating cover that fits domestic market
     */
    @Override
    public SeatingCover GetSeatingCover() {
        return SeatingCover.Velvet;
    }

    /**
     * Gets engine injection used for planes used in domestic flights
     * @return engine injection that fits domestic market
     */
    @Override
    public EngineInjection GetInjection() {
        return EngineInjection.Turbojet;
    }
}
