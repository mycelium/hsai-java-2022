package distribution;

public class PoissonDistribution implements Distribution<Double> {
    private final double lambda;

    private PoissonDistribution(final double lambda) {
        if (lambda < 0) {
            throw new IllegalArgumentException("lambda should be greater than 0");
        }
        this.lambda = lambda;
    }

    public static PoissonDistribution of(final double lambda) {
        return new PoissonDistribution(lambda);
    }

    @Override
    public Double get() {
        double L = Math.exp(-lambda);
        double p = 1.0;
        int k = 0;

        do {
            k++;
            p *= Math.random();
        } while (p > L);

        return (double) (k - 1);
    }
}
