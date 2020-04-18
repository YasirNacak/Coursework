import java.util.Random;

/**
 * Genetic algorithm class that utilizes Roulette Wheel Selection method
 * @author Yasir
 */
public class RouletteWheelGenetics extends Genetics {
    public RouletteWheelGenetics(int maxPopulation, int maxGeneration, int mutationPercentage, double convergenceThreshold, int crossoverPoints) {
        super("Roulette", maxPopulation, maxGeneration, mutationPercentage, convergenceThreshold, crossoverPoints);
    }

    /**
     * Selects two parents randomly by probabilities based on fitness values
     * @return new parents that are going to reproduce in the current generation
     */
    @Override
    protected Chromosome[] Select() {
        Random rng = new Random();
        Chromosome[] parents = new Chromosome[2];

        double totalFitness = 0;
        for (int j = 0; j < currentPopulation.length; j++) {
            totalFitness += currentPopulation[j].fitness;
        }

        // spin the wheel and get 2 parents
        for (int i = 0; i < 2; i++) {
            double rouletteResult = rng.nextDouble() * totalFitness;
            double partialSum = 0;
            for (Chromosome chromosome : currentPopulation) {
                partialSum += chromosome.fitness;
                if (partialSum >= rouletteResult) {
                    parents[i] = chromosome;
                    break;
                }
            }
        }

        return parents;
    }
}
