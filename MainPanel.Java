
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainPanel extends Application {
    private final static String FILE_PATH_TO_INPUT_SAMPLE_TIMINGS = "sample_digraph";
    private final static String INPUT_DELIMITER = ",";

    private List<Digraph> sampleKeyTimings = null;
    private List<KeyStroke> allKeyStrokes = new ArrayList<>();
    private double typingSpeedRatio = 1.25D;

    private TextArea inputTextArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        readSampleKeyTimings();
        GridPane gridPane = new GridPane();

        inputTextArea = new TextArea();
        inputTextArea.onKeyPressedProperty().set(event -> processKey(event.getCode(), event.isShiftDown()));
        GridPane.setConstraints(inputTextArea, 1, 1, 2, 1);

        Button resetBtn = new Button("Reset");
        resetBtn.onActionProperty().set(event -> processResetButton());
        GridPane.setConstraints(resetBtn, 1, 2);

        Button submitBtn = new Button("Submit");
        submitBtn.onActionProperty().set(event -> processSubmitButton());
        GridPane.setConstraints(submitBtn, 2, 2);

        gridPane.getChildren().addAll(inputTextArea, resetBtn, submitBtn);

        Scene scene = new Scene(gridPane, 500, 500);
        primaryStage.setTitle("Keystroke authentication");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void processResetButton() {
        allKeyStrokes.clear();
        inputTextArea.clear();
        inputTextArea.requestFocus();
    }

    private void readSampleKeyTimings() {
        try {
            sampleKeyTimings = new FileReader(FILE_PATH_TO_INPUT_SAMPLE_TIMINGS, INPUT_DELIMITER).read();
        } catch (IOException e) {
            Logger.error("Error while reading input file", e);
            System.exit(1);
        }
        System.out.println(sampleKeyTimings);
    }

    private void processSubmitButton() {
        List<Digraph> freeTextDigraphTimings = new ArrayList<>();
        KeyStroke previousKeyStroke = null;
        for (KeyStroke keyStroke : allKeyStrokes) {
            if (previousKeyStroke != null) {
                String keys = previousKeyStroke.getKey() + keyStroke.getKey();
                long timeBetweenKeys = keyStroke.getTime() - previousKeyStroke.getTime();
                freeTextDigraphTimings.add(new Digraph(keys, timeBetweenKeys));
            }
            previousKeyStroke = keyStroke;
        }
        System.out.println(freeTextDigraphTimings);
        System.out.printf("Relative distance = %.2f\n", RelativeDistance.calculate(sampleKeyTimings, freeTextDigraphTimings));
        System.out.printf("Absolute distance = %.2f\n", AbsoluteDistance.calculate(sampleKeyTimings, freeTextDigraphTimings, typingSpeedRatio));
    }

    private void processKey(KeyCode code, boolean isShiftDown) {
        String key = code.getName();
        if (code.isLetterKey() && !isShiftDown) {
            key = key.toLowerCase();
        }
        this.allKeyStrokes.add(new KeyStroke(key, System.currentTimeMillis()));
    }
}
