package text.analyzer.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import text.analyzer.bean.DataLoad;

public class DataLoadService {

    public DataLoad dataLoad() {
        Scanner in = new Scanner(System.in);
        DataLoad dataLoad = new DataLoad();

        dataLoad.setTextInput(loadTextInput(in));

        System.out.println("Do you want to log info to file?\n(Input 'yes' if you want)");
        if (in.nextLine().equals("yes")) {
            dataLoad.setFileForOutput(getFileForOutput(in));
        }


        System.out.println("Do you want count some symbols in file?\n(Input 'yes' if you want)");
        if (in.nextLine().equals("yes")) {
            dataLoad.setSymbolsForSearching(getSymbolsForSearching(in));
        }

        System.out.println();
        in.close();

        return dataLoad;
    }

    private List<String> loadTextInput(Scanner in) {
        System.out.println("Please, input path for file for analyzer:");
        String fileInput = in.nextLine();

        List<String> textInput = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileInput))) {
            String line;

            while ((line = br.readLine()) != null) {
                textInput.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error in input file! " + e.getMessage());
            System.exit(-1);
        }

        System.out.println();
        return textInput;
    }

    private String getFileForOutput(Scanner in) {
        System.out.println("Please, input path for file for output:");
        String fileForOutput = in.nextLine();

        System.out.println();
        return fileForOutput;
    }

    private Set<Character> getSymbolsForSearching(Scanner in) {
        System.out.println("Please, list the symbols separated by spaces (if symbols' length > 1, this symbol doesn't count)");

        Set<Character> symbols = Arrays
            .stream(in.nextLine().split(" +"))
            .filter(x -> !x.isEmpty())
            .filter(x -> x.length() == 1)
            .map(x -> x.charAt(0))
            .collect(Collectors.toSet());

        if (symbols.isEmpty()) {
            System.out.println("No symbols for searching!");
        }

        System.out.println();
        return symbols;
    }
}
