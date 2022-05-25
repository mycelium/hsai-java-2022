import services.FileProcessing;
import services.impl.FileProcessingImpl;

import java.util.Arrays;

public class Application {
    public static void main(String[] args) {
//        String inputPathString = "C:\\Users\\skyli\\Desktop\\asd.txt";

        FileProcessing fileProcessing = new FileProcessingImpl();

        Integer parametersNumber = args.length;
        switch (parametersNumber) {
            case 0:
                System.out.println("You must input at least one argument!");
            case 1:
                fileProcessing.countInfo(args[0]);
                break;
            case 2:
                fileProcessing.countInfo(args[0], args[1]);
                break;
            default:
                System.out.println("You must input max 2 arguments!"); //for extra task
//                fileProcessing.countInfo(args[0], args[1], Arrays.copyOfRange(args, 2, parametersNumber));
        }

    }
}