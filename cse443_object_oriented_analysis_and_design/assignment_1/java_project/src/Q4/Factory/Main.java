package Q4.Factory;

public class Main {
    public static void main(String[] args) {
        TPXPlaneFactory factory = new TPXPlaneFactory();
        TPXPlane tpx100plane = factory.GetPlane(TPXPlaneType.TPX100);
        System.out.println(tpx100plane);
        TPXPlane tpx200plane = factory.GetPlane(TPXPlaneType.TPX200);
        System.out.println(tpx200plane);
        TPXPlane tpx300plane = factory.GetPlane(TPXPlaneType.TPX300);
        System.out.println(tpx300plane);
    }
}
