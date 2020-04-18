package Q1;

/**
 * Implementation of a linear equation solving method. Uses Gaussian Elimination method to solve.
 * @author Yasir
 */
public class GaussianEliminationSolveMethod implements LinearEquationSolveMethod {
    //https://introcs.cs.princeton.edu/java/95linear/GaussianElimination.java.html
    /**
     * Implementation of Gaussian Elimination method
     * @param coeffs coefficients of numbers in the equation
     * @param results results of each equation
     * @return a list of numbers representing variables of the equations
     */
    @Override
    public double[] Solve(double[][] coeffs, double[] results) {
        int n = results.length;

        for (int p = 0; p < n; p++) {
            // find pivot row and swap
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(coeffs[i][p]) > Math.abs(coeffs[max][p])) {
                    max = i;
                }
            }
            double[] temp = coeffs[p]; coeffs[p] = coeffs[max]; coeffs[max] = temp;
            double t = results[p]; results[p] = results[max]; results[max] = t;

            // singular or nearly singular
            if (Math.abs(coeffs[p][p]) <= EPSILON) {
                System.err.println("Matrix is singular or nearly singular");
            }

            // pivot within A and b
            for (int i = p + 1; i < n; i++) {
                double alpha = coeffs[i][p] / coeffs[p][p];
                results[i] -= alpha * results[p];
                for (int j = p; j < n; j++) {
                    coeffs[i][j] -= alpha * coeffs[p][j];
                }
            }
        }

        // back substitution
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += coeffs[i][j] * x[j];
            }
            x[i] = (results[i] - sum) / coeffs[i][i];
        }

        return x;
    }
}
