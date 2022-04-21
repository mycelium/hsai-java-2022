public class ExceptionHandler {
    public void stop(String message) {
        System.out.println("\u001B[31m" + message + "\u001B[0m");
        System.exit(-1);
    }
}
