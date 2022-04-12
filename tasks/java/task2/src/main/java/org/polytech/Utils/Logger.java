package org.polytech.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

// Logger to file with timestamp
public class Logger {
    private static Logger instance = null;
    private static String logPath = "log.txt";
    public static String logFolder;

    static {
        try {
            logFolder = new File(Logger.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI()).getParent();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Impossible case: " + e);
        }
    }

    // Private constructor
    private Logger() {
    }

    public static void setLogFolder(String logFolder) {
        Logger.logFolder = logFolder;
        Logger.logPath = logFolder + File.separator + logPath;
    }

    public static void log(String message) {
        var date = new Date();
        var sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(date);

        try {
            var writer = new PrintWriter(new FileOutputStream(logPath, true));
            writer.println(time + " : " + message);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
