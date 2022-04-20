import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    public static void log(String message, boolean flag) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String time = simpleDateFormat.format(date);

        if (flag) {
            System.out.println(time + "\u001B[32m  INFO\u001B[0m : " + message);
        } else {
            System.out.println(time + "\u001B[31m ERROR\u001B[0m : " + message);
        }
    }

}
