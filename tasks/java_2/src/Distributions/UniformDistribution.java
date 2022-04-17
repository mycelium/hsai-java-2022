package Distributions;

public class UniformDistribution extends Distribution {

    private Double leftBoundary;
    private Double rightBoundary;

    public UniformDistribution(Double lBound, double rBound) {
        this.leftBoundary = lBound;
        this.rightBoundary = rBound;
    }

    public Double getRnd(){
        return leftBoundary + random.nextDouble() * (rightBoundary - leftBoundary);
    }
}
