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

    @Override
    public String genValues(){
        log.info("Values generation started");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < numberOfValues; i++) {
            stringBuilder.append((i + 1) + "," + Double.valueOf(genValue()).toString() + "\n");
        }
        log.info("Values generation finished");
        return stringBuilder.toString();
    };
}
