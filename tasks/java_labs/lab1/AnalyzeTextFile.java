package java_labs.lab1;

import java.io.*;
import java.nio.CharBuffer;
import java.util.HashMap;

public class AnalyzeTextFile {
    private static CharBuffer buffer;
    private static String[] words;
    private static String import_path = "", export_path = "";
    private static boolean export_file = true;

    private static String readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(import_path))) {
            buffer = CharBuffer.allocate(((int) new File(import_path).length()));
            return String.valueOf(br.read(buffer)) + '\n';
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        }
        return "\n";
    }

    private static String countWords() {
        words = String.valueOf(buffer.array()).split("([\\W\\s]+)");
        return String.valueOf(words.length) + '\n';
    }

    private static String countSpaces() {
        int count = 0;
        for (char i : buffer.array())
            if (i == ' ')
                count++;
        return String.valueOf(count) + '\n';
    }

    private static String countWordLengths() {
        HashMap<Integer, Integer> words_lengths = new HashMap<>();
        for (String word : words) {
            if (!words_lengths.containsKey(word.length()))
                words_lengths.put(word.length(), 1);
            else
                words_lengths.put(word.length(), words_lengths.get(word.length()) + 1);
        }
        return words_lengths.toString() + '\n';
    }

    private static void writeOutput() {
        String output = readFile() + countWords() + countSpaces() + countWordLengths();

        Writer writer;
        if (export_file) {
            try {
                FileWriter fw = new FileWriter(export_path);
                writer = new BufferedWriter(fw);
            } catch (IOException e) {
                System.out.println("Invalid export path!");
                writer = new OutputStreamWriter(System.out);
            }
        } else
            writer = new OutputStreamWriter(System.out);

        BufferedWriter bw = new BufferedWriter(writer);
        try {
            bw.write(output);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            import_path = args[0];
            if (args.length > 1)
                export_path = args[1];
            else
                export_file = false;
        } else {
            System.out.println("Too few arguments!");
            System.exit(1);
        }

        writeOutput();
    }
}
