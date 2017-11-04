import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserProfile {
    User user;
    Map<ProfileType, List<NGraph>> graphTimings;

    public UserProfile() {
        this(null);
    }

    public UserProfile(User user) {
        this.user = user;
        graphTimings = new HashMap<>();
    }

    public User getUser() {
        return user;
    }

    public void add(ProfileType type, List<NGraph> graphTimings) {
        this.graphTimings.put(type, graphTimings);
    }

    public List<NGraph> getGraphTimings(ProfileType type) {
        return graphTimings.get(type);
    }
}
