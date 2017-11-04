public enum ProfileType {
    UNIGRAPH("unigraph"), DIGRAPH("digraph");

    private String value;

    ProfileType(String value) {
        this.value = value;
    }

    String value() {
        return this.value;
    }
}
