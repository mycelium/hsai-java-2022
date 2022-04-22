package dot.randomnumber;
import dot.randominterfaces.RandomInterfaces.RandomlyGeneratableArgumentsVisible;
import picocli.CommandLine.Option;
import java.util.Random;

public class RandomUniform implements RandomlyGeneratableArgumentsVisible{
    @Option(names = "-dist-u", description = "Uniform distribution", required = true)
    Boolean bRandomUniform;
    @Option(names = "-l-u", description = "Lowerbound")
    Double lowerBound=0.;
    @Option(names = "-u-u", description = "Upperbound")
    Double upperBound=1.;
    public RandomUniform(){};
    public RandomUniform(Double lowerBound, Double upperBound){
        this.lowerBound=lowerBound;
        this.upperBound=upperBound;
    };
    @Override
    public Double generate(){
        Random r=new Random();
        return r.nextDouble(lowerBound, upperBound);
    };
    @Override
    public String showArguments(){
        return "Lower bound:"+String.valueOf(lowerBound)+" and "+"Upper bound:"+String.valueOf(upperBound);
    }
}
