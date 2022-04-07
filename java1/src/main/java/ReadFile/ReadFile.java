package ReadFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface ReadFile {

    void readFile() throws  IOException;

    void writeFile(StringBuilder sbOut) throws IOException;

    StringBuilder dataManipulation();
}
