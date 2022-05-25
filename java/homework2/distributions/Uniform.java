package homework2.distributions;

import java.util.ArrayList;

public class Uniform implements Distribution {
    private final double a;
    private final double b;

    public Uniform(double a, double b){
        if (a > b) {
            throw new IllegalArgumentException("Incorrect bounds!");
        }
        else {
            this.a = a;
            this.b = b;
        }
    }

    public ArrayList<Double> generate(int number){
        log.severe("Generating data...");
        for(int i = 0; i < number; i++){
            data.add(a + random.nextDouble() * (b - a));
        }
        log.severe("Generation complete!");
        return data;
    }
}
