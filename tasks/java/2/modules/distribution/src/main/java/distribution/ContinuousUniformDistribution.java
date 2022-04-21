package distribution;

import java.util.Random;

public class ContinuousUniformDistribution implements Distribution<Double> {
    private static final Random random = new Random();
    private final double origin;
    private final double bound;

    private ContinuousUniformDistribution(final double origin, final double bound) {
        this.origin = origin;
        this.bound = bound;
    }

    public static ContinuousUniformDistribution of(double origin, double bound) {
        return new ContinuousUniformDistribution(origin, bound);
    }

    @Override
    public Double get() {
        return random.nextDouble(origin, bound);
    }
}
