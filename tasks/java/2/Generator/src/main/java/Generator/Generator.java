package Generator;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class  Generator {
    protected static final Logger log = Logger.getLogger(Generator.class.getName());
    protected int numberOfValues;
    protected final Random random = new Random();

    Generator(int n){
        numberOfValues = n;
    }

    public abstract double genValue();

    public List<Double> genValues(){
        log.info("Data generation");
        return IntStream.range(0, numberOfValues)
                .mapToDouble(n -> this.genValue())
                .boxed().collect(Collectors.toList());
    };
}
