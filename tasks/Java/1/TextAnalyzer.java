import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;

class TextAnalyzer{
    public static void main(String[] args) throws IOException{
        Integer textAnalyze1=0;
        Integer textAnalyze2=0;
        Map<Integer, Integer> textAnalyze3= new HashMap<>();
        if(args.length<1 || args.length>2){
            System.out.println("Number of arguments is illegal!");
            return;
        }
        Path inputPath=Path.of(args[0]);
        Optional<Path> outputPath=(args.length==2)? Optional.of(Path.of(args[1])):Optional.empty();
        StringBuilder sb = new StringBuilder();
        try(InputStream input = new FileInputStream(inputPath.toString())){
            try(InputStreamReader reader = new InputStreamReader(input)){
                int data = reader.read();
                while(data!=-1){
                    sb.append((char)data);
                    data = reader.read();
                }
            }
        }
        List<String> parsed=List.of(sb.toString().split("[\\W\\s]+"));
        textAnalyze1=parsed.size();
        textAnalyze2=sb.toString().replaceAll("[^ ]", "").length();
        parsed.forEach(str->textAnalyze3.compute(str.length(), (k,v)->textAnalyze3.containsKey(k)?v+1:1));
        String[] msgArr={"Number of words: "+String.valueOf(textAnalyze1)+"\n"+"Number of spaces: "+String.valueOf(textAnalyze2)+"\n"+"Map of words' length to number of words with the same length:\n"};
        textAnalyze3.forEach((k,v)->{msgArr[0]+=String.format("Length: %d -> Number: %d\n", k, v);});
        if(outputPath.isEmpty()){
            System.out.println(msgArr[0]);
        }
        else{
            try(OutputStream output=new FileOutputStream(outputPath.get().toString())){
                try(OutputStreamWriter writer = new OutputStreamWriter(output)){
                    writer.write(msgArr[0]);
                }
            }
        }
    }
}