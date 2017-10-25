import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
    private String fileName;
    private String delimiter;

    public FileReader(String fileName, String delimiter) {
        this.fileName = fileName;
        this.delimiter = delimiter;
    }

    public List<Digraph> read() throws IOException {
        List<Digraph> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(this.fileName))) {
            String line = reader.readLine();
            while(line != null) {
                String[] parts = line.split(this.delimiter);
                String keys = parts[0];
                long timeBetweenKeys = Long.parseLong(parts[1]);
                result.add(new Digraph(keys, timeBetweenKeys));

                line = reader.readLine();
            }
        }
        return result;
    }
}
