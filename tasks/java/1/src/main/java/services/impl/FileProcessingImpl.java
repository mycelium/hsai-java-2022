package services.impl;

import services.FileProcessing;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FileProcessingImpl implements FileProcessing {
    private String[] readFileInBuffer(String pathString) {

        Path inputPath = Paths.get(pathString);

        String text = null;
        String[] symbols = new String[0];
        try (BufferedReader reader = Files.newBufferedReader(inputPath)) {
            text = reader.lines()
                    .collect(Collectors.joining(" "));
        }
        catch (IOException exception) {
            System.out.println("Ouch, IOException because: " + exception.getCause());
        }

        if (text == null) {
            System.out.println("Ouch, file is empty");
        } else {
            symbols = text.split("");
        }

        return symbols;
    }

//    private void consolePrint(Integer wordCounter, Integer spaceCounter, Map<Integer, Integer> lengthToCount) {
//        System.out.printf("Word count: %d%n", wordCounter);
//        System.out.printf("Space count: %d%n", spaceCounter);
//        for (Integer key : lengthToCount.keySet())
//        {
//            System.out.printf("Word length %d : count %d%n", key, lengthToCount.get(key));
//        }
//    }

    private void filePrint(Integer wordCounter, Integer spaceCounter, Map<Integer, Integer> lengthToCount, String outputPathString) throws IOException {
        PrintWriter out;
        if (outputPathString == null) {
            out = new PrintWriter(System.out);
        } else {
            out = new PrintWriter(new FileWriter(outputPathString));
        }

        out.printf("Word count: %d%n", wordCounter);
        out.printf("Space count: %d%n", spaceCounter);
        for (Integer key : lengthToCount.keySet())
        {
            out.printf("Word length %d : count %d%n", key, lengthToCount.get(key));
        }

        out.close();
    }

    private void superCounter(String[] symbols, String outputPathString) {
        Boolean prevSpace = true;
        Integer wordCounter = 0;
        Integer spaceCounter = 0;
        Integer currentWordLength = 0;
        Map<Integer, Integer> lengthToCount = new HashMap<>();

        for (String symbol : symbols) {
            if (!symbol.equals(" ")) {
                if (prevSpace) {
                    wordCounter++;
                    currentWordLength = 0;
                    prevSpace = false;
                }
                currentWordLength++;
            } else {
                if (!prevSpace) {
                    if (lengthToCount.containsKey(currentWordLength)) {
                        lengthToCount.put(currentWordLength, lengthToCount.get(currentWordLength) + 1);
                    } else {
                        lengthToCount.put(currentWordLength, 1);
                    }
                }
                prevSpace = true;
                spaceCounter++;
            }
        }

        if (currentWordLength != 0) { //block for case when EOF isn't space
            if (lengthToCount.containsKey(currentWordLength)) {
                lengthToCount.put(currentWordLength, lengthToCount.get(currentWordLength) + 1);
            } else {
                lengthToCount.put(currentWordLength, 1);
            }
        }

        try {
            filePrint(wordCounter, spaceCounter, lengthToCount, outputPathString);
        } catch (IOException exception) {
            System.out.println("Ouch, IOException because: " + exception.getCause());
        }
    }

    public void countInfo(String inputPathString) {
        String[] bufferedSymbols = readFileInBuffer(inputPathString);
        superCounter(bufferedSymbols, null);
    }

    public void countInfo(String inputPathString, String outputPathString) {
        String[] bufferedSymbols = readFileInBuffer(inputPathString);
        superCounter(bufferedSymbols, outputPathString);
    }
}
