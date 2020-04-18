import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * An environment for our chromosomes to live and reproduce for finding a result to our equation until they converge or reach a maximum number of generations
 * @author Yasir
 */
public abstract class Genetics {
    /**
     * Highest possible outcome of the equation
     */
    public static final double BIGGEST_SOLUTION = 00126.0434782f;

    /**
     * Lowest possible outcome of the solution
     */
    public static final double SMALLEST_SOLUTION = -075.0000000f;

    /**
     * Name of this environment
     */
    private String _name;

    /**
     * Maximum number of chromosomes that can live in this environment
     */
    protected int _maxPopulation;

    /**
     * Maximum number of generations until reproduction stops
     */
    private int _maxGeneration;

    /**
     * Percentage of a mutation on each gene of each chromosome
     */
    private int _mutationPercentage;

    /**
     * The maximum possible fitness difference worst and the best chromosomes for reproduction to stop
     */
    private double convergenceThreshold;

    /**
     * Number of cuts to make in genes to cross them
     */
    private int _crossoverPoints;

    /**
     * All chromosomes currently living in this environment
     */
    protected Chromosome[] currentPopulation;

    /**
     * All best fitness values of each generation
     */
    private ArrayList<Double> _allFitnesses;

    /**
     * Is simulation paused
     */
    private boolean _isPaused;

    /**
     * Has simulation started
     */
    private boolean _isRunning;

    /**
     * Creates an environment based on given parameters
     * @param _name name of this environment
     * @param maxPopulation maximum chromosomes that can live in a generation in this environment
     * @param maxGeneration maximum number of generations until reproduction stops
     * @param mutationPercentage percentage of possible mutations in each gene of each chromosome after each reproduction
     * @param convergenceThreshold maximum difference between worst and the best fitness for reproduction to stop
     * @param crossoverPoints number of cuts to make in genes to cross them
     */
    public Genetics(String _name, int maxPopulation, int maxGeneration, int mutationPercentage, double convergenceThreshold, int crossoverPoints) {
        this._name = _name;
        this._maxPopulation = maxPopulation;
        this._maxGeneration = maxGeneration;
        this._mutationPercentage = mutationPercentage;
        this.convergenceThreshold = convergenceThreshold;
        this._crossoverPoints = crossoverPoints;
        this.currentPopulation = new Chromosome[_maxPopulation];
        this._allFitnesses = new ArrayList<>();
        this._isPaused = false;
    }

