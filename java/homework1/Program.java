package homework1;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter input file path: ");
        String inFile = scanner.nextLine();
        System.out.println("Is output file needed? y/n: ");
        String ans = scanner.nextLine();
        FileOperation file;
        if (ans.equals("y")) {
            System.out.println("Enter output file path: ");
            String outFile = scanner.nextLine();
            file = new FileOperation(new File(inFile), new File(outFile));
        }
        else {
            if (!ans.equals("n")){
                System.out.println("Didn't understand your answer, assuming console output.");
            }
            file = new FileOperation(new File(inFile), null);
        }
        file.read();
        file.calculateWords();
        System.out.println("Do you want to calculate specific symbols? y/n: ");
        ans = scanner.nextLine();
        if(ans.equals("y")){
            System.out.println("Enter symbols (sep = ','): ");
            file.collectSymbols(
                Arrays.stream(
                    scanner.nextLine().split(",")
                ).filter(
                        (s) -> (s.length() > 0)
                ).collect(Collectors.toList())
            );
        }
        else if(!ans.equals("n")){
            System.out.println("Didn't understand your answer, assuming no.");
        }
        file.write();
    }
}