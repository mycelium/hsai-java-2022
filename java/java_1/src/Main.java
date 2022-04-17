import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) throws IOException {

        String path = " ";

        System.out.println("java 1 \n" +
                "This application reads file with text, prints text in the console, counts number of words and spaces. \n" +
                "Allowed digits (punctuation marks) in data:  . ! ; ? , : ) ( - « » \n" +
                "Any other digits will count as words.");

        while (!path.contains("\\")&&!path.contains(".txt")) {
            Scanner in = new Scanner(System.in);
            System.out.print("\n Input a file path. Example : C:\\Users\\veres\\IdeaProjects\\test\\src\\data.txt \n");
            System.out.print("Only .txt format is allowed. Print Exit for exit. \n");
            path = in.nextLine();
            if (Objects.equals(path, "Exit")) exit(0);
            if (!path.contains("\\")&&!path.contains(".txt"))
                System.out.println(" Try again!");
        }

        WordCounter wordCounter = new WordCounter(path);
        wordCounter.readFile(path);
        wordCounter.countTotalWords();
    }

}
