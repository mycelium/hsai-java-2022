package io.output;

import java.io.Closeable;
import java.util.function.Consumer;

public interface OutputWriter extends Consumer<Double>, Closeable {
}
