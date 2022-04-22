package dot.randomoutput;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import dot.randominterfaces.RandomInterfaces.RandomStorablenArgumentsVisible;
import picocli.CommandLine.Option;

public class RandomOutputCSV implements RandomStorablenArgumentsVisible<Double[]>{
    @Option(names = "-csv", required = true, description = "Store the results in the CSV format file.")
    Boolean bRandomOutputCSV;
    @Override
    public void store(Double[] data, Path path){
        StringBuilder sb = new StringBuilder();
        for (Double random : data) {
            sb.append(String.valueOf(random)+"\n");
        }
        try{
            OutputStream out=new FileOutputStream(path.toString(), true);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            writer.write(sb.toString());
            writer.close();
            out.close();
        }
        catch(IOException e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    @Override
    public String showArguments() {
        return "CSV output format";
    }
}
