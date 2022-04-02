package lab1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.TreeMap;

public class FileAnalyzer {

	private File readFile; //входной файл
	private File writeFile; //выходной файл
	private int numberOfWords = 0; //количество слов
	private int numberOfSpaces = 0; //количество пробелов
	private TreeMap<Integer, Integer> wordsLength = new TreeMap<>(); //ключ - длина слова, значение - количество слов данной длины
	private static final char notLetters[] = {',', '.', '(', ')', '!', ':', ';', '\"','\'', '\\', '/'}; //некоторые символы, не являющиеся буквами
	
	public FileAnalyzer(String read) {
		readFile = new File(read);
		writeFile = null;
	}
	
	public FileAnalyzer(String read, String write) {
		readFile = new File(read);
		writeFile = new File(write);
	}
	
	public void analyze() throws IOException {
		FileReader reader = new FileReader(readFile);
		try {
			for(int i = 0; i < readFile.length(); ) {
				Integer length = 0; //длина считываемого слова
				char currSymbol = (char)reader.read(); //текущий символ
				while(currSymbol != ' ') {
					if(isLetter(currSymbol)) { //если текущий символ не буква, то он не засчитывается
						length++;
					}
					i++;
					if(i >= readFile.length()) {
						numberOfSpaces--;//numberOfSpaces++ считает на один пробел больше, если файл не заканчивается на пробел
						break;
					}
					currSymbol = (char)reader.read();
				}
				i++;
				numberOfSpaces++;
				if(length != 0) {
					numberOfWords++;
					Integer newValue = 1;
					if(wordsLength.containsKey(length)) {
						newValue = wordsLength.get(length) + 1;
					}
					wordsLength.put(length, newValue);
				}
			}
			write();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
				reader.close();
		}
	}
	
	private static boolean isLetter(char symbol) {
		for(int i = 0; i < notLetters.length; i++) {
			if(notLetters[i] == symbol) {
				return false;
			}
		}
		return true;
	}
	
	public void write() throws IOException {
		OutputStream out;
		if(writeFile == null) {
			out = System.out;
		}
		else {
			out = new FileOutputStream(writeFile);
		}
		out.write(new String("Количество слов в файле:" + numberOfWords + "\n").getBytes());
		out.write(new String("Количество пробелов в файле:" + numberOfSpaces + "\n").getBytes());
		Set<Integer> setOfKeys = wordsLength.keySet();
		for(Integer i : setOfKeys) {
			out.write(new String("Длина:" + i + ", количество слов:" + wordsLength.get(i) + "\n").getBytes());
		}
	}
}
