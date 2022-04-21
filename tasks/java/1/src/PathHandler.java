import java.nio.file.Path;
import java.util.Optional;

public class PathHandler {

    private Path inFile;

    public Path getInFile() {
        return inFile;
    }

    public void setInFile(String inFile) {
        try {
            this.inFile = Path.of(inFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Optional<Path> outFile = Optional.empty();

    public void setOutFile(String outFile) {
        this.outFile = Optional.of(Path.of(outFile));
    }

    public Optional<Path> getOutFile() {
        return outFile;
    }
}
