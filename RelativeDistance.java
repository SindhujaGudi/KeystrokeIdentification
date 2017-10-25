import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RelativeDistance {
    private final static Comparator<Digraph> timeBetweenKeysComparator = Comparator.comparingLong(Digraph::getTimeBetweenKeys);

    public static double calculate(List<Digraph> trainingSample, List<Digraph> freeTextSample) {
        List<Digraph> sample1 = new ArrayList<>(trainingSample);
        List<Digraph> sample2 = new ArrayList<>(freeTextSample);

        sample1.retainAll(sample2);
        sample2.retainAll(sample1);

        if (sample1.size() == 0) {
            return -1;
        }

        sample1.sort(timeBetweenKeysComparator);
        sample2.sort(timeBetweenKeysComparator);

        int totalOfIndividualDistance = 0;
        for (int i = 0; i < sample1.size(); i++) {
            for (int j = 0; j < sample2.size(); j++) {
                if (sample1.get(i).getKeys().equals(sample2.get(j).getKeys())) {
                    totalOfIndividualDistance += Math.abs(j - i);
                }
            }
        }

        int maxDisorderOfTheSample = maxDisorder(sample1.size());

        double result = totalOfIndividualDistance / (double) maxDisorderOfTheSample;

        return result;
    }

    private static int maxDisorder(int size) {
        if (size % 2 == 0) {
            return (size * size) / 2;
        } else {
            return (size * size - 1) / 2;
        }
    }
}
