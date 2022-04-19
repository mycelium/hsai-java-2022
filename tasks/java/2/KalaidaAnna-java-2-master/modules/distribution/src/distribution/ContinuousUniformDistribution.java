package distribution;

import java.util.Random;

public class ContinuousUniformDistribution implements Distribution<Double> {

    static final String MESSAGE_ILLEGAL_BOUNDS = "illegal bounds";

    private final Random random;

    private final double origin;
    private final double bound;

    static public ContinuousUniformDistribution create(Random random, double origin, double bound) throws IllegalArgumentException {
        if (!(origin < bound && (bound - origin) < Double.POSITIVE_INFINITY)) {
            throw new IllegalArgumentException(MESSAGE_ILLEGAL_BOUNDS);
        }
        return new ContinuousUniformDistribution(random, origin, bound);
    }

    private ContinuousUniformDistribution(Random random, double origin, double bound) {
        this.random = random;
        this.origin = origin;
        this.bound = bound;
    }

    public Double get() {
        return random.nextDouble(origin, bound);
    }
}
