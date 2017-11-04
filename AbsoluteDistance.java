import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
Class to calculate the absolute distance between
the user's training digraph timings and the user's input digraph timings
 */
public class AbsoluteDistance {
    // This is typing ratio constant as required by the absolute distance formula
    private static double typingSpeedRatio = 1.25D;

    public static double calculate(List<NGraph> trainingSample, List<NGraph> freeTextSample) {
//        Logger.info("Calculating absolute distance");
        // Create a new list of the training digraphs to avoid modifying input list
        List<NGraph> sample1 = new ArrayList<>(trainingSample);
        // Create a new list of the user input digraphs to avoid modifying input list
        List<NGraph> sample2 = new ArrayList<>(freeTextSample);

        // Remove all digraphs from sample1 that are not there in sample2
        sample1.retainAll(sample2);
        // Remove all digraphs from sample2 that are not there in sample1
        sample2.retainAll(sample1);
        // At this point both sample1 and sample2 have the same digraph keys

        // If there are no common digraphs return Double.MAX_VALUE
        int noOfCommonGraphs = sample1.size();
        if (noOfCommonGraphs == 0) {
            Logger.info("There are no common graphs between the training sample and the user input text");
            return Double.MAX_VALUE;
        }

        Logger.info("No of common graphs between the training sample and the user input text = %d", noOfCommonGraphs);

        //Sort the lists by the graph keys
        sample1.sort(Comparator.comparing(NGraph::getKeys));
        sample2.sort(Comparator.comparing(NGraph::getKeys));

        // Count the no of similar graphs based on the timing formula
        int noOfSimilarGraphs = 0;
        for (int i = 0; i < sample1.size(); i++) {
            NGraph d1 = sample1.get(i);
            NGraph d2 = sample2.get(i);
            double maxMinRatio = Long.max(d1.getTimeBetweenKeys(), d2.getTimeBetweenKeys()) / (double) Long.min(d1.getTimeBetweenKeys(), d2.getTimeBetweenKeys());
            if (maxMinRatio > 1 && maxMinRatio <= typingSpeedRatio) {
                noOfSimilarGraphs++;
            }
        }

        Logger.info("No of graphs with similar time distance = %d", noOfSimilarGraphs);

        double result = (noOfSimilarGraphs / (double) noOfCommonGraphs);
//        Logger.info("Absolute distance = %.2f", result);
//        Logger.sectionEnd();

        return result;
    }
}
