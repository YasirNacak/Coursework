/**
 * Utilities that are used in Genetic Algorithm Real Value Crossovers
 * @author Yasir
 */
public class Utils {
    /**
     * Takes a real number and returns its binary form's string representation
     * @param d number to be converted
     * @return 64bit binary representation of the given value
     */
    public static String DoubleToString(double d) {
        long longBits = Double.doubleToLongBits(d);
        String str = Long.toBinaryString(longBits);
        return String.format("%64s", str).replace(' ', '0');
    }

    /**
     * Takes a 64 character string that represents a real number and converts it to a double value
     * @param s string representation of the real number
     * @return converted real number from the given string
     */
    public static Double StringToDouble(String s) {
        long doubleBits = Long.parseLong(s, 2);
        return Double.longBitsToDouble(doubleBits);
    }
}
