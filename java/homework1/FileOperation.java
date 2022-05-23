package homework1;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FileOperation {

    private final File inFile;
    private final File outFile;
    private final StringBuilder inData;
    private final StringBuilder outData;

    public FileOperation(File inFile, File outFile) {
        this.inFile = inFile;
        this.outFile = outFile;
        this.inData = new StringBuilder();
        this.outData = new StringBuilder();
    }

    public void read() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inFile));
        String line;
        while ((line = reader.readLine()) != null) {
            inData.append(line.replaceAll(
                    "\\p{Punct}|\\d", ""
            ).trim().replaceAll(
                    " +", " ")
            ).append(" ");
        }
        reader.close();
    }

    public void collectSymbols(List<String> symbols){
        for (var symbol : symbols) {
            outData.append(
                "\nTotal number of symbols "
            ).append(symbol).append(": ").append(
                    inData.chars().filter((s) -> Character.toString(s).equals(symbol)).count()
            );
        }
    }

    public void calculateWords() {
        List<String> words = Arrays.stream(
                    inData.toString().split(" ")
                ).sorted(
                        Comparator.comparingInt(String::length)
                ).collect(Collectors.toList());
        outData.append("\nTotal number of words: ");
        if (words.get(words.size() - 1).length() == 0){
            outData.append("0\n");
        } else {
            outData.append(words.size()).append("\n");
        }
        for (int i = 1; i <= words.get(words.size() - 1).length(); i++) {
            int finalI = i;
            long numb_words = words.stream().filter((s) -> (s.length() == finalI)).count();
            if (numb_words > 0) {
                outData.append(" - ").append(numb_words).append(
                        " word(s) containing "
                ).append(i).append(" letter(s); \n");
            }
        }

        outData.append(
                "Total number of spaces: "
        ).append(
                inData.chars().filter((s) -> Character.toString(s).equals(" ")).count()
        );

    }
    
    public void write() throws IOException {
        if (this.outFile != null) {
            Writer writer = new OutputStreamWriter(new FileOutputStream(outFile));
            writer.write(outData.toString());
            writer.close();
        } else {
            System.out.println(inData);
            System.out.println(outData);
        }
    }

}