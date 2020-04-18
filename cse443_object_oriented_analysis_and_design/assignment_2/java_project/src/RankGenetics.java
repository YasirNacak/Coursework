import java.util.Random;

/**
 * Genetic algorithm class that utilizes Rank Selection method
 * @author Yasir
 */
public class RankGenetics extends Genetics {
    public RankGenetics(int maxPopulation, int maxGeneration, int mutationPercentage, double convergenceThreshold, int crossoverPoints) {
        super("Rank", maxPopulation, maxGeneration, mutationPercentage, convergenceThreshold, crossoverPoints);
    }

    /**
     * Selects two parents based on the rank selection algorithm that gives value 0 to lowest fit and N to
     * the highest fit and uses these values like percentages to pick
     * @return new parents that are going to reproduce in the current generation
     */
    @Override
    protected Chromosome[] Select() {
        Random rng = new Random();
        Chromosome[] parents = new Chromosome[2];

        int[] ranks = new int[_maxPopulation];

        int totalRanks = 0;
        for (int i = 0; i < _maxPopulation; i++) {
            ranks[i] = i;
            totalRanks += i;
        }

        // get 2 parents
        for (int i = 0; i < 2; i++) {
            int expectedSum = rng.nextInt(totalRanks);
            int currentSum = 0;
            for (int j = 0; j < _maxPopulation; j++) {
                currentSum += j;
                if (currentSum >= expectedSum) {
                    parents[i] = currentPopulation[j];
                    break;
                }
            }
        }

        return parents;
    }
}
