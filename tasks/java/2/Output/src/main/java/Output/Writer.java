package Output;

import java.util.List;
import java.util.logging.Logger;

public abstract class Writer {
    protected static final Logger log = Logger.getLogger(Writer.class.getName());
    protected String pathToFile;

    Writer(String path){pathToFile = path;}

    public abstract void write(List<Double> a);

}
