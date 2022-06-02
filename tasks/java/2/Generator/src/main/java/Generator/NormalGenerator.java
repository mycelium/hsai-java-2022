package Generator;

public class NormalGenerator extends Generator {
    double v, u;

    public NormalGenerator(double mx, double s, int n) {
        super(n);
        this.v = mx;
        this.u = (s > 0) ? s : 1.0;
    }

    @Override
    public double genValue() {
        return v * random.nextGaussian() + u;
    }
}
