import java.util.Random;

/**
 * Genetic algorithm class that utilizes Tournament Selection method
 */
public class TournamentGenetics extends Genetics {
    public TournamentGenetics(int maxPopulation, int maxGeneration, int mutationPercentage, double convergenceThreshold, int crossoverPoints) {
        super("Tournament", maxPopulation, maxGeneration, mutationPercentage, convergenceThreshold, crossoverPoints);
    }

    /**
     * Selects two parents by creating two fitness tournaments between quarter of the population and picks
     * a parent from each tournament to reproduce
     * @return new parents that are going to reproduce in the current generation
     */
    @Override
    protected Chromosome[] Select() {
        Random rng = new Random();
        Chromosome[] parents = new Chromosome[2];

        for (int i = 0; i < 2; i++) {
            int winner = 0;

            // quarter of the population joins the tournament
            for (int j = 0; j < _maxPopulation / 4; j++) {
                int ind = rng.nextInt(_maxPopulation);

                if(ind > winner) {
                    winner = ind;
                }
            }

            parents[i] = currentPopulation[winner];
        }

        return parents;
    }
}
