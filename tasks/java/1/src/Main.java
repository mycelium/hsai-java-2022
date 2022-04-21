import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Need, at least, 1 parameter - path to file");
            return;
        }
        StringBuilder results = new StringBuilder();
        PathHandler pathes = new PathHandler();
        pathes.setInFile(args[0]);
        if (args.length > 1) pathes.setOutFile(args[1]);

        Text text = new Text();
        text.setText(Files.readString(pathes.getInFile()));

        Tally tally = new Tally();

        tally.setSpaceCount(text.getText().split("[\\S]+").length);
        results.append("Whitespaces: ").append(tally.getSpaceCount()).append("\n");

        String[] words = text.getText().split("[^A-Za-zА-Яа-я]+");
        tally.setWordCount(words.length);
        results.append("Words: ").append(tally.getWordCount()).append("\n");

        Map<Integer, Integer> map = new HashMap<>();
        for (String word : words) {
            map.compute(word.length(), (k, v) -> (v == null) ? 1 : v + 1);
        }

        tally.setLengthCount(map);
        tally.getLengthCount().forEach((k, v) -> results.append("Length: ").append(k).append(" Count: ").append(v).append("\n"));

        System.out.println(results);

        pathes.getOutFile().ifPresentOrElse(x -> {
            try {
                Files.writeString(x, results);
                System.out.print("Saved in file " + x);
            } catch (IOException e) {
                e.getMessage();
            }
        }, () -> System.out.println("No out file path or wrong path"));
    }
}
