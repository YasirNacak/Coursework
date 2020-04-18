package Q1;

/**
 * Base interface of solver behaviours for linear equations
 * @author Yasir
 */
public interface LinearEquationSolveMethod {
    public static final double EPSILON = 1e-10;

    /**
     * Base method that will get implemented by classes implementing
     * it that contains linear equation solve logic
     * @param coeffs coefficients of numbers in the equation
     * @param results results of each equation
     * @return a list of numbers representing variables of the equations
     */
    public double[] Solve(double[][] coeffs, double[] results);
}
