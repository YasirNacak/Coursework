package Q1;

/**
 * Solver class that can change its behaviour to any solving method
 * @author Yasir
 */
public class LinearEquationSolverDeluxe {
    /**
     * Current method of solving an equation
     */
    private LinearEquationSolveMethod _method;

    /**
     * Constructor. Defaults to Gaussian Elimination method
     */
    public LinearEquationSolverDeluxe() {
        this._method = new GaussianEliminationSolveMethod();
    }

    /**
     * Setter that changes the current solving method
     * @param method new solving method
     */
    public void SetMethod(LinearEquationSolveMethod method) {
        this._method = method;
    }

    /**
     * Executes the current solving method on given set of equations
     * @param coeffs coefficients of numbers in the equation
     * @param results results of each equation
     * @return a list of numbers representing variables of the equations
     */
    public double[] executeMethod(double [][] coeffs, double[] results) {
        return _method.Solve(coeffs, results);
    }
}
