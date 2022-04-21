package distribution;

public class ContinuousUniformDistributionFactory implements IDistributionFactory {
    private final double origin;
    private final double bound;

    private ContinuousUniformDistributionFactory(final double origin, final double bound) {
        this.origin = origin;
        this.bound = bound;
    }

    public static ContinuousUniformDistributionFactory of(double origin, double bound) {
        return new ContinuousUniformDistributionFactory(origin, bound);
    }

    @Override
    public Distribution<Double> makeDistribution() {
        return ContinuousUniformDistribution.of(origin, bound);
    }
}
