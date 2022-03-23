import java.util.*;

public class Analyzer {

    public static String textCanonization(String txt){
        return txt.replaceAll("\\pP", "");
    }

    public static int numberOfWords (String txt){
        return txt.split("[\\d\\s]+").length;
    }

    public static int numberOfSpaces(String txt){
        return txt.split(" ").length;
    }

    public static Map<Integer, Integer> numberOfWordsDifferentLength (String txt){
        Map<Integer, Integer> countWords = new HashMap<Integer, Integer>();
        String[] words = txt.split("[\\d\\s]+");

        for (int i=0; i<words.length; i++){
            if(countWords.containsKey(words[i].length()))
                countWords.put(words[i].length(), countWords.get(words[i].length())+1);
            else
                countWords.put(words[i].length(), 1);
        }

        return countWords ;
    }

}
