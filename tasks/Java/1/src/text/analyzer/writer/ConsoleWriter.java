package text.analyzer.writer;

public class ConsoleWriter implements DataWriter {

    @Override
    public void write() {
        System.out.println();
    }

    @Override
    public void write(String text) {
        System.out.println(text);
    }
}
