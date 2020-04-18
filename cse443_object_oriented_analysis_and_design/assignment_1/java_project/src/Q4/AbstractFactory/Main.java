package Q4.AbstractFactory;

public class Main {
    public static void main(String[] args) {
        TPXPlaneFactory factory = new TPXPlaneFactory();
        TPXPlane tpx100plane = factory.GetPlane(TPXPlaneType.TPX100, TPXPlaneMarket.Domestic);
        System.out.println(tpx100plane);
        TPXPlane tpx200plane = factory.GetPlane(TPXPlaneType.TPX200, TPXPlaneMarket.Eurasia);
        System.out.println(tpx200plane);
        TPXPlane tpx300plane = factory.GetPlane(TPXPlaneType.TPX300, TPXPlaneMarket.None);
        System.out.println(tpx300plane);
    }
}
