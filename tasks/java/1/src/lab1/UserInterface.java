package lab1;

import java.io.IOException;
import java.util.Scanner;

public class UserInterface {

	public static void main(String[] args) {
		FileAnalyzer analyzer;
		if(args.length == 0) {
			System.out.println("No file specified");
			return;
		}
		else if(args.length > 2) {
			System.out.println("Too many parameters specified");
			return;
		}
		else if (args.length == 1) {
			analyzer = new FileAnalyzer(args[0]);
		}
		else {
			analyzer = new FileAnalyzer(args[0], args[1]);
		}
		
		analyzer.analyze();
		analyzer.write();
		System.out.println("File analysis is completed");
	}
}
