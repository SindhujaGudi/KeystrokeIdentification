public class Logger {
    public static void info(String message) {
        System.out.println(message);
    }

    public static void error(String message, Exception e) {
        System.err.printf("%s. Error - %s\n", message, e.getMessage());
    }

    public static void error(Exception e) {
        System.err.printf("Error - %s\n", e.getMessage());
    }

    public static void error(String message) {
        System.err.println(message);
    }
}
