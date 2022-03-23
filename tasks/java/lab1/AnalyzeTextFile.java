package java.lab1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;

public class AnalyzeTextFile {
    public static void main(String[] args) {
        String import_path = "", export_path = "";
        if (args.length > 0) {
            import_path = args[0];
            if (args.length > 1)
                export_path = args[1];
        }
        else {
            System.out.println("Too few arguments!");
            System.exit(1);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(import_path))) {
            CharBuffer cb = CharBuffer.allocate(1024);
            int count = br.read(cb);
            System.out.println(count);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        }
    }
}
