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
    public static void main(java.lang.String[] args) {
        DataLoadService dataLoadService = new DataLoadService();
        FileCounterService fileCounterService = new FileCounterService();
        FIleCounterToStringService fIleCounterToStringService = new FIleCounterToStringService();

        // tasks/java/1/resources/wells_invisible_man_txt.txt
        DataLoad dataLoad = dataLoadService.dataLoad();
        FileCounterResult fileCounterResult = fileCounterService.countAllSymbols(dataLoad);
        String textForOutput = fIleCounterToStringService.resultToString(fileCounterResult);

        DataWriter dataWriter = dataLoad.getFileForOutput() == null ?
            new ConsoleWriter() :
            new WriterInFile(dataLoad.getFileForOutput());

        dataWriter.write(textForOutput);
    }
}
