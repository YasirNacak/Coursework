package Q4.AbstractFactory;

/**
 * Factory class that creates TPX planes based on the model name and market specification
 * @author Yasir
 */
public class TPXPlaneFactory {
    /**
     * Factory method that returns an instance of a TPX Plane without a specific market specification
     * @param planeType Type (Model Name) of the plane
     * @return an instance of the plane if the model name matches any of the available models
     */
    private TPXPlane GetPlane(TPXPlaneType planeType) {
        TPXPlane plane = GetPlane(planeType, TPXPlaneMarket.None);

        return plane;
    }

    /**
     * Factory method that returns an instance of a TPX Plane that have a specific market aim
     * @param planeType Type (Model Name) of the plane
     * @param market Market that this TPX Plane will be used at
     * @return an instance of the plane if the model name matches any of the available models
     */
    public TPXPlane GetPlane(TPXPlaneType planeType, TPXPlaneMarket market) {
        TPXPlane plane = null;

        switch (planeType) {
            case TPX100:
                plane = new TPXPlane(planeType.toString(), "Domestic Flights", PlaneSkeleton.Aluminum, 1, 50);
                break;
            case TPX200:
                plane = new TPXPlane(planeType.toString(), "Domestic and Short International Flights", PlaneSkeleton.Nickel, 2, 100);
                break;
            case TPX300:
                plane = new TPXPlane(planeType.toString(), "Transatlantic Flights", PlaneSkeleton.Titanium, 4, 250);
                break;
        }

        EngineInjection injection;
        SeatingCover cover;

        TPXPlanePartFactory partFactory;

        switch (market) {
            case Domestic:
                partFactory = new DomesticPlanePartFactory();
                break;
            case Eurasia:
                partFactory = new EurasiaPlanePartFactory();
                break;
            default:
                partFactory = new OtherPlanePartFactory();
                break;
        }

        plane.SetEngineInjection(partFactory.GetInjection());
        plane.SetSeatingCover(partFactory.GetSeatingCover());

        plane.ConstructSkeleton();
        plane.PlaceEngines();
        plane.PlaceSeats();

        return plane;
    }
}
