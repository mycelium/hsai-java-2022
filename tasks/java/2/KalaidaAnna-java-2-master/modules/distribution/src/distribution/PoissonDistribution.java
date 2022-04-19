package distribution;

import java.util.Random;

public class PoissonDistribution implements Distribution<Integer> {

    static final String MESSAGE_MEAN_NON_NEGATIVE = "mean should be non negative";

    private final Random random;

    private final double mean;

    public static PoissonDistribution create(Random random, double mean) throws IllegalArgumentException {
        if (mean <= 0) {
            throw new IllegalArgumentException(MESSAGE_MEAN_NON_NEGATIVE);
        }
        return new PoissonDistribution(random, mean);
    }

    private PoissonDistribution(Random random, double mean) {
        this.random = random;
        this.mean = mean;
    }

    @Override
    public Integer get() {
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do {
            p = p * random.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }
}
