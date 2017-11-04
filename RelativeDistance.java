import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
Class to calculate the relative distance between
the user's training digraph timings and the user's input digraph timings
 */
public class RelativeDistance {
    private final static Comparator<NGraph> timeBetweenKeysComparator = Comparator.comparingLong(NGraph::getTimeBetweenKeys);

    public static double calculate(List<NGraph> trainingSample, List<NGraph> freeTextSample) {
//        Logger.info("Calculating relative distance");
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
        if (sample1.size() == 0) {
            Logger.info("There are no common digraphs between the training sample and the user input text");
            return Double.MAX_VALUE;
        }
        Logger.info("No of common graphs between the training sample and the user input text = %d", sample1.size());

        // Sort the training digraphs in ascending order of key stroke timings
        sample1.sort(timeBetweenKeysComparator);
        // Sort the user input digraphs in ascending order of key stroke timings
        sample2.sort(timeBetweenKeysComparator);

        // Calculate the sum of individual digraph distance between the 2 digraph lists
        int totalOfIndividualDistance = 0;
        for (int i = 0; i < sample1.size(); i++) {
            for (int j = 0; j < sample2.size(); j++) {
                if (sample1.get(i).getKeys().equals(sample2.get(j).getKeys())) {
                    totalOfIndividualDistance += Math.abs(j - i);
                    break;
                }
            }
        }

        // If the maximum distance disorder of the common digraph set is 0 return Double.MAX_VALUE
        int maxDisorderOfTheSample = maxDisorder(sample1.size());
        Logger.info("The maximum disorder of the common graph set is %d", maxDisorderOfTheSample);
        if (maxDisorderOfTheSample == 0) {
            return Double.MAX_VALUE;
        }

        double result = totalOfIndividualDistance / (double) maxDisorderOfTheSample;
//        Logger.info("Relative distance = %.2f", result);
//        Logger.sectionEnd();

        return result;
    }

    // Calculates the maximum disorder of a digraph list according to the formula given in the paper
    private static int maxDisorder(int size) {
        if (size % 2 == 0) {
            return (size * size) / 2;
        } else {
            return (size * size - 1) / 2;
        }
    }
}
