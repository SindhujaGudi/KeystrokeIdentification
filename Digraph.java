import java.util.Objects;

public class Digraph {
    private String keys;
    private long timeBetweenKeys;

    public Digraph(String keys, long timeBetweenKeys) {
        this.keys = keys;
        this.timeBetweenKeys = timeBetweenKeys;
    }

    @Override
    public String toString() {
        return String.format("{'%s'=%d}", keys, timeBetweenKeys);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Digraph digraph = (Digraph) o;
        return Objects.equals(keys, digraph.keys);
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
}
