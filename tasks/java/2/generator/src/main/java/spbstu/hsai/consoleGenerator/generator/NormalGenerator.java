package spbstu.hsai.consoleGenerator.generator;

public class NormalGenerator extends Generator {

    private final double mean;
    private final double stddev;

    public static NormalGenerator getGenerator(double mean, double stddev) throws IllegalArgumentException {
        if (stddev < 0.0) {
            throw new IllegalArgumentException("Standard deviation must be greater than 0");
        }
        return new NormalGenerator(mean, stddev);
    }

    private NormalGenerator(double mean, double stddev) {
        this.mean = mean;
        this.stddev = stddev;
    }

    public double getValue() {
        return random.nextGaussian() * stddev + mean;
    }
}
