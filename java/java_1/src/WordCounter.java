import java.io.*;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.exit;

public class WordCounter {

    static int countTotalWords = 0;
    static int countSpaces = 0; // подсчет пробелов. Примечание: если осуществляется переход на следующую строку в стихотворении - это также является пробелом.
    static int [] countWords = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    static String currentWord = " ";

    static File file;
    static Scanner sc;

    public WordCounter(String path) throws FileNotFoundException {
        file = new File(path);
        try {
            sc = new Scanner(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            System.out.println("File not found, sorry! Closing application...");
            exit(0);
        }
    }

    public static void readFile(String path){
        try(FileReader reader = new FileReader(path))
        {
            // читаем посимвольно
            System.out.println("\n 1. Print text for checking: ");
            int c;
            while((c=reader.read())!=-1){
                System.out.print((char)c);
                if ((char)c == ' ') // Если сюда подставить другой символ - будет считать количество заданных символов
                    countSpaces++;
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    public void countTotalWords() {
        do {
            currentWord = sc.next().trim();
            currentWord = currentWord.replaceAll("\\.|\\!|\\;|\\?|\\,|\\:|\\)|\\(|\\-|\\«|\\»,", ""); //удаляем знаки препинания
            countWords[currentWord.length()-1]++;
            countTotalWords++;
        } while(sc.hasNext());

        System.out.println("\n\n 2. Total number of words: " + countTotalWords);
        System.out.println("\n 3. Number of spaces (with moves to another line if in poetry): " + countSpaces);
        System.out.println("\n 4. Number of n-letter words:");
        for (int i = 0; i < countWords.length; i++)
        {
            if (countWords[i]!= 0)
                System.out.println(i+1 + " - letter word: " + countWords[i]);
        }
    }


}




