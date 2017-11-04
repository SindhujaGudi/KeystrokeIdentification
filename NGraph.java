import java.util.Objects;

/*
A class to record the digraph keys and the time between the key strokes
 */
public class NGraph {
    private String keys;
    private long timeBetweenKeys;
    private double scaledTimeBetweenKeys;

    public NGraph(String keys, long timeBetweenKeys) {
        this.keys = keys;
        this.timeBetweenKeys = timeBetweenKeys;
    }

    public NGraph(String keys, long timeBetweenKeys, double scaledTimeBetweenKeys) {
        this.keys = keys;
        this.timeBetweenKeys = timeBetweenKeys;
        this.scaledTimeBetweenKeys = scaledTimeBetweenKeys;
    }

    public NGraph(NGraph graph) {
        this(graph.keys, graph.timeBetweenKeys);
    }

    @Override
    public String toString() {
        return String.format("{'%s'=%d}", keys, timeBetweenKeys);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NGraph NGraph = (NGraph) o;
        return Objects.equals(keys, NGraph.keys);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keys);
    }

    public String getKeys() {
        return keys;
    }

    public long getTimeBetweenKeys() {
        return timeBetweenKeys;
    }

    public double getScaledTimeBetweenKeys() {
        return scaledTimeBetweenKeys;
    }

    public void setScaledTimeBetweenKeys(double scaledTimeBetweenKeys) {
        this.scaledTimeBetweenKeys = scaledTimeBetweenKeys;
    }
}
