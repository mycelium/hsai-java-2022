package distribution;

import java.util.Random;

public class NormalDistribution implements Distribution<Double> {

    static String MESSAGE_STDDEV_NON_NEGATIVE = "standard deviation must be non-negative";

    private final Random random;

    private final double mean;
    private final double stddev;

    public static NormalDistribution create(Random random, double mean, double stddev) throws IllegalArgumentException {
        if (stddev < 0.0) {
            throw new IllegalArgumentException(MESSAGE_STDDEV_NON_NEGATIVE);
        }

        return new NormalDistribution(random, mean, stddev);
    }

    private NormalDistribution(Random random, double mean, double stddev) {
        this.random = random;
        this.mean = mean;
        this.stddev = stddev;
    }

    @Override
    public Double get() {
        return random.nextGaussian(mean, stddev);
    }

}
