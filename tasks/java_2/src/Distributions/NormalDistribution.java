package Distributions;


public class NormalDistribution extends Distribution {
    private Double standDev;
    private Double mean;

    public NormalDistribution(Double std, Double mean) {
        this.standDev = std;
        this.mean = mean;
    }

    public Double getRnd() {
        return random.nextGaussian() * standDev + mean;
    }
}
