public class Main {

    /*
    Input Arguments Example:
    type=normal/poisson/uniform (type of distribution)
    count=100622 (count of values)
    path=C:\ (path without filename to create file)
    parameter=2,9 (delimiter: ,) (parameters of distribution)
     */

    public static void main(String[] args) {
        System.out.println("********** PROCESS LOGGING INFORMATION **********");
        MainProcessor handler = new MainProcessor();
        handler.Start(args);
    }

    public static String purple = "\u001B[35m";
    public static String reset = "\u001B[0m";
    public static String red = "\u001B[31m";
    public static String green = "\u001B[32m";
}
