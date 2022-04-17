import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public  static  void  main (String[] args) throws IOException{
        Path path = Path.of(args[0]);
        String inputText = Files.readString(path);
        if (!Files.exists(path)) {
            System.out.println("File does not exist");
            return;
        }

        StringBuilder result = TextAnalysis.analyzeText(inputText);

        if(args.length == 2){
            Files.write(Path.of(args[1]), result.toString().getBytes(StandardCharsets.UTF_8));
        }
        else {
            System.out.println(result);
        }

    }
}
