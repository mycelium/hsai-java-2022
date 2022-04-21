package io.parameters;

import java.util.HashMap;

public class Arguments extends HashMap<String, String> {
    public static Arguments fromArray(String[] arguments) {
        Arguments res = new Arguments();
        if (arguments.length % 2 != 0) {
            throw new IllegalArgumentException("Array should be even");
        }

        for (int i = 0; i < arguments.length; i += 2) {
            res.put(arguments[i], arguments[i + 1]);
        }

        return res;
    }
}
