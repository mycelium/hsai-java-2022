package org.polytech.Output;

import java.io.IOException;
import java.util.List;

public interface Output {

    void save(List<Double> list) throws IOException;
}
