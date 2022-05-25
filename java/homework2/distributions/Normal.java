package homework2.distributions;

import java.util.ArrayList;

public class Normal implements Distribution {
    private final double mu;
    private final double sigma;

    public Normal(double mu, double sigma){
        if (sigma < 0.0) {
            throw new IllegalArgumentException("Incorrect sigma!");
        }
        else {
            this.mu = mu;
            this.sigma = sigma;
        }
    }

    public ArrayList<Double> generate(int number){
        log.severe("Generating data...");
        for(int i = 0; i < number; i++){
            data.add(random.nextGaussian() * sigma + mu);
        }
        log.severe("Generation complete!");
        return data;
    }

}
