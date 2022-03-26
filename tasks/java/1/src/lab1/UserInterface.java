package lab1;

import java.io.IOException;
import java.util.Scanner;

public class UserInterface {

	public static void main(String[] args) {
		String pathForRead, pathForWrite;
		Scanner in = new Scanner(System.in);
		System.out.print("������� ���� � �������� �����:");
		pathForRead = in.next();
		System.out.print("������� ���� � ��������� �����\n\t(��� no, ���� �� �� �����):");
		pathForWrite = in.next();
		try {
			FileAnalyzer fileAnalyzer;
			if(pathForWrite.equals("no")) {
				fileAnalyzer = new FileAnalyzer(pathForRead);
			}
			else {
				fileAnalyzer = new FileAnalyzer(pathForRead, pathForWrite);
			}
			fileAnalyzer.analyze();
		} catch (IOException e) {
			System.out.println("������� ������ ������� ����. ���������� ������");
		} finally {
			in.close();
			System.out.println("������ ����� ��������");
		}
	}
}
