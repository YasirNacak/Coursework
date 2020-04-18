package Q1;

import java.util.ArrayList;

/**
 * Utility class for input parsing and processing
 * @author Yasir
 */
public class Utilities {
    /**
     * Converts an array of strings to a matrix of double numbers
     * @param matrix string array that contains numbers separated with spaces
     * @return an matrix of double numbers that is converted from the input
     */
    public static double[][] StringToMatrix(ArrayList<String> matrix) {
        double[][] arr = new double[matrix.size()][matrix.size() + 1];

        for(int i = 0; i < matrix.size(); i++) {
            String [] parts = matrix.get(i).split(" ");
            for(int j = 0; j < matrix.size(); j++) {
                arr[i][j] = Double.parseDouble(parts[j]);
            }
        }

        return arr;
    }

    /**
     * Converts a string to an array of double numbers
     * @param str string that has numbers separated with spaces
     * @return an array of double numbers that is converted from the input
     */
    public static double[] StringToDoubleArray(String str) {
        String[] nums = str.split("\\s+");
        double[] arr = new double[nums.length];

        for(int i = 0; i < nums.length; i++) {
            arr[i] = Double.parseDouble(nums[i]);
        }
        return arr;
    }
}
