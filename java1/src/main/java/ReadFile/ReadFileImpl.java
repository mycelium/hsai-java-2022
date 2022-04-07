package ReadFile;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReadFileImpl implements ReadFile {

    private File fileIn;
    private File fileOut;
    private StringBuilder data;
    private List<String> symbols;


    public ReadFileImpl(File fileIn, File fileOut, String symbols) {
        this.fileIn = fileIn;
        this.fileOut = fileOut;
        this.data = new StringBuilder();
        this.symbols = Arrays.stream(symbols.split(" ")).filter((s) -> (s.length() > 0)).collect(Collectors.toList());
    }

    @Override
    public void readFile() throws IOException {
        InputStreamReader in = new InputStreamReader(new FileInputStream(fileIn), "UTF-8");
        BufferedReader stream = new BufferedReader(in);
        String c;
        while (stream.ready()) {
            data.append(stream.readLine().replaceAll("[\\p{Punct}\\s&&[^\\h]]", "")
                    .replace("—", ""));
            data.append(" ");
        }

        stream.close();
    }

    @Override
    public StringBuilder dataManipulation() {

        StringBuilder sbOut = new StringBuilder();
        long spaces = data.chars().filter((c) -> c == (int) ' ').count();
        sbOut.append("Количество пробелов: ");
        sbOut.append(spaces - 1);
        List<String> words = Arrays.stream(data.toString().split(" "))
                .filter((s) -> s.matches("[a-zA-ZА-Яа-я]+"))
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
        sbOut.append("\nКоличество слов: ");
        sbOut.append(words.size());
        for (int i = 1; i <= words.get(words.size() - 1).length(); i++) {
            sbOut.append("\nКоличество слов из " + i + " букв: ");
            int finalI = i;
            sbOut.append(words.stream().filter((s) -> (s.length() == finalI)).count());
        }
        for (var el : symbols) {
            sbOut.append("\nКоличество символов " + el + " : ");
            sbOut.append(data.chars().filter((c) -> Character.toString(c).equals(el)).count());
        }
        return sbOut;

    }

    @Override
    public void writeFile(StringBuilder sbOut) throws IOException {
        if (this.fileOut == null) {
            System.out.println(sbOut.toString());
        } else {
            FileOutputStream out = new FileOutputStream(fileOut);
            Writer stream = new OutputStreamWriter(out);
            stream.write(sbOut.toString());
            stream.close();
        }
    }
}
