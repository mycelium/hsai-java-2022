package spbstu.hsai.consoleGenerator.generator;

public class UniformGenerator extends Generator {

    private final double a;
    private final double b;

    static public UniformGenerator getGenerator(double a, double b) throws IllegalArgumentException {
        if (!(a < b && (b - a) < Double.POSITIVE_INFINITY)) {
            throw new IllegalArgumentException("Invalid bounds for uniform distribution");
        }
        return new UniformGenerator(a, b);
    }

    private UniformGenerator(double origin, double bound) {
        this.a = origin;
        this.b = bound;
    }

    public double getValue() {
        return a + random.nextDouble() * (b - a);
    }
}
