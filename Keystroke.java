import java.util.Objects;

/*
A class to record the user typed key and its startTime of even
 */
public class KeyStroke {
    private String key;
    private long startTime;
    private long endTime;

    public KeyStroke(String key, long startTime) {
        this.key = key;
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyStroke keyStroke = (KeyStroke) o;
        return Objects.equals(key, keyStroke.key);
    }

    @Override
    public String toString() {
        return key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    public String getKey() {
        return key;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
