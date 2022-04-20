package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Generator {

    public static File formOutputFile(HashMap<String, Object> params) {
        String path = (String) params.get("out");
        File file = new File((path.matches(".+[/\\\\]") || "".equals(path) ? path : path + "/") + "result.csv");
        try {
            AtomicBoolean running = new AtomicBoolean(false);
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            Thread progress = new Thread(() -> {
                running.set(true);
                while (running.get()) {
                    System.out.print("=^._.^= ∫ -meow ");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.print("Work in progress \n");
            progress.start();
            fw.append(getData(params));
            fw.close();
            Thread.sleep(200);
            running.set(false);
            System.out.println("\n/ᐠ ̥ ̮  ̥ ᐟ\\ฅ Completed successfully!");
        } catch (IOException IOE) {
            System.out.println("/ᐠ｡‸｡ᐟ\\ An error occurred while managing the output file.");
            System.exit(-1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return file;
    }

    private static String getData(HashMap<String, Object> params) {
        Random rnd = new Random();
        StringBuilder data = new StringBuilder();
        Float[] pars = (Float[]) params.get("params");

        for (long  i = 0; i < (long) params.get("cnt"); i++) {

            double tmp = 0;
            switch (((Distribution)params.get("type")).getType()) {
                case (0):
                    //tmp = pars[0] + rnd.nextDouble(pars[1] - pars[0] + 1); doesn't work, probably due to some version problem I can't figure out
                    tmp = pars[0] + rnd.nextDouble() * (pars[1] - pars[0]);
                    break;
                case (1):
                    tmp = rnd.nextGaussian() * pars[1] + pars[0];
                    break;
                case (2):
                    double lim = Math.exp(-pars[0]);
                    int n;
                    double prod = rnd.nextDouble();
                    for (n = 0; prod >= lim; n++)
                        prod *= rnd.nextDouble();
                    tmp = n;
                    break;
            }
            data.append(tmp).append("\n");
        }
        return data.toString();
    }
}
