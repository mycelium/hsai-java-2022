package Distribution;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface Distribution {

     List<Double> generateData(int quantity);
}