import java.util.Map;
import java.io.*;

public class Writer {
    public static void writeData (String outp, String text)
            throws FileNotFoundException{

        StringBuilder result = createString(text);

        if (outp.contains(".txt")==true){
            try(FileWriter fw = new FileWriter(outp, false)) {
                fw.write(result.toString());
                fw.flush();
                fw.close();
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
        else {
            System.out.println(result);
        }
    }
    private static StringBuilder createString (String text) {

        int wordsCount = Analyzer.numberOfWords(text);
        int spacesCount = Analyzer.numberOfSpaces(text);
        Map<Integer, Integer> wordsDifferentLength = Analyzer.numberOfWordsDifferentLength(text);

        StringBuilder sb = new StringBuilder();
        sb.append("количество слов:  " + wordsCount + "\n")
                .append("количество пробелов:  " + spacesCount + "\n")
                .append("====================================="+ "\n");

        if (wordsDifferentLength != null) {
            for (Integer key : wordsDifferentLength.keySet()) {
                sb.append("длина: " + key + " - количество слов: " + wordsDifferentLength.get(key) + "\n");
            }
        }
        return sb;
    }

}
