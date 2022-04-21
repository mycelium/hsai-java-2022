package distribution;

import java.util.Random;

public class NormalDistribution implements Distribution<Double> {
    private static final Random random = new Random();

    private final double mean;
    private final double stddev;

    private NormalDistribution(final double mean, final double stddev) {
        this.mean = mean;
        this.stddev = stddev;
    }

    public static NormalDistribution of(final double mean, final double stddev) {
        return new NormalDistribution(mean, stddev);
    }

    @Override
    public Double get() {
        return random.nextGaussian(mean, stddev);
    }
}