    /**
     * Starts simulating this environment
     * Does the:
     *
     * Generate the initial population
     * Compute fitness
     * REPEAT
     *      Selection
     *      Crossover
     *      Mutation
     *      Compute fitness
     * UNTIL population has converged
     *
     * Method.
     */
    public void Start() {
        _isRunning = true;

        GeneticsGraphWindow.CreateSeries(_name);

        currentPopulation = GeneratePopulation();

        int currentGeneration = 0;

        while (_isRunning && currentGeneration < _maxGeneration && !IsConverged()) {
            _allFitnesses.add(GetFittest().equationResult);

            Chromosome[] parents = Select();
            Chromosome[] nonMutatedChildren = Crossover(parents[0], parents[1]);

            Chromosome[] children = Mutate(nonMutatedChildren);

            // replace worst fit chromosomes newly born children
            currentPopulation[0] = children[0];
            currentPopulation[1] = children[1];

            Arrays.sort(currentPopulation);

            currentGeneration++;

            double[] allFitnessesArray = new double[currentGeneration];

            for (int i = 0; i < currentGeneration; i++) {
                allFitnessesArray[i] = _allFitnesses.get(i);
            }

            GeneticsGraphWindow.UpdateSeries(_name, currentGeneration, allFitnessesArray);

            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (_isPaused) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Finds and returns the fittest chromosome in the current environment
     * @return fittest chromosome
     */
    public Chromosome GetFittest() {
        return currentPopulation[_maxPopulation - 1];
    }

    /**
     * Finds if the difference of fitness between the best and the worst are lower than our threshold
     * @return true if there is a really low chance of getting new different chromosomes, false otherwise
     */
    private boolean IsConverged() {
        return currentPopulation[_maxPopulation - 1].fitness - currentPopulation[0].fitness < convergenceThreshold;
    }

    /**
     * Generates an initial population
     * @return a population of randomly generated chromosomes
     */
    private Chromosome[] GeneratePopulation() {
        Chromosome[] population = new Chromosome[_maxPopulation];
        Random rng = new Random();

        // generate the initial population
        for (int i = 0; i < _maxPopulation; i++) {
            boolean doesNumbersSatisy = false;
            while (!doesNumbersSatisy) {
                // generate a number between 0 and 5 for x1
                double currentX1 = rng.nextDouble() * 5;

                // give 5 - x1 to x2
                double currentX2 = rng.nextDouble() * 5;

                if(currentX1 + currentX2 <= 5) {
                    // put the newly generated chromosome into the population
                    Chromosome current = new Chromosome(currentX1, currentX2);
                    population[i] = current;
                    doesNumbersSatisy = true;
                }
            }
        }

        Arrays.sort(population);

        return population;
    }

    /**
     * Selects parents in the current environment
     * @return a list of 2 chromosomes for each parent
     */
    protected abstract Chromosome[] Select();

    /**
     * Crosses over two chromosomes based on some cut points
     * @param parent1 first parent to sample
     * @param parent2 second parent to sample
     * @return children with crossed over genes from parents
     */
    protected Chromosome[] Crossover(Chromosome parent1, Chromosome parent2) {
        Chromosome[] children = new Chromosome[2];
        String parent1X1 = Utils.DoubleToString(parent1.x1);
        String parent1X2 = Utils.DoubleToString(parent1.x2);

        String parent2X1 = Utils.DoubleToString(parent2.x1);
        String parent2X2 = Utils.DoubleToString(parent2.x2);

        boolean doesChildFit = false;

        Random rng = new Random();

        int crossoverPoint = 16;
        while(!doesChildFit && crossoverPoint < 64) {
            String firstChildX1BinaryStr = parent1X1;
            String firstChildX2BinaryStr = parent1X2;

            String secondChildX1BinaryStr = parent2X1;
            String secondChildX2BinaryStr = parent2X2;

            if(_crossoverPoints == 1) {
                firstChildX1BinaryStr = parent1X1.substring(0, crossoverPoint) + parent2X1.substring(crossoverPoint, 64);
                firstChildX2BinaryStr = parent1X2.substring(0, crossoverPoint) + parent2X2.substring(crossoverPoint, 64);
                secondChildX1BinaryStr = parent2X1.substring(0, crossoverPoint) + parent1X1.substring(crossoverPoint, 64);
                secondChildX2BinaryStr = parent2X2.substring(0, crossoverPoint) + parent1X2.substring(crossoverPoint, 64);
            } else if (_crossoverPoints == 2) {
                int secondCrossOverPoint = rng.nextInt(63 - crossoverPoint) + crossoverPoint + 1;
                firstChildX1BinaryStr = parent1X1.substring(0, crossoverPoint) + parent2X1.substring(crossoverPoint, secondCrossOverPoint) + parent1X1.substring(secondCrossOverPoint, 64);
                firstChildX2BinaryStr = parent1X2.substring(0, crossoverPoint) + parent2X2.substring(crossoverPoint, secondCrossOverPoint) + parent1X2.substring(secondCrossOverPoint, 64);
                secondChildX1BinaryStr = parent2X1.substring(0, crossoverPoint) + parent1X1.substring(crossoverPoint, secondCrossOverPoint) + parent2X1.substring(secondCrossOverPoint, 64);
                secondChildX2BinaryStr = parent2X2.substring(0, crossoverPoint) + parent1X2.substring(crossoverPoint, secondCrossOverPoint) + parent2X2.substring(secondCrossOverPoint, 64);
            }

            double child1X1 = Utils.StringToDouble(firstChildX1BinaryStr);
            double child1X2 = Utils.StringToDouble(firstChildX2BinaryStr);

            double child2X1 = Utils.StringToDouble(secondChildX1BinaryStr);
            double child2X2 = Utils.StringToDouble(secondChildX2BinaryStr);

            if(child1X1 + child1X2 <= 5 && child2X1 + child2X2 <= 5) {
                doesChildFit = true;
                children[0] = new Chromosome(child1X1, child1X2);
                children[1] = new Chromosome(child2X1, child2X2);
            } else {
                crossoverPoint++;
            }
        }

        return children;
    }

    /**
     * Mutates children based on the equation rule and a percentage of mutation on each gene
     * @param children children to mutate
     * @return mutated versions of given children chromosomes
     */
    private Chromosome[] Mutate(Chromosome[] children) {
        Chromosome[] result = new Chromosome[2];

        boolean isMutationValid = false;

        while (!isMutationValid) {
            double mutatedC1X1 = MutateGene(children[0].x1);
            double mutatedC1X2 = MutateGene(children[0].x2);
            double mutatedC2X1 = MutateGene(children[1].x1);
            double mutatedC2X2 = MutateGene(children[1].x2);

            if(mutatedC1X1 + mutatedC1X2 <= 5 && mutatedC2X1 + mutatedC2X2 <= 5) {
                result[0] = new Chromosome(mutatedC1X1, mutatedC1X2);
                result[1] = new Chromosome(mutatedC2X1, mutatedC2X2);
                isMutationValid = true;
            }
        }

        return result;
    }

    /**
     * Mutates a single gene
     * @param n gene to mutate
     * @return randomly bit flipped version of the given gene
     */
    private double MutateGene(double n) {
        String geneStr = Utils.DoubleToString(n);

        Random rng = new Random();
        for (int i = 16; i < geneStr.length(); i++) {
            int mutChance = rng.nextInt(100);

            if(mutChance < _mutationPercentage) {
                StringBuilder sb = new StringBuilder(geneStr);
                if(sb.charAt(i) == '0') {
                    sb.setCharAt(i, '1');
                } else {
                    sb.setCharAt(i, '0');
                }
                geneStr = sb.toString();
            }
        }

        return Utils.StringToDouble(geneStr);
    }

    /**
     * Pauses the simulation
     */
    public void Pause() {
        _isPaused = true;
    }

    /**
     * Continues the simulation
     */
    public void Continue() {
        _isPaused = false;
    }

    /**
     * Resets the environment and stops simulating
     */
    public void Stop() {
        _isRunning = false;
    }
}
