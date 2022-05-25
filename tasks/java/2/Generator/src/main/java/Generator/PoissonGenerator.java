package Generator;

public class PoissonGenerator extends Generator {
    double mean;

    public PoissonGenerator(double lambda, int n) {
        super(n);
        if (lambda > 0)
            this.mean = lambda;
        else {
            //Logger log = LogManager.getLogger(PoissonGenerator.class);
            //log.warn("Lambda must be positive. Setting to 1.0 by default");
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
