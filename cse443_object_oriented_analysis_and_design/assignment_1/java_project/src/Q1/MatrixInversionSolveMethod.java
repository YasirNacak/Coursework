package Q1;

/**
 * Implementation of a linear equation solving method. Uses Matrix Inversion method to solve.
 * @author Yasir
 */
public class MatrixInversionSolveMethod implements LinearEquationSolveMethod {
    /**
     * Implementation of linear equation solving by inversion
     * @param A coefficients of numbers in the equation
     * @param b results of each equation
     * @return a list of numbers representing variables of the equations
     */
    @Override
    public double[] Solve(double[][] A, double[] b) {
        int size = A.length;
        double sum = 0.0;
        double[][] inverse;
        double[] x = new double[size];

        inverse = Invert(A);

        for (int i = 0; i < size; ++i) {
            sum = 0.0;
            for (int j = 0; j < size; ++j) {
                sum += inverse[i][j]*b[j];
            }
            x[i] = sum;
        }
        return x;
    }

    // Got help from: https://www.sanfoundry.com/java-program-find-inverse-matrix/

    /**
     * Takes inverse of a matrix
     * @param a matrix to be inversed
     * @return invertex matrix
     */
    private double[][] Invert(double a[][]) {
        int size = a.length;
        double x[][] = new double[size][size];
        double b[][] = new double[size][size];
        int index[] = new int[size];

        for(int i = 0; i < size; ++i) {
            b[i][i] = 1;
        }

        Gaussian(a, index);

        for(int i = 0; i < size - 1; ++i) {
            for(int j = i + 1; j < size; ++j) {
                for(int k = 0; k < size; ++k) {
                    b[index[j]][k] -= a[index[j]][i] * b[index[i]][k];
                }
            }
        }

        for(int i = 0; i < size; ++i) {
            if (a[index[size - 1]][size - 1] <= EPSILON) {
                System.err.println("Matrix is singular or nearly singular");
            }

            x[size - 1][i] = b[index[size - 1]][i] / a[index[size - 1]][size - 1];
            for (int j = size - 2; j >= 0; --j) {
                x[j][i] = b[index[j]][i];
                for (int k = j + 1; k < size; ++k) {
                    x[j][i] -= a[index[j]][k] * x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }

        return x;
    }

    /**
     * Method to carry out the partial-pivoting Gaussian Elimination
     * @param a matrix to get partially pivotted
     * @param index pivoting order
     */
    private void Gaussian(double a[][], int index[]) {
        int size = index.length;
        double arr[] = new double[size];

        for (int i = 0; i < size; ++i) {
            index[i] = i;
        }

        for (int i = 0; i < size; ++i) {
            double a1 = 0;
            for (int j = 0; j < size; ++j) {
                double a0 = Math.abs(a[i][j]);
                if (a0 > a1)
                    a1 = a0;
            }
            arr[i] = a1;
        }

        int k = 0;
        for (int j = 0; j < size - 1; ++j) {
            double pivot1 = 0;
            for (int i = j; i < size; ++i) {
                double pivot0 = Math.abs(a[index[i]][j]);
                pivot0 /= arr[index[i]];
                if (pivot0 > pivot1) {
                    pivot1 = pivot0;
                    k = i;
                }
            }

            int temp = index[j];
            index[j] = index[k];
            index[k] = temp;

            for (int i = j + 1; i < size; ++i) {
                double pj = a[index[i]][j] / a[index[j]][j];
                a[index[i]][j] = pj;
                for (int l = j + 1; l < size; ++l) {
                    a[index[i]][l] -= pj * a[index[j]][l];
                }
            }
        }
    }
}
