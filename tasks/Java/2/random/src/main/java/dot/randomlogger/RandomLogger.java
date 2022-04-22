package dot.randomlogger;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RandomLogger {
    static public void log(String info) throws IOException{
        try(OutputStream out=new FileOutputStream("random.log", true)){
            try(OutputStreamWriter writer = new OutputStreamWriter(out)){
                writer.write(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date())+": "+info+"\n");
            }
        }
    }
}
