import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GraphUtility {
    public static List<NGraph> computeDigraphTimings(Stack<KeyStroke> keyStrokes) throws IOException {
        Map<String, TimeIntervalCounter> timeIntervalCounterMap = new HashMap<>();
        KeyStroke previousKey = keyStrokes.pop();
        while (!keyStrokes.isEmpty()) {
            KeyStroke currentStroke = keyStrokes.peek();
            if (currentStroke.getEndTime() > 0) {
                String keys = currentStroke.getKey() + previousKey.getKey();
                long timeInterval = previousKey.getStartTime() - currentStroke.getEndTime();

                TimeIntervalCounter timeIntervalCounter = timeIntervalCounterMap.get(keys);
                if (timeIntervalCounter == null) {
                    timeIntervalCounter = new TimeIntervalCounter();
                }
                timeIntervalCounter.addInterval(timeInterval);
                timeIntervalCounterMap.put(keys, timeIntervalCounter);
            }
            previousKey = keyStrokes.pop();
        }

        List<NGraph> digraphTimings = new ArrayList<>(timeIntervalCounterMap.size());
        for (Map.Entry<String, TimeIntervalCounter> entry : timeIntervalCounterMap.entrySet()) {
            NGraph diGraph = new NGraph(entry.getKey(), entry.getValue().getAverage());
            digraphTimings.add(diGraph);
        }
        return digraphTimings;
    }

    public static List<NGraph> computeUniGraphTimings(Stack<KeyStroke> keyStrokes) throws IOException {
        Map<String, TimeIntervalCounter> timeIntervalCounterMap = new HashMap<>();
        while (!keyStrokes.isEmpty()) {
            KeyStroke keyStroke = keyStrokes.pop();
            long timeInterval = keyStroke.getEndTime() - keyStroke.getStartTime();
            if (timeInterval < 0) {
                continue;
            }
            TimeIntervalCounter timeIntervalCounter = timeIntervalCounterMap.get(keyStroke.getKey());
            if (timeIntervalCounter == null) {
                timeIntervalCounter = new TimeIntervalCounter();
            }
            timeIntervalCounter.addInterval(timeInterval);
            timeIntervalCounterMap.put(keyStroke.getKey(), timeIntervalCounter);
        }

        List<NGraph> unigraphTimings = new ArrayList<>(timeIntervalCounterMap.size());
        for (Map.Entry<String, TimeIntervalCounter> entry : timeIntervalCounterMap.entrySet()) {
            NGraph uniGraph = new NGraph(entry.getKey(), entry.getValue().getAverage());
            unigraphTimings.add(uniGraph);
        }
        return unigraphTimings;
    }

    public static List<NGraph> clone(List<NGraph> values) {
        return values.stream().map(NGraph::new).collect(Collectors.toList());
    }

    public static List<NGraph> scaleDownTimeBetweenKeys(List<NGraph> values) {
        long maxDistance = values.stream().mapToLong(NGraph::getTimeBetweenKeys).max().getAsLong();
        return values.stream()
                .map(v -> new NGraph(v.getKeys(), v.getTimeBetweenKeys(), v.getTimeBetweenKeys() / (double) maxDistance))
                .collect(Collectors.toList());
    }

    public static double standardDeviation(List<Double> values) {
        double sum = values.stream().mapToDouble(a -> a).sum();
        double mean = sum / values.size();
        double meanDistanceSum = values.stream()
                .mapToDouble(a -> a)
                .reduce(0D, (a, b) -> a + (b - mean) * (b - mean));
        double meanDistanceSumAverage = meanDistanceSum / values.size();
        double result = Math.sqrt(meanDistanceSumAverage);
        return result;
    }
}
