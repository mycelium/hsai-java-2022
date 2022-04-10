import java.util.*;

public class Analyzer {
    //Поиск слов путём разделения текста
    public static List<String> splitText(String s) {
        String[] text_splitted = s.split("[\\s,!?.;:]+");
        List<String> words = new ArrayList<>();
        for (int i = 0; i < text_splitted.length; i++) {
            if (text_splitted[i].matches("[a-zA-Z]+"))
            {
                words.add(text_splitted[i]);
            }
        }
        return words;
    }

    //Поиск пробелов в тексте
    public static int countSpaces(String text) {
        int spaceCount = 0;
        int i = 0;
        while (i < text.length()){
            char ch = text.charAt(i);
            if (ch == ' '){
                spaceCount++;
            }
            i++;
        }
        return spaceCount;
    }

    //Создание словаря, где в качестве ключа - длина слова, в качестве значения - кол-во слов с такой длиной
    public static Map<Integer, Integer> wordSizeMap(List<String> words) {
        Map<Integer, Integer> wordslength = new HashMap<Integer, Integer>();

        for (int i = 0; i < words.size(); i++) {
            int len = words.get(i).length();
            if (wordslength.containsKey(len)) {
                wordslength.put(len, wordslength.get(len) + 1);
            }
            else {
                wordslength.put(len, 1);
            }
        }
        return wordslength;
    }

    //Создание словаря, где в качестве ключа - символ, в качестве значения - кол-во появлений этого символа
    public static Map<Character, Integer> symbolsMap(String text) {
        Map<Character, Integer> symbols = new HashMap<Character, Integer>();
        char[] textArray = text.toCharArray();

        for (int i = 0; i < textArray.length; i++) {
            if (symbols.containsKey(textArray[i])) {
                symbols.put(textArray[i], symbols.get(textArray[i]) + 1);
            }
            else {
                symbols.put(textArray[i], 1);
            }
        }
        return symbols;
    }
}