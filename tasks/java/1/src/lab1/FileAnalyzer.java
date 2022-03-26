package lab1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.TreeMap;

public class FileAnalyzer {

	private File readFile; //������� ����
	private File writeFile; //�������� ����
	private int numberOfWords = 0; //���������� ����
	private int numberOfSpaces = 0; //���������� ��������
	private TreeMap<Integer, Integer> wordsLength = new TreeMap<>(); //���� - ����� �����, �������� - ���������� ���� ������ �����
	private static final char notLetters[] = {',', '.', '(', ')', '!', ':', ';', '\"','\'', '\\', '/'}; //��������� �������, �� ���������� �������
	
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
				Integer length = 0; //����� ������������ �����
				char currSymbol = (char)reader.read(); //������� ������
				while(currSymbol != ' ') {
					if(isLetter(currSymbol)) { //���� ������� ������ �� �����, �� �� �� �������������
						length++;
					}
					i++;
					if(i >= readFile.length()) {
						numberOfSpaces--;//numberOfSpaces++ ������� �� ���� ������ ������, ���� ���� �� ������������� �� ������
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
		out.write(new String("���������� ���� � �����:" + numberOfWords + "\n").getBytes());
		out.write(new String("���������� �������� � �����:" + numberOfSpaces + "\n").getBytes());
		Set<Integer> setOfKeys = wordsLength.keySet();
		for(Integer i : setOfKeys) {
			out.write(new String("�����:" + i + ", ���������� ����:" + wordsLength.get(i) + "\n").getBytes());
		}
	}
}
