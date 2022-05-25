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
