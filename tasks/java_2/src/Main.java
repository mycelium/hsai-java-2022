public class Main {
    //input example : dist=u|n|p params=1,2 cnt=10000 [out=c:\...]

    public static char type = 'u';
    public static Double[] params;
    public static int cnt = 10000;
    public static String out = "";

    public static void main(String[] args) {

        ArgsParser.parseArgs(args);

        Generator gen = new Generator();
        try {
            StringBuilder data = gen.generateData();
            FileHandler handler = new FileHandler();
            handler.createCSV(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}