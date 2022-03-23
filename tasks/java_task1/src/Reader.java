import java.io.*;
import java.util.Scanner;

public class Reader {

    public static String readFile (String path)
            throws FileNotFoundException{
        File file = new File(path);
        Scanner sc = new Scanner(file);

        sc.useDelimiter("\\Z");

        String text = sc.next();
        return text;
    }

}