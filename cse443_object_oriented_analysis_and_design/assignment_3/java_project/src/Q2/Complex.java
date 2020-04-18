package Q2;

/**
 * Class that holds data to represent a complex number
 * @author Yasir
 */
public class Complex {
    /**
     * Real part of the complex number
     */
    public int Real;

    /**
     * Imaginary part of the complex number
     */
    public int Imaginary;

    /**
     * Default constructor that initializes real and imaginary parts as zero
     */
    public Complex() {
        Real = 0;
        Imaginary = 0;
    }

    /**
     * Constructor that initializes this number based on the given values
     * @param real Real part of the number
     * @param imaginary Imaginary part of the number
     */
    public Complex(int real, int imaginary) {
        Real = real;
        Imaginary = imaginary;
    }

    /**
     * Adds two given complex numbers and returns the addition result
     * @param c1 First number to be added
     * @param c2 Second number to be added
     * @return result of sums of real parts and sums of imaginary parts as a new complex number
     */
    public static Complex Add(Complex c1, Complex c2) {
        return new Complex(c1.Real + c2.Real, c1.Imaginary + c2.Imaginary);
    }

    /**
     * Returns string representation of a complex number
     * @return R+Ii formatted string of the complex number
     */
    @Override
    public String toString() {
        return Real + "+" + Imaginary + "i";
    }
}
