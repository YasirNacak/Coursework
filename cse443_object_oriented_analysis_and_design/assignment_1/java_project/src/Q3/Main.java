package Q3;

public class Main {
    public static void main(String[] args) {
        // System.out.println("Welcome to ZirhSan suit designer suit. Please pick a base suit type to start designing your dream exoskeleton suit!");

        FlamethrowerDecorator ft = new FlamethrowerDecorator(new FlamethrowerDecorator(new LaserDecorator(new DecSuit())));

        System.out.println(ft.GetPrice());
        System.out.println(ft.GetWeight());
    }
}
