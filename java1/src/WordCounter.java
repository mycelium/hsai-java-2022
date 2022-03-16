import java.io.*;
import java.util.Scanner;

public class WordCounter {

    static int countTotalWords = 0; // подсчет всех слов
    static int countSpaces = 0; // подсчет пробелов
    static int [] countWords = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}; // подсчет количества слов по количествеу букв
    static String currentWord = " ";

    static File file = new File("C:\\Users\\veres\\IdeaProjects\\java1\\src\\data.txt");  //по умолчанию
    static Scanner sc;

    static {
        try {
            sc = new Scanner(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public WordCounter() throws FileNotFoundException {
    }
    public WordCounter(File f) throws FileNotFoundException {
        file = f;
    }
    public static void readFile(){

        try(FileReader reader = new FileReader("C:\\Users\\veres\\IdeaProjects\\java1\\src\\data.txt"))
        {
            // читаем посимвольно
            System.out.println("\n 1. Print text for checking: ");
            int c;
            while((c=reader.read())!=-1){
                System.out.print((char)c);
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    public void countTotalWords() {

        do {
            //currentWord = sc.next();
            currentWord = sc.next().trim(); // избавляемся от пробелов
            currentWord = currentWord.replaceAll("\\.|\\!|\\;|\\?|\\,|\\:|\\)|\\(|\\-,", ""); //удаляем знаки препинания
            countWords[currentWord.length()-1]++;

            countTotalWords++;
        } while(sc.hasNext());

        countSpaces = countTotalWords -1;
        System.out.println("\n\n 2. Total number of words: " + countTotalWords);
        System.out.println("\n 3. Number of spaces: " + countSpaces);
        System.out.println("\n 4. Number of n-letter words:");
        System.out.println(" one-letter word: " + countWords[0]);
        System.out.println(" two-letter word: " + countWords[1]);
        System.out.println(" three-letter word: " + countWords[2]);
        System.out.println(" four-letter word: " + countWords[3]);
        System.out.println(" five-letter word: " + countWords[4]);
        System.out.println(" six-letter word: " + countWords[5]);
        System.out.println(" seven-letter word: " + countWords[6]);
        System.out.println(" eight-letter word: " + countWords[7]);
        System.out.println(" nine-letter word: " + countWords[8]);
        System.out.println(" ten-letter word: " + countWords[9]);
        System.out.println(" eleven-letter word: " + countWords[10]);
        System.out.println(" twelve-letter word: " + countWords[11]);
        System.out.println(" thirteen-letter word: " + countWords[11]);
        System.out.println(" fourteen-letter word: " + countWords[11]);
        System.out.println(" fifteen-letter word: " + countWords[11]);

    }
}




