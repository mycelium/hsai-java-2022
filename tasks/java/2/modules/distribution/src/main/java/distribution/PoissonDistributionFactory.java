package distribution;

public class PoissonDistributionFactory implements IDistributionFactory {
    private final double lambda;

    private PoissonDistributionFactory(final double lambda) {
        if (lambda < 0) {
            throw new IllegalArgumentException("lambda should be greater than 0");
        }
        this.lambda = lambda;
    }

    public static PoissonDistributionFactory of(final double lambda) {
        return new PoissonDistributionFactory(lambda);
    }

    @Override
    public Distribution<Double> makeDistribution() {
        return PoissonDistribution.of(lambda);
    }
}
