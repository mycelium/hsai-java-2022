import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class UI {

    public static FileProcessor newFileProcessor() throws IOException {
        final HashMap<String, String> paths = getPaths();
        return new FileProcessor(paths.get("source"), paths.get("trace"), paths.get("chars"));
    }

    private static HashMap<String, String> getPaths() {
        System.out.println("If using relative path, then current directory is: " + System.getProperty("user.dir"));
        final Scanner scanner = new Scanner(System.in);
        final HashMap<String, String> response = new HashMap<>();
        System.out.print("Source file: ");
        response.put("source", scanner.nextLine());
        System.out.print("Trace file (leave blank to use console output): ");
        response.put("trace", scanner.nextLine());
        System.out.print("Process with character sequence "
                + "(input sequence like 'abc,?1s' without quotes, or leave blank): ");
        response.put("chars", scanner.nextLine());

        return response;
    }

    public static void printReport(FileProcessor fp) {
        System.out.println("\n\n" + fp.getReport());
    }
}
