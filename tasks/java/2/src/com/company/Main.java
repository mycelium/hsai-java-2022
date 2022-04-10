package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Integer.*;
import static java.lang.Float.*;
import static java.lang.Long.*;


public class Main {

    public static int getPoisson(double a) {
        Random rnd = new Random();
        double limit = Math.exp(-a);
        double prod = rnd.nextDouble();
        int n;
        for (n = 0; prod >= limit; n++)
            prod *= rnd.nextDouble();
        return n;
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Distribution (choose num):\n1 - Uniform distribution\n2 - Normal distribution\n3 - Poisson distribution\nOther - exit");
        int type;
        try{type = parseInt(scanner.nextLine());}
        catch(NumberFormatException e) {return;}
        float[] params = new float[2];
        switch (type){
            case(1):
                System.out.println("===Uniform distribution===");
                System.out.print("Min=");
                try{params[0] = parseFloat(scanner.nextLine());}
                catch(NumberFormatException e) {return;}
                System.out.print("Max=");
                try{params[1] = parseFloat(scanner.nextLine());}
                catch(NumberFormatException e) {return;}
                if (params[0]>params[1]) return;
                System.out.println();
                break;
            case(2):
                System.out.println("===Normal distribution===");
                System.out.print("Mean=");
                try{params[0] = parseFloat(scanner.nextLine());}
                catch(NumberFormatException e) {return;}
                System.out.print("St.dev=");
                try{params[1] = parseFloat(scanner.nextLine());}
                catch(NumberFormatException e) {return;}
                System.out.println();
                break;
            case(3):
                System.out.println("===Poisson distribution===");
                System.out.print("Mean=");
                try{params[0] = parseFloat(scanner.nextLine());}
                catch(NumberFormatException e) {return;}
                System.out.println();
                break;
            default:
                return;
        }
        long count;
        System.out.print("Count=");
        try{count = parseLong(scanner.nextLine());}
        catch(NumberFormatException e) {return;}
        /* output in csv only*/
        System.out.print("Output directory: ");
        String output = scanner.nextLine();
        System.out.println();

        FileWriter csvWriter = new FileWriter(output+"/result.csv");
        csvWriter.append("rnd_data\n");
        double buff;
        switch (type) {
            case (1):
                for (long i = 0; i < count; i++) {
                    buff = params[0] + Math.random() * (params[1]-params[0]);
                    csvWriter.append(buff+"\n");
                }
                break;
            case (2):
                for (long i = 0; i < count; i++) {
                    Random rnd = new Random();
                    buff = (rnd.nextGaussian())*params[1]+params[0];
                    csvWriter.append(buff+"\n");
                }
                break;
            case (3):
                for (long i = 0; i < count; i++) {
                    buff = getPoisson(params[0]);
                    csvWriter.append(buff+"\n");
                }
                break;
        }
        csvWriter.flush();
        csvWriter.close();

        System.out.print("Your result in: "+output+"/result.csv");
    }
}
