package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeMap;

public class FileAnalyzer {

	private String readFile; //входной файл
	private File writeFile; //выходной файл
	private int numberOfWords = 0; //количество слов
	private long numberOfSpaces = 0; //количество пробелов
	private TreeMap<Integer, Integer> wordsLength = new TreeMap<>(); //ключ - длина слова, значение - количество слов данной длины
	private static final String notLetters = "[,.()!:;\"']"; //некоторые символы, не являющиеся буквами
	
	public FileAnalyzer(String read) {
		writeFile = null;
		readFile = read;
	}
	
	public FileAnalyzer(String read, String write) {
		readFile = read;
		writeFile = new File(write);
	}
	
	public void analyze() {
		try {
			String content = Files.readString(Paths.get(readFile)).replaceAll(notLetters, "");
			numberOfSpaces = content.chars().filter((x) -> x == (int)' ').count();
			String[] words = content.split("\\s");
			for(String currentWord : words) {
				if(!currentWord.isEmpty()) {
					numberOfWords++;
					int length = currentWord.length();
					Integer newValue = 1;
					if(wordsLength.containsKey(length)) {
						newValue = wordsLength.get(length) + 1;
					}
					wordsLength.put(length, newValue);
				}
			}
			} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void write() {
		try {
		OutputStream out;
		if(writeFile == null) {
			out = System.out;
		}
		else {
			out = new FileOutputStream(writeFile);
		}
		out.write(String.format("Number of words in file:%d\n", numberOfWords).getBytes());
		out.write(String.format("Number of spaces in file:%d\n", numberOfSpaces).getBytes());
		Set<Integer> setOfKeys = wordsLength.keySet();
		for(Integer i : setOfKeys) {
			out.write(String.format("Length:%d, number of words:%d\n", i, wordsLength.get(i)).getBytes());
		}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
