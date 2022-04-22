package dot.randomnumber;
import dot.randominterfaces.RandomInterfaces.RandomlyGeneratableArgumentsVisible;
import picocli.CommandLine.Option;
import java.util.Random;

public class RandomPoisson implements RandomlyGeneratableArgumentsVisible{
    @Option(names = "-dist-p", description = "Distribution of Poisson", required = true)
    Boolean bRandomPoisson;
    @Option(names = "-e-p", description = "Expection value of distribution of Poisson")
    Double expect=0.5;
    public RandomPoisson(){};
    public RandomPoisson(Double expect){
        this.expect=expect;
    };
    //Knuth's algorithm of generating Poisson distribution samples O(expect). 
    @Override
    public Double generate(){
        Random r=new Random();
        Double L = Math.exp(-expect);
        Double p = 1.0;
        Double k = 0.;
        while(p>L){
            k+=1;
            p*=r.nextDouble();
        }
        return k;
    };
    @Override
    public String showArguments(){
        return "Expection:"+String.valueOf(expect);
    }
}
