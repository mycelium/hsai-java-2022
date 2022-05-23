package java1;

import java.io.IOException;
import java.nio.file.Files;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;


public class TextAnalyzer {
    String fileNameFrom;
    String fileNameTo;

    public TextAnalyzer(String from, String to){
        fileNameFrom = from;
        fileNameTo = to;
    }
    public TextAnalyzer(String from){
        fileNameFrom = from;
    }

    private static String readUsingFiles(String fileName) throws IOException  {
        String str = new String(Files.readAllBytes(Paths.get(fileName)));
        str = str.replaceAll("\n", " ");
        str = str.replaceAll("\r", "");
        return str;
    }

    private void writeUsingFiles(String text, Integer word,
                                 Integer spaces, Map<Integer, Integer> count)throws IOException{
        String textStr = "Text: " + String.valueOf(text) + "\n";
        String words = "Number of words in the text: " + String.valueOf(word) + "\n";
        String space = "Number of spaces in the text: " + String.valueOf(spaces) +"\n";
        String str = new String();
        Set<Integer> setCount = count.keySet();
        for (Integer i : setCount) {
            Integer numberLetter = count.get(i);
            str += "Number of words with the number of letters "+ String.valueOf(i) +": " + String.valueOf(numberLetter) + "\n";
        }

        try(FileWriter writer = new FileWriter(fileNameTo, false)) {
            writer.write(textStr + words + space + str);
            System.out.println("Data was written to a file.");
            writer.flush();
        } catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void textCounter() throws IOException {
        String textStr = readUsingFiles(this.fileNameFrom);
        //считаем пробелы
        int numberSpace = 0;
        for (int i = 0; i < textStr.length(); i++){
            if(textStr.charAt(i) == ' ')
                numberSpace += 1;
        }

        //считаем количество слов
        String[] arrStr = textStr.split(" ");
        for(int i = 0; i < arrStr.length; i++) {
            arrStr[i] = arrStr[i].replaceAll("\\w+\\.\\w+|[0-9]|[_]", "");
            arrStr[i] = arrStr[i].replaceAll("[^a-z^A-Z^a-я^А-Я]", "");
        }
        ArrayList<String> listStr = new ArrayList<String>(Arrays.asList(arrStr));
        listStr.removeIf(nextStr -> nextStr.equals(""));
        Integer numberWord = listStr.size();


        //считаем количесво слов из n букв
        int max = -1;
        for(String i: listStr){
            if(i.length() > max)
                max = i.length();
        }

        Map<Integer, Integer> counter = new HashMap<Integer, Integer>();
        for(int i = 0; i <= max; i++){
            int count = 0;
              for(String j : listStr){
                  if(i == j.length())
                      count += 1;
              }
            if(count != 0)
                counter.put(i,count);
        }
        if(this.fileNameTo != null){
            printRes(textStr, numberWord, numberSpace, counter);
            writeUsingFiles(textStr, numberWord, numberSpace, counter);
        }
        else
            printRes(textStr, numberWord, numberSpace, counter);
    }

    private void printRes(String text, Integer words, Integer spaces, Map<Integer, Integer> mapCount){
        System.out.println("Text: " + text);
        System.out.println("Number of words in the text: " + words);
        System.out.println("Number of spaces in the text: " + spaces);

        Set<Integer> setCount = mapCount.keySet();
        for (Integer i : setCount) {
            Integer numberLetter = mapCount.get(i);
            System.out.println("Number of words with the number of letters "+ i +": " + numberLetter);
        }
    }
}
