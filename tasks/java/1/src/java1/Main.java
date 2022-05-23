package java1;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException{
        //from = "C:\\advanced_java\\advanced_java\\src\\java1\\resources\\text.txt";
        //to = "C:\\advanced_java\\advanced_java\\src\\java1\\resources\\result.txt";

        Scanner scanner = new Scanner(System.in);

        String fileNameFrom = "C:\\advanced_java\\advanced_java\\src\\java1\\resources\\text.txt";
        String fileNameTo = "C:\\advanced_java\\advanced_java\\src\\java1\\resources\\result.txt";

        //ввод пути к входному файлу (будет просить ввод, пока не будет введен существующий файл)
        System.out.print("Enter the path to the input file \n> ");
        fileNameFrom = scanner.nextLine();
        File fileFrom = new File(fileNameFrom);
        while (!(fileFrom.exists())) {
            System.out.print("> ");
            fileNameFrom = scanner.nextLine();
            fileFrom = new File(fileNameFrom);
        }

        //будет ли выходной файл (защита от неправильного ввода есть)
        System.out.println("Write the program results to the output file? (y/n)");
        String answer = "";
        do {
            System.out.print("> ");
            answer = scanner.nextLine();
        } while (!(answer.equals("y") || answer.equals("n")));


        if(answer.equals("y")){
            System.out.print("Enter the path to the output file \n> ");
            fileNameTo = scanner.nextLine();
            File fileTo = new File(fileNameTo);

            while (!(fileTo.exists())){
                System.out.print("> ");
                fileNameTo = scanner.nextLine();
                fileTo = new File(fileNameTo);
            }
        }

        TextAnalyzer text;
        if (fileNameTo.equals("")){
                text = new TextAnalyzer(fileNameFrom);
        }
        else {
                text = new TextAnalyzer(fileNameFrom, fileNameTo);
        }
        text.textCounter();
    }
}
