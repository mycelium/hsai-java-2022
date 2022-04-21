package distribution;

import java.util.Random;
import java.util.function.Supplier;

public interface Distribution<T extends Number> extends Supplier<T> {
}
