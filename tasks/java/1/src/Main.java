import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        FileProcessor fileProcessor = UI.newFileProcessor();
        if (fileProcessor.isTraced()) {
            System.out.println(fileProcessor.traceToFile());
        } else {
            UI.printReport(fileProcessor);
        }
    }
}
