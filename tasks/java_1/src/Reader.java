import java.util.Scanner;

public class Reader {

    private static Scanner scanner;

    public Reader() {
        scanner = new Scanner(System.in);
    }

    public static String read() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        } else {
            return null;
        }
    }
}
