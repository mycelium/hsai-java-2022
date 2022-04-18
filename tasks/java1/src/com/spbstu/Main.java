package com.spbstu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws Exception {
        Optional<Path> out = Optional.empty();

        if (args.length == 0) {
            System.out.println("Input is empty");
        }

        Path in = Path.of(args[0]);
        if (args.length == 2) {
            out = Optional.of(Path.of(args[1]));
        }

        String input = Files.readString(in);
        StringBuilder sb = TextAnalyzer.analyze(input).build();

        out.ifPresentOrElse( p-> {
            try {
                Files.writeString(p, sb);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, () -> System.out.println(sb));

    }
}
