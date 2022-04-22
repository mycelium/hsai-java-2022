package dot.randomnumber;
import dot.randominterfaces.RandomInterfaces.RandomlyGeneratableArgumentsVisible;
import picocli.CommandLine.Option;
import java.util.Random;

public class RandomGaussian implements RandomlyGeneratableArgumentsVisible{
    @Option(names = "-dist-g", description = "Distribution of Gaussian", required = true)
    Boolean bRandomGaussian;
    @Option(names = "-sd-g", description = "Standard deviation")
    Double standardDeviation=1.;
    @Option(names = "-e-g", description = "Expection value of distribution of Gaussian")
    Double expect=0.;
    public RandomGaussian(){};
    public RandomGaussian(Double standardDeviation, Double expect){
        this.standardDeviation=standardDeviation;
        this.expect=expect;
    };
    @Override
    public Double generate(){
        Random r=new Random();
        return r.nextGaussian()*standardDeviation+expect;
    };
    @Override
    public String showArguments(){
        return "Standard Deviation:"+String.valueOf(standardDeviation)+" and "+"Expection:"+String.valueOf(expect);
    }
}
