package Generator;

public class PoissonGenerator extends Generator {
    double mean;

    public PoissonGenerator(double lambda, int n) {
        super(n);
        if (lambda > 0)
            this.mean = lambda;
        else {
            this.mean = 1.0;
        }
    }
    @Override
    public double genValue() {
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do {
            p = p * random.nextDouble();
            ++k;
        } while (p > L);
        return k - 1;
    }
}
