import java.util.ArrayList;
import java.util.List;

public class AbsoluteDistance {
    public static double calculate(List<Digraph> trainingSample, List<Digraph> freeTextSample, double typingSpeedRatio) {
        List<Digraph> sample1 = new ArrayList<>(trainingSample);
        List<Digraph> sample2 = new ArrayList<>(freeTextSample);

        sample1.retainAll(sample2);
        sample2.retainAll(sample1);

        int noOfSimilarGraphs = 0;
        for (Digraph d1 : sample1) {
            Digraph d2 = sample2.get(sample2.indexOf(d1));
            double maxMinRatio = Long.max(d1.getTimeBetweenKeys(), d2.getTimeBetweenKeys()) / (double) Long.min(d1.getTimeBetweenKeys(), d2.getTimeBetweenKeys());
            if (maxMinRatio > 1 && maxMinRatio <= typingSpeedRatio) {
                noOfSimilarGraphs++;
            }
        }

        int noOfCommonGraphs = sample1.size();
        double result = 1 - (noOfSimilarGraphs / (double) noOfCommonGraphs);
        return result;
    }
}
