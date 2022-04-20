package com.company;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Dialog {

    public static HashMap<String, Object> parseArgs(String[] args) {
        HashMap<String, Object> params = new HashMap<>(
                Map.of("type", Distribution.Normal, "cnt", 10000L, "out", "",
                        "params", new Float[0]));

        if (args.length == 0) {
            System.out.println("Arguments required!");
            System.exit(-1);
        }
        Arrays.stream(args).forEach(arg -> {
            String argVal = arg.substring(arg.indexOf('=') + 1).replaceAll("\s", "")
                    .toLowerCase();
            switch(arg.substring(0, arg.indexOf('=')).replaceAll("\s","").toLowerCase()) {
                case "type":
                    switch (argVal) {
                        case "norm" -> params.replace("type", Distribution.Normal);
                        case "uni" -> params.replace("type", Distribution.Uniform);
                        case "pois" -> params.replace("type", Distribution.Poisson);
                        default -> System.out.println("Unknown distribution type! Normal distribution will be used.");
                    }
                    break;
                case "cnt":
                    try {
                        long tmp = Long.parseLong(argVal);
                        if (tmp > 10000L) { params.replace("cnt", tmp); }
                    } catch (NumberFormatException NFE) {
                        System.out.println("Invalid cnt number! Default (10000) will be used.");
                    }
                    break;
                case "out":
                    String path = argVal.replaceFirst("^[\\\\/]", "");
                    if (new File(path).exists()) { params.replace("out", path); }
                    else { System.out.println("Cannot find a path specified! Writing to a default location."); }
                    break;
                case "params":
                    try {
                        Float[] tmp = Arrays.stream(argVal.replaceAll("[\\[\\]\s]", "")
                                .split(",")).map(Float::parseFloat).toArray(Float[]::new);
                        if (((Distribution) params.get("type")).getParameterNumber() == 2) {
                            try {
                                params.replace("params", new Float[] {tmp[0], tmp[1]});
                            } catch (IndexOutOfBoundsException IOB) {
                                params.replace("params", new Float[] {tmp[0], 0f});
                            }
                        } else { params.replace("params", new Float[] {tmp[0], 0f}); }
                    } catch (NumberFormatException NFE) {
                        System.out.println("Invalid parameters for distribution!");
                        System.exit(-1);
                    }
                    break;
                default:
                    System.out.println("Invalid arguments!");
                    System.exit(-1);
            }
        });
        return params;
    }

    public static void showParams(HashMap<String, Object> params) {
        Distribution dist = (Distribution) params.get("type");
        Float[] pars = (Float[]) params.get("params");
        System.out.printf("Building %s distribution with the following parameters:\n%s = %f\n",
                dist.toString(), dist.getParameterNames()[0], pars[0]);
        if (dist.getParameterNumber() == 2) { System.out.printf("%s = %f\n", dist.getParameterNames()[1], pars[1]); }
    }

    public static void showResult(File result) {
        System.out.println("Results can be seen in file: " + result.getAbsolutePath());
    }
}
