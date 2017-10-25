public class KeyStroke {
    private String key;
    private long time;

    public KeyStroke(String key, long time) {
        this.key = key;
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public long getTime() {
        return time;
    }
}
