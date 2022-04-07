import ReadFile.ReadFileImpl;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Run {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ReadFileImpl readFile;
        System.out.println("Введите файл ввода: ");
        String in = scanner.nextLine();
        System.out.println("Введите файл вывода или - : ");
        String out = scanner.nextLine();
        System.out.println("Введите через пробел символы для их подсчета: ");
        String symbols = scanner.nextLine();
        if (out.equals("-")) {
            readFile = new ReadFileImpl(new File(in), null, symbols);
        } else {
            readFile = new ReadFileImpl(new File(in), new File(out), symbols);
        }
        try {
            readFile.readFile();
            readFile.writeFile(readFile.dataManipulation());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
