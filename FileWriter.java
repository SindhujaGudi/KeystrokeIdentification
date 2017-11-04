import java.io.*;
import java.util.List;

public class FileWriter {

    static void write(User user, ProfileType type, List<NGraph> graphTimings) throws IOException {
        File file = new File(fileName(user, type));

        file.getParentFile().mkdirs();
        try (PrintWriter w = new PrintWriter(file)) {
            for (NGraph graph : graphTimings) {
                String line = graph.getKeys() + "," + graph.getTimeBetweenKeys() + "\n";
                w.write(line);
            }
        }
    }

    private static String fileName(User user, ProfileType type) {
        return System.getProperty("user.dir") + "/"
                + Constants.USER_PROFILE_DIR + "/"
                + user.getLastName() + "_" + user.getFirstName() + "/"
                + type.value() + ".txt";
    }

}
