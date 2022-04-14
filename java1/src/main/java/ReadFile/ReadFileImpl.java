package ReadFile;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class ReadFileImpl implements ReadFile {

    private String fileIn;
    private String fileOut;
    private StringBuilder data;
    private List<String> symbols;


    public ReadFileImpl(String fileIn, String fileOut, String symbols) {
        this.fileIn = fileIn;
        this.fileOut = fileOut;
        this.data = new StringBuilder();
        this.symbols = Arrays.stream(symbols.split(",")).filter((s) -> (s.length() > 0)).collect(Collectors.toList());
    }

    @Override
    public void readFile() {
        try {
            data.append(Files.readString(Paths.get(this.fileIn))
                    .replaceAll("[\\p{Punct}]", "")
                    .replace("—", "")
                    .replace("\r\n", " "));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public StringBuilder dataManipulation() {

        StringBuilder sbOut = new StringBuilder();
        long spaces = data.chars().filter((c) -> c == (int) ' ').count();
        sbOut.append(String.format("Количество пробелов: %d", spaces));
        List<String> words = Arrays.stream(data.toString().split(" "))
                .filter((s) -> s.matches("[a-zA-ZА-Яа-я]+"))
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
        sbOut.append(String.format("\nКоличество слов: %d", words.size()));
        Map<Integer, List<String>> sizeWords = new TreeMap<>(words.stream()
                .collect(Collectors.groupingBy(String::length)));
        sizeWords.forEach((k, v) -> sbOut
                .append(String.format("\nКоличество из %d букв: %d", k, v.size())));
        symbols.forEach((s) -> sbOut
                .append(String.format("\nКоличество символов %s : %d",
                        s, StringUtils.countMatches(data.toString(), s))));
        return sbOut;

    }

    @Override
    public void writeFile(StringBuilder sbOut) {
        if (this.fileOut == null) {
            System.out.println(sbOut.toString());
        } else {
            try {
                Files.writeString(Path.of(this.fileOut), sbOut);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
