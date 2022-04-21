package distribution;

public class NormalDistributionFactory implements IDistributionFactory {
    private final double mean;
    private final double stddev;

    private NormalDistributionFactory(final double mean, final double stddev) {
        this.mean = mean;
        this.stddev = stddev;
    }

    public static NormalDistributionFactory of(final double mean, final double stddev) {
        return new NormalDistributionFactory(mean, stddev);
    }

    @Override
    public Distribution<Double> makeDistribution() {
        return NormalDistribution.of(mean, stddev);
    }
}
