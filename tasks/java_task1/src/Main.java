import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        String input_file = "";
        String output_file = "";

        Scanner user_input = new Scanner(System.in);
        boolean flag = false;
        while(!flag) {
            System.out.println("Введите полный путь до входного файла: ");
            input_file = user_input.nextLine();
            System.out.println("Введите полный путь до выходного файла: ");
            output_file = user_input.nextLine();

            flag = checkValue(input_file);
            if(flag==false)
                System.out.println("!!!!неверное имя файла!!!!");
        }

        String text = Reader.readFile(input_file);
        text = Analyzer.textCanonization(text);

        Writer.writeData(output_file, text);
    }
    private static boolean checkValue(String inp){

        if (inp.contains(".txt")==false || inp.equals("")==true)
            return false;
        return true;
    }
}
