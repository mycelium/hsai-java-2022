import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("java 1 \n" +
                "This application reads file with text, prints text in the console, counts number of words and spaces. \n" +
                "Allowed digits in data:  . ! ; ? , : ) ( - ");

        Scanner in = new Scanner(System.in);
        System.out.print("Input a file path in commas. Example : \"C:\\Users\\veres\\IdeaProjects\\java1\\src\\data.txt\" \n");
        String path = in.nextLine();
        File file = new File(path);

        WordCounter wordCounter = new WordCounter(file);
        wordCounter.readFile();
        wordCounter.countTotalWords();
    }
}
