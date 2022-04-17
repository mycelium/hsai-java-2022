public class Main {
    //input example : dist=u|n|p params=1,2 cnt=10000 [out=c:\...]

    public static char type = 'u';
    public static Float[] params;
    public static int cnt = 10000;
    public static String out = "";

    public static void main(String[] args) {
        ArgsParser.parseArgs(args);
    }
}
