package Generator;

public class UniformGenerator extends Generator {
    double b, a;

    public UniformGenerator(double left, double right, int n) {
        super(n);
        if (left <= right) {
            a = left;
            b = right;
        } else {
            a = right;
            b = left;
        }
    }

    @Override
    public double genValue() {
        return random.nextDouble() * (b - a) + a;
    }

}
