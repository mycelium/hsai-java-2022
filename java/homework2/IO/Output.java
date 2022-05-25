package homework2.IO;

import homework2.distributions.Distribution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public interface Output {
    Logger log = Logger.getLogger(Distribution.class.getName());
    void write(ArrayList<Double> data) throws IOException;
}
