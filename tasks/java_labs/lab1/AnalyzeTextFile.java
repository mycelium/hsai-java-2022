package java_labs.lab1;

import java.io.*;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class AnalyzeTextFile {
    private static CharBuffer buffer;
    private static String[] words;
    private static HashMap<Integer, Integer> words_lengths;
    private static String import_path = "", export_path = "";
    private static boolean export_file;

    private static int readFile(){
        try (BufferedReader br = new BufferedReader(new FileReader(import_path))) {
            buffer = CharBuffer.allocate(((int) new File(import_path).length()));
            return br.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        }
        return -1;
    }

    private static int countWords(){
        words = String.valueOf(buffer.array()).split("([\\W\\s]+)");
        return words.length;
    }

    private static int countSpaces() {
        int count = 0;
        for (char i : buffer.array())
            if (i==' ')
                count++;
        return count;
    }

    private static String countWordLengths() {
        words_lengths = new HashMap<>();
        for (String word : words){
            if (!words_lengths.containsKey(word.length()))
                words_lengths.put(word.length(), 1);
            else
                words_lengths.put(word.length(), words_lengths.get(word.length())+1);
        }
        return words_lengths.toString();
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            import_path = args[0];
            if (args.length > 1)
                export_path = args[1];
            export_file = false;
        }
        else {
            System.out.println("Too few arguments!");
            System.exit(1);
        }

        try {
            FileWriter fw = new FileWriter(export_path);
        } catch (IOException e) {

            System.out.println("Invalid export path!");
        }
        System.out.println(readFile());
        System.out.println(countWords());
        System.out.println(countSpaces());
        System.out.println(countWordLengths());
    }
}
