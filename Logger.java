/*
This file has methods for logging info and error messages.
It displays the messages on the console.
 */
public class Logger {
    public static void info(String message, Object... params) {
        System.out.println(String.format(message, params));
    }

    public static void error(String message, Exception e) {
        System.err.printf("%s. Error - %s\n", message, e.getMessage());
        e.printStackTrace();
    }

    public static void error(Exception e) {
        System.err.printf("Error - %s\n", e.getMessage());
    }

    public static void error(String message) {
        System.err.println(message);
    }

    public static void sectionEnd() {
        info("=====================");
    }

    public static void logScore(double score) {
        info(String.format("Score is %.2f", score));
    }
}
