package text.analyzer;

import text.analyzer.bean.DataLoad;
import text.analyzer.bean.FileCounterResult;
import text.analyzer.service.DataLoadService;
import text.analyzer.service.FileCounterService;
import text.analyzer.service.FIleCounterToStringService;
import text.analyzer.writer.ConsoleWriter;
import text.analyzer.writer.DataWriter;
import text.analyzer.writer.WriterInFile;

public class Main {
    public static void main(String[] args) {
        DataLoadService dataLoadService = new DataLoadService();
        FileCounterService fileCounterService = new FileCounterService();
        FIleCounterToStringService fIleCounterToStringService = new FIleCounterToStringService();

        // The file directory is: resources/Input.txt
        DataLoad dataLoad = dataLoadService.dataLoad();
        FileCounterResult fileCounterResult = fileCounterService.countAllSymbols(dataLoad);
        String textForOutput = fIleCounterToStringService.resultToString(fileCounterResult);

        DataWriter dataWriter;
        if(dataLoad.getFileForOutput() == null) {
        	dataWriter = new ConsoleWriter();
        }
        
        else {
        	dataWriter = new WriterInFile(dataLoad.getFileForOutput());
        }

        dataWriter.write(textForOutput);
    }
}
