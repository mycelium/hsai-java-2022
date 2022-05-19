package spbstu.hsai.consoleGenerator.generator;

public class PoissonGenerator extends Generator {

    private final double mean;

    public static PoissonGenerator getGenerator(double mean) throws IllegalArgumentException {
        if (mean <= 0.0) {
            throw new IllegalArgumentException("Mean must be greater than 0");
        }
        return new PoissonGenerator(mean);
    }

    private PoissonGenerator(double mean) {
        this.mean = mean;
    }

    public double getValue() {
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
