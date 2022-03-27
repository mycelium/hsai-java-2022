import java.io.*;
import java.util.*;

public class Main {

    public static void main(String args[]) {
        // Using Scanner for Getting Input from User
        Scanner in = new Scanner(System.in);

        System.out.println("Enter path to input file:");
        String inputFile = in.nextLine();

        System.out.println("Enter path to output file or print \"-\":");
        String outputFile = in.nextLine();


        try (
                InputStream inputStream = new FileInputStream(inputFile);
                Scanner scanner = new Scanner(inputStream, "Cp1252"); //ANSI

                FileWriter fileWriter = new FileWriter(outputFile, true);
        ) {
                scanner.useDelimiter("\\Z");
                String text = scanner.next();

                // count words
                int wordsNumber = TextAnalysis.countWordsUsingSplit(text);
                // count spaces
                int spaces = TextAnalysis.countSpaces(text);

                // count word length with occurrence
                String s = text.replaceAll("[.:;,#?!()/]","");
                String[] words = s.split(" ");
                List<String> targetList = Arrays.asList(words);
                Map<Integer, Integer> wordSizeOccurance = TextAnalysis.countWordLengthOccurrence(targetList);

                // count the occurrence of each character in a text
                List<String> characterOccurrence = TextAnalysis.characterCount(text.toCharArray());

                // output to console or write to file
                if (outputFile.equals("-")) {
                    System.out.println("Number of words:");
                    System.out.println(wordsNumber);
                    System.out.println("Number of spaces: ");
                    System.out.println(spaces);
                    System.out.println("Word length with occurrence: ");
                    System.out.println(wordSizeOccurance);
                    System.out.println("No. of each character: ");
                    System.out.println(characterOccurrence);
                }
                else {
                    BufferedWriter writer = new BufferedWriter(fileWriter);
                    writer.write("Number of words: ");
                    writer.write(String.valueOf(wordsNumber));
                    writer.write(System.lineSeparator());
                    writer.write("Number of spaces: ");
                    writer.write(String.valueOf(spaces));
                    writer.write(System.lineSeparator());
                    writer.write("Word length with occurrence: ");
                    writer.write(String.valueOf(wordSizeOccurance));
                    writer.write(System.lineSeparator());
                    writer.write("No. of each character: ");
                    writer.write(String.valueOf(TextAnalysis.characterCount(text.toCharArray())));
                    writer.close();
                }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}