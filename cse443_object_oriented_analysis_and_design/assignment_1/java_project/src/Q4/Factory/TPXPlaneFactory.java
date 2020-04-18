package Q4.Factory;

/**
 * Factory class that creates TPX planes based on the model name
 * @author Yasir
 */
public class TPXPlaneFactory {
    /**
     * Factory method that returns an instance of a TPX Plane
     * @param planeType Type (Model Name) of the plane
     * @return an instance of the plane if the model name matches any of the available models
     */
    public TPXPlane GetPlane(TPXPlaneType planeType) {
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

        plane.ConstructSkeleton();
        plane.PlaceEngines();
        plane.PlaceSeats();

        return plane;
    }
}
