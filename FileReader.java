import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
A class to read the user's training digraph from input file.
 */
public class FileReader {

    public static List<NGraph> read(String fileName, String delimiter) throws IOException {
        List<NGraph> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(fileName))) {
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(delimiter);
                String keys = parts[0];
                long timeBetweenKeys = Long.parseLong(parts[1]);
                result.add(new NGraph(keys, timeBetweenKeys));

                line = reader.readLine();
            }
        }
        return result;
    }

    public static List<UserProfile> readAllUserProfiles() throws Exception {
        List<UserProfile> userProfiles = new ArrayList<>();
        File userProfileDir = new File(System.getProperty("user.dir") + "/" + Constants.USER_PROFILE_DIR);
        if (!userProfileDir.exists() || userProfileDir.listFiles().length == 0) {
            throw new Exception("No existing user profiles found");
        }
        for (File userDir : userProfileDir.listFiles()) {
            if (Constants.SAMPLE_DATA_DIR.equals(userDir.getName())) {
                continue;
            }
            User user = parseUser(userDir.getName());
            UserProfile userProfile = new UserProfile(user);

            String uniGraphFile = userDir.getAbsolutePath() + "/" + ProfileType.UNIGRAPH.value() + ".txt";
            if ((!(new File(uniGraphFile).exists()))) {
                continue;
            }
            List<NGraph> uniGraphTimings = read(uniGraphFile, ",");
            userProfile.add(ProfileType.UNIGRAPH, uniGraphTimings);

            String diGraphFile = userDir.getAbsolutePath() + "/" + ProfileType.DIGRAPH.value() + ".txt";
            List<NGraph> diGraphTimings = read(diGraphFile, ",");
            userProfile.add(ProfileType.DIGRAPH, diGraphTimings);

            userProfiles.add(userProfile);
        }
        return userProfiles;
    }

    private static User parseUser(String fileName) {
        String[] parts = fileName.split("_");
        return new User(parts[1], parts[0]);
    }
}
