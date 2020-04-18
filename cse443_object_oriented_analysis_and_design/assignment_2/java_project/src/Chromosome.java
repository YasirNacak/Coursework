/**
 * Class that represents a single chromosome in our problem
 * @author Yasir
 */
public class Chromosome implements Comparable<Chromosome>{
    /**
     * First gene, x1 variable in equation
     */
    public double x1;

    /**
     * Second gene, x2 variable in equation
     */
    public double x2;

    /**
     * Fitness value of this chromosome
     */
    public double fitness;

    /**
     * Result that equation gives for this x1 and x2
     */
    public double equationResult;

    /**
     * Constructor that takes genes and calculates its fitness and equation result
     * @param x1 First gene
     * @param x2 Second gene
     */
    public Chromosome(double x1, double x2) {
        this.x1 = x1;
        this.x2 = x2;
        this.equationResult = EquationResult();
        this.fitness = Fitness();
    }

    /**
     * String representation of a chromosome, tells
     * @return x1 and x2 variables alongside fitness and equation result
     */
    @Override
    public String toString() {
        return "X1: " + x1 + ", X2: " + x2 + ", Fitness: " + fitness + ", Result: " + equationResult;
    }


    /**
     * Compare method of chromosomes based on fitness
     * @param o chromosome to compare with
     * @return 0 if equal, 1 is this is better, -1 if other is better
     */
    @Override
    public int compareTo(Chromosome o) {
        if(fitness == o.fitness) {
            return 0;
        } else if(fitness > o.fitness) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * Puts chromosome into the equation and gets the result
     * @return result of the equation when this chromosome holds the variables
     */
    private double EquationResult() {
        double t1 = 20 * x1 * x2;
        double t2 = 16 * x2;
        double t3 = 2 * x1 * x1;
        double t4 = x2 * x2;
        double t5 = (x1 + x2) * (x1 + x2);

        return t1 + t2 - t3 - t4 - t5;
    }

    /**
     * Gets fitness of the chromosome based on the equation result and also makes it positive by adding minimum possible value of the equation
     * @return fitness of this chromosome
     */
    private double Fitness() {
        return EquationResult() - Genetics.SMALLEST_SOLUTION;
    }
}
