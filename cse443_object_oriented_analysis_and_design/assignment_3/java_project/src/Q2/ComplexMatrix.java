package Q2;

import java.util.Random;

/**
 * Class that represents a square matrix of complex numbers
 * @author Yasir
 */
public class ComplexMatrix {
    /**
     * Size of each dimension of the matrix
     */
    private int _size;

    /**
     * Elements of the matrix
     */
    public Complex[][] data;

    /**
     * Constructor that creates an empty matrix with given size
     * @param size Size of each dimension of the matrix
     */
    public ComplexMatrix(int size) {
        _size = size;

        data = new Complex[_size][_size];

        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _size; j++) {
                data[i][j] = new Complex();
            }
        }
    }

    /**
     * Randomly generates a complex number for the each element of this matrix
     */
    public void Randomize() {
        Random rng = new Random();
        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _size; j++) {
                data[i][j] = new Complex(rng.nextInt(3), rng.nextInt(5));
            }
        }
    }

    /**
     * Converts this matrix to a string and returns it
     * @return String representation of the matrix
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _size; j++) {
                sb.append(data[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
