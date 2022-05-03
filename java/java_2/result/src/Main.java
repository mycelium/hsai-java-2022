import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

/* Program that generates data
 * Distributions: normal, poisson, uniform
 * Parameters: average,dev for normal; lambda for poisson; a,b for uniform
 * Amount of output data: min 10000, max 100000
 * Output: CSV
 * */

public class Main {

    private static final Logger log = Logger.getLogger(Main.class.getName());
    static String path = "";
    static int size = 0;
    static int par1, par2;
    static int n = 2;
    static int dataFormat = 1;

    static ArrayList<Double> list;

    public static void printMenu() {
        System.out.println("Print number\n");
        System.out.println("1 - Data with normal distribution");
        System.out.println("2 - Data with poisson distribution");
        System.out.println("3 - Data with uniform distribution");
        Scanner in = new Scanner(System.in);
        do {
            n = in.nextInt();
        } while (n != 1 && n != 2 && n != 3);

        System.out.println("Print format for output: 1 - csv /  2 - database\n");
        do {
            in = new Scanner(System.in);
            dataFormat = in.nextInt();
            if (dataFormat != 1 && dataFormat != 2) System.out.println("Again!\n");
        } while (dataFormat != 1 && dataFormat != 2);

        System.out.println("Print output path. Example: C:\\Users\\veres\\IdeaProjects\\java_2\n");
        in = new Scanner(System.in);
        path = in.next();

    }

    public static void printSMenu(String a, String b) {
        System.out.println("Please input data value, more than 10 000 and less than 100 000: ");
        Scanner in = new Scanner(System.in);
        do {
            size = in.nextInt();
            if (size < 1 || size > 100000) System.out.println("Again! more than 10 000 and less than 100 000:\n");
        } while (size < 1|| size > 100000);
        System.out.println("Please, input parameters " + a + " value: ");
        par1 = in.nextInt();
        if (!Objects.equals(b, " ")) {
            System.out.println("Please, input parameters " + b + " value: ");
            par2 = in.nextInt();
        }
    }

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        log.info("Application has started");
        System.out.println("Welcome to Java-2" +
                "This program generates data ");
        printMenu();

        switch (n) {
            case 1 -> {
                //normal
                printSMenu("u", "v");
                NormalDistribution n = new NormalDistribution(par1, par2, size);
                list = n.getNormalDistribution();
                break;
            }
            case 2 -> {
                //poisson
                printSMenu("lambda", " ");
                PoissonDistribution p = new PoissonDistribution(par1, size);
                list = p.getPoissonDistribution();
                break;
            }
            case 3 -> {
                //uniform
                printSMenu("a", "b");
                UniformDistribution u = new UniformDistribution(par1, par2, size);
                list = u.getUniformDistribution();
                break;
            }
        }

        if (dataFormat == 1) {
            OutputCSV out = new OutputCSV();
            out.outputCSV(size, list, path);
        } else if (dataFormat == 2){
            OutputDatabase out = new OutputDatabase();
            out.outputDatabase(list);
        } else {
            System.out.println("Wrong data format! Closing application..");
            log.info("Application has finished. Data hasn't been stored.");
        }
    }
}
