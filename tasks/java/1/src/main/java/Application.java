import services.FileProcessing;
import services.impl.FileProcessingImpl;

public class Application {
    public static void main(String[] args) {
//        String inputPathString = "C:\\Users\\skyli\\Desktop\\asd.txt";

        FileProcessing fileProcessing = new FileProcessingImpl();
        fileProcessing.countInfo(args[0]);
    }
}