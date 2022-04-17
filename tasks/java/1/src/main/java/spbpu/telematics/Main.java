package spbpu.telematics;

public class Main {
    public static void main(String[] args) {
        FileAnalysis analyzer = null;
        if (args.length == 1) {
            analyzer = new FileAnalysis(args[0], null);
        } else if (args.length == 2) {
            analyzer = new FileAnalysis(args[0], args[1]);
        } else {
            System.out.println("Передайте путь к существующему файлу");
        }

        if (analyzer != null) analyzer.startAnalysis();
    }
}
