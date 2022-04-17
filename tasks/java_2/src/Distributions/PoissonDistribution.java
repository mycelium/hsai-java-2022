package Distributions;

public class PoissonDistribution extends Distribution {

    private Double mean;

    public PoissonDistribution(Double mean) {
        this.mean = mean;
    }

    public Double getRnd() {
        Double limit = Math.exp(-mean);
        Double prod = random.nextDouble();
        for (int i = 0; prod >= limit; i++) {
            prod *= random.nextDouble();
        }
        return prod;
    }
}
