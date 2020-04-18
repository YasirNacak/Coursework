package Q4.AbstractFactory;

/**
 * Base interface for all types of plane part factories.
 * Since not all types of seating covers and engine injections match
 * for TPX Planes with certain type of market, providing user with
 * an abstract factory of this type is necessary.
 *
 * @author Yasir
 */
public interface TPXPlanePartFactory {
    public SeatingCover GetSeatingCover();
    public EngineInjection GetInjection();
}
