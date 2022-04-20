public class Main {

    /* Example of parameters use
    type=uniform/normal/poisson (type of distribution)
    cnt=133000 (cnt must be higher than 10000 or equals to 10000)
    path=C:\Users\User (directory for file creation)
    params=10,30 (delimiter ',' only)
     */

    public static String errorColor = "\u001B[31m";
    public static String infoColor = "\u001B[32m";
    public static String resetColor = "\u001B[0m";

    public static void main(String[] args) {
        Process p = new Process();
        p.doProcess(args);
    }
}
