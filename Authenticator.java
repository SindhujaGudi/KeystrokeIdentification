import java.util.*;
import java.util.stream.Collectors;

public class Authenticator {

    public List<User> compareUserProfiles(List<UserProfile> userProfiles, UserProfile toBeDeterminedUser) {
        List<UserScore> userScores = new ArrayList<>();
        for (UserProfile userProfile : userProfiles) {
            Logger.info("Comparing with user profile of - " + userProfile.getUser());
            UserScore userScore = new UserScore(userProfile.getUser());

            // UniGraphs
            List<NGraph> trainingUniGraphTimings = userProfile.getGraphTimings(ProfileType.UNIGRAPH);
            List<NGraph> userUniGraphTimings = toBeDeterminedUser.getGraphTimings(ProfileType.UNIGRAPH);

            Logger.info("Calculating " + Algorithm.UNIGRAPH_ABSOLUTE);
            double uniGraphAbsoluteScore = AbsoluteDistance.calculate(trainingUniGraphTimings, userUniGraphTimings);
            Logger.logScore(uniGraphAbsoluteScore);
            userScore.addScore(Algorithm.UNIGRAPH_ABSOLUTE, uniGraphAbsoluteScore);

            Logger.info("Calculating " + Algorithm.UNIGRAPH_RELATIVE);
            double uniGraphRelativeScore = RelativeDistance.calculate(trainingUniGraphTimings, userUniGraphTimings);
            Logger.logScore(uniGraphRelativeScore);
            userScore.addScore(Algorithm.UNIGRAPH_RELATIVE, uniGraphRelativeScore);

            Logger.info("Calculating " + Algorithm.UNIGRAPH_MANHATTAN);
            double uniGraphScaledManhattanScore = ScaledManhattan.calculate(trainingUniGraphTimings, userUniGraphTimings);
            Logger.logScore(uniGraphScaledManhattanScore);
            userScore.addScore(Algorithm.UNIGRAPH_MANHATTAN, uniGraphScaledManhattanScore);

            Logger.info("Calculating " + Algorithm.UNIGRAPH_EUCLIDIAN);
            double uniGraphScaledEuclideanScore = ScaledEuclidean.calculate(trainingUniGraphTimings, userUniGraphTimings);
            Logger.logScore(uniGraphScaledEuclideanScore);
            userScore.addScore(Algorithm.UNIGRAPH_EUCLIDIAN, uniGraphScaledEuclideanScore);

            // DiGraphs
            List<NGraph> trainingDiGraphTimings = userProfile.getGraphTimings(ProfileType.DIGRAPH);
            List<NGraph> userDiGraphTimings = toBeDeterminedUser.getGraphTimings(ProfileType.DIGRAPH);

            Logger.info("Calculating " + Algorithm.DIGRAPH_ABSOLUTE);
            double diGraphAbsoluteScore = AbsoluteDistance.calculate(trainingDiGraphTimings, userDiGraphTimings);
            Logger.logScore(diGraphAbsoluteScore);
            userScore.addScore(Algorithm.DIGRAPH_ABSOLUTE, diGraphAbsoluteScore);

            Logger.info("Calculating " + Algorithm.DIGRAPH_RELATIVE);
            double diGraphRelativeScore = RelativeDistance.calculate(trainingDiGraphTimings, userDiGraphTimings);
            Logger.logScore(diGraphRelativeScore);
            userScore.addScore(Algorithm.DIGRAPH_RELATIVE, diGraphRelativeScore);

            Logger.info("Calculating " + Algorithm.DIGRAPH_MANHATTAN);
            double diGraphScaledManhattanScore = ScaledManhattan.calculate(trainingDiGraphTimings, userDiGraphTimings);
            Logger.logScore(diGraphScaledManhattanScore);
            userScore.addScore(Algorithm.DIGRAPH_MANHATTAN, diGraphScaledManhattanScore);

            Logger.info("Calculating " + Algorithm.DIGRAPH_EUCLIDIAN);
            double diGraphScaledEuclideanScore = ScaledEuclidean.calculate(trainingDiGraphTimings, userDiGraphTimings);
            Logger.logScore(diGraphScaledEuclideanScore);
            userScore.addScore(Algorithm.DIGRAPH_EUCLIDIAN, diGraphScaledEuclideanScore);

            Logger.sectionEnd();

            userScores.add(userScore);
        }
        Map<User, UserScore> userUserScoreMap = userToUserScoreMap(userScores);
        //Normalize the user scores
        for (Algorithm algorithm : Algorithm.values()) {
            Map<User, Double> userScoreMap = new HashMap<>();
            for (UserScore userScore : userScores) {
                userScoreMap.put(userScore.getUser(), userScore.getScore(algorithm));
            }
            Map<User, Double> normalizedUserScoreMap = normalizeScore(userScoreMap);
            for (Map.Entry<User, Double> entry : normalizedUserScoreMap.entrySet()) {
                UserScore userScore = userUserScoreMap.get(entry.getKey());
                userScore.addScore(algorithm, entry.getValue());
            }
        }
        //Determine the users with scores in top 3
        userScores.sort(Comparator.comparing(UserScore::totalScore));
        List<User> topMatchingUsers = userScores.subList(0, Math.min(1, userScores.size())).stream()
                .map(UserScore::getUser)
                .collect(Collectors.toList());
        return topMatchingUsers;
    }

    private Map<User, Double> normalizeScore(Map<User, Double> userScoreMap) {
        List<Map.Entry<User, Double>> userScores = new ArrayList<>(userScoreMap.entrySet());
        userScores.sort(Comparator.comparing(Map.Entry::getValue));
        Map<User, Double> normalizedScores = new HashMap<>();
        for (int i = 0; i < userScores.size(); i++) {
            Map.Entry<User, Double> entry = userScores.get(i);
            double score = (double) (i + 1);
            // If two users have same score, the normalized score should be same
            if (i > 0) {
                Map.Entry<User, Double> previousEntry = userScores.get(i - 1);
                if (previousEntry.getValue().equals(entry.getValue())) {
                    score = normalizedScores.get(previousEntry.getKey());
                }
            }
            normalizedScores.put(entry.getKey(), score);
        }
        return normalizedScores;
    }

    private Map<User, UserScore> userToUserScoreMap(List<UserScore> userScores) {
        Map<User, UserScore> result = new HashMap<>();
        for (UserScore userScore : userScores) {
            result.put(userScore.getUser(), userScore);
        }
        return result;
    }
}
