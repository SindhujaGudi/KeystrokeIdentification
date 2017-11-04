import java.util.HashMap;
import java.util.Map;

public class UserScore {
    private User user;
    private Map<Algorithm, Double> algorithmScores;

    UserScore(User user) {
        this.user = user;
        algorithmScores = new HashMap<>();
    }

    public void addScore(Algorithm algorithm, Double score) {
        algorithmScores.put(algorithm, score);
    }

    public User getUser() {
        return user;
    }

    public double getScore(Algorithm algorithm) {
        return algorithmScores.get(algorithm) == null ? 0D : algorithmScores.get(algorithm);
    }

    public double totalScore() {
        return algorithmScores.values().stream().mapToDouble(Double::doubleValue).sum();
    }
}
