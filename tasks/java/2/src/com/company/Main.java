package com.company;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static HashMap<String, Object> params = new HashMap<>(
            Map.of("type", Distribution.Normal, "cnt", 10000L, "out", "",
                    "params", new Float[0]));

    public static void main(String[] args) {
        HashMap<String, Object> params = Dialog.parseArgs(args);
        Dialog.showParams(params);
        Dialog.showResult(Generator.formOutputFile(params));
    }
}
