import java.util.List;
import java.util.Stack;

public class FreeTextPanelHandler {
    private Authenticator authenticator = new Authenticator();

    @SuppressWarnings("unchecked")
    public List<User> process(Stack<KeyStroke> keyStrokes) throws Exception {
        List<NGraph> uniGraphTimings = GraphUtility.computeUniGraphTimings((Stack<KeyStroke>) keyStrokes.clone());
        List<NGraph> diGraphTimings = GraphUtility.computeDigraphTimings((Stack<KeyStroke>) keyStrokes.clone());
        UserProfile toBeDeterminedUser = new UserProfile();
        toBeDeterminedUser.add(ProfileType.UNIGRAPH, uniGraphTimings);
        toBeDeterminedUser.add(ProfileType.DIGRAPH, diGraphTimings);

        // Write the sample data to file
        User user = new User("data", "sample");
        FileWriter.write(user, ProfileType.UNIGRAPH, uniGraphTimings);
        FileWriter.write(user, ProfileType.DIGRAPH, diGraphTimings);

        List<UserProfile> userProfiles = FileReader.readAllUserProfiles();
        List<User> users = authenticator.compareUserProfiles(userProfiles, toBeDeterminedUser);
        return users;
    }
}
