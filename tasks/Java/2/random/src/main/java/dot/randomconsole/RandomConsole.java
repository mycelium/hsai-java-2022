package dot.randomconsole;
import dot.randomnumber.*;
import dot.randomoutput.*;
import java.io.IOException;
import java.nio.file.Path;
import dot.randominterfaces.RandomInterfaces.*;
import dot.randomlogger.RandomLogger;
import picocli.CommandLine;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
@Command(name = "RandomGenerator", mixinStandardHelpOptions = true, version = "RandomGenerator 0.0",description = "Get random numbers of different distributions.")
public class  RandomConsole implements Runnable{

    @Option(names = {"-n","--number"}, description = "Number of random numbers to gernerate.",required = false)
    private Integer num=1000;
    @Option(names = {"-p","--path"}, description = "Path to store the results.")
    String path;
    @ArgGroup(exclusive =true, multiplicity = "1")
    RandomDistSelector randomDistSelector;
    static class RandomDistSelector implements RandomSelector<RandomlyGeneratableArgumentsVisible>{
        @ArgGroup(exclusive =false)
        RandomGaussian randomGaussian;
        @ArgGroup(exclusive =false)
        RandomPoisson randomPoisson;
        @ArgGroup(exclusive =false)
        RandomUniform randomUniform;
        @Override
        public RandomlyGeneratableArgumentsVisible select(){
            return randomGaussian!=null?randomGaussian:(randomPoisson!=null?randomPoisson:randomUniform);
        }
    }
    @ArgGroup(exclusive =true, multiplicity = "1")
    RandomOutputSelector randonOutputSelector;
    static class RandomOutputSelector implements RandomSelector<RandomStorablenArgumentsVisible<Double[]>>{
        @ArgGroup(exclusive =false)
        RandomOutputCSV randomOutputCSV;
        @ArgGroup(exclusive =false)
        RandomOutputSql randomOutoutSql;
        @Override
        public RandomStorablenArgumentsVisible<Double[]> select() {
            return randomOutoutSql!=null?randomOutoutSql:randomOutputCSV;
        }
    }
    @Override
    public void run(){
        RandomlyGeneratableArgumentsVisible randomGenerator = randomDistSelector.select();
        RandomStorablenArgumentsVisible<Double[]> randomStorage = randonOutputSelector.select();
        if(num<1000){
            System.out.print("Number of generated random numbers should not be under 1000!\n");
            return;
        }
        Double[] randoms = new Double[num];
        for(int i=0;i<num;i++){
            randoms[i]=randomGenerator.generate();
        }
        randomStorage.store(randoms,Path.of(path));
        try {
            RandomLogger.log(String.valueOf(num)+" "+"random numbers of "+randomGenerator.getClass().getSimpleName()+" with "+randomGenerator.showArguments()+ " and " + randomStorage.showArguments() + " to path:" + path.toString() +" generated.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Random numbers successfully created!\n");
    }
    public static void main(String args[]){
        new CommandLine(new RandomConsole()).execute(args);
    }
}
