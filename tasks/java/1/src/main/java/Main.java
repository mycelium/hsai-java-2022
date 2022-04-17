import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        FileAnalyzer analyzer = new FileAnalyzer();

        System.out.print("Input file path: ");
        while (!analyzer.setInputFile(sc.nextLine())) {
            System.out.println("File does not exist");
            System.out.print("Input file path: ");
        }

        System.out.print("Output file path (optional): ");
        analyzer.setOutputFile(sc.nextLine());
        System.out.print("String of symbols to count (optional): ");
        analyzer.setSymbolsToCount(sc.nextLine());
        System.out.println();

        analyzer.run();
    }
}
