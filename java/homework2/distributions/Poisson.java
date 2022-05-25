package homework2.distributions;

import java.util.ArrayList;

public class Poisson implements Distribution {
    private final double mean;

    public Poisson(double mean){
        if (mean < 0.0) {
            throw new IllegalArgumentException("Incorrect mean value!");
        }
        else {
            this.mean = mean;
        }
    }

    private double getNumber() {
        double exp = Math.exp(-mean);
        double p = 1.0;
        int number = 0;
        do {
            number++;
            p *= random.nextDouble();
        } while (p > exp);
        return number - 1;
    }

    public ArrayList<Double> generate(int number){
        log.severe("Generating data...");
        for(int i = 0; i < number; i++){
            data.add(this.getNumber());
        }
        log.severe("Generation complete!");
        return data;
    }
}
