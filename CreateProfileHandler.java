import java.io.IOException;
import java.util.*;

public class CreateProfileHandler {

    @SuppressWarnings("unchecked")
    public void create(User user, Stack<KeyStroke> keyStrokes) throws IOException {
        List<NGraph> uniGraphTimings = GraphUtility.computeUniGraphTimings((Stack<KeyStroke>) keyStrokes.clone());
        List<NGraph> digraphTimings = GraphUtility.computeDigraphTimings((Stack<KeyStroke>) keyStrokes.clone());

        FileWriter.write(user, ProfileType.UNIGRAPH, uniGraphTimings);
        FileWriter.write(user, ProfileType.DIGRAPH, digraphTimings);
    }

}
