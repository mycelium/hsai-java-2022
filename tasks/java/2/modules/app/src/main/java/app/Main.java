package app;

import io.parameters.Arguments;
import io.parameters.ArgumentsParser;
import io.parameters.StreamCreator;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        if (args.length == 0) {
            System.out.println("Usage: program type (distribution type: normal|continuous|poisson) output (output type: csv|sqlite) " +
                    "file (output file path) count (count:int) " +
                    "[mean (mean:double) stddev (stddev:double)] [origin (origin:double) bound (bound:double)] " +
                    "[lambda (lambda:double)]");
            return;
        }
        var arguments = Arguments.fromArray(args);
        var parser = new ArgumentsParser();
        var streamCreator = new StreamCreator();
        var distrFactroy = parser.parseDistribution(arguments);

        try (var writer = parser.parseOutput(arguments)) {
            var count = Integer.parseInt(arguments.get("count"));
            var stream = streamCreator.createStream(distrFactroy.makeDistribution(), count);
            stream.forEach(writer);
        } catch (NullPointerException | NumberFormatException e) {
            System.err.println("No count parameter or illegal format");
        }
    }
}
