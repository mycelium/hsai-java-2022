import ReadFile.ReadFileImpl;

public class Run {
    public static void main(String[] args) {
        if (args.length < 2 || args.length > 3) {
            System.out.println("Требуются файл ввода и символы для подсчета");
        }
        ReadFileImpl readFile;
        if (args.length == 2) {
            readFile = new ReadFileImpl(args[0], null, args[1]);
        } else {
            readFile = new ReadFileImpl(args[0], args[1], args[2]);
        }
        readFile.readFile();
        readFile.writeFile(readFile.dataManipulation());

    }
}
