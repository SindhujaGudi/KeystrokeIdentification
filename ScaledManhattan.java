import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScaledManhattan {
    public static double calculate(List<NGraph> trainingSample, List<NGraph> freeTextSample) {
//        Logger.info("Calculating scaled manhattan distance");
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

        List<NGraph> sample1Clone = GraphUtility.clone(sample1);
        List<NGraph> sample2Clone = GraphUtility.clone(sample2);

        sample1Clone = GraphUtility.scaleDownTimeBetweenKeys(sample1Clone);
        sample2Clone = GraphUtility.scaleDownTimeBetweenKeys(sample2Clone);

        //Sort the lists by the graph keys
        sample1Clone.sort(Comparator.comparing(NGraph::getKeys));
        sample2Clone.sort(Comparator.comparing(NGraph::getKeys));

        List<Double> distanceBetweenPairs = new ArrayList<>();
        for (int i = 0; i < sample1Clone.size(); i++) {
            NGraph graph1 = sample1Clone.get(i);
            NGraph graph2 = sample2Clone.get(i);
            distanceBetweenPairs.add(Math.abs(graph1.getScaledTimeBetweenKeys() - graph2.getScaledTimeBetweenKeys()));
        }
        double manhattanDistance = distanceBetweenPairs.stream().mapToDouble(a -> a).sum();
        double standardDeviationOfDistances = GraphUtility.standardDeviation(distanceBetweenPairs);
        if (standardDeviationOfDistances == 0D) {
            return Double.MAX_VALUE;
        }

        double result = manhattanDistance / standardDeviationOfDistances;
//        Logger.info("Scaled manhattan distance is - %.2f", result);

        return result;
    }

}
