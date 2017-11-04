import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.stream.Collectors;

public class FreeTextPanel extends BorderPane {
    private TextArea inputTextArea;
    private Label message;
    private FreeTextPanelHandler freeTextPanelHandler;

    FreeTextPanel() {
        freeTextPanelHandler = new FreeTextPanelHandler();
        init();
    }

    private void init() {
        this.setPadding(new Insets(10));
        inputTextArea = new TextArea();
        // make the text area the center component
        this.setCenter(inputTextArea);

        // Create the reset button
        Button resetBtn = new Button("Reset");
        resetBtn.setPrefWidth(120);
        resetBtn.onActionProperty().set(event -> processResetButton());

        // Create the submit button
        Button submitBtn = new Button("Submit");
        submitBtn.setPrefWidth(120);
        submitBtn.onActionProperty().set(event -> processSubmitButton());

        // Place the reset and submit buttons in a horizontal box
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER_RIGHT);
        hbox.getChildren().addAll(resetBtn, submitBtn);

        message = new Label();
        // Place the relative and absolute distance labels in a vertical box
        VBox vBox = new VBox();
        vBox.getChildren().addAll(message, hbox);
        this.setBottom(vBox);
    }

    // Handles the click event on reset button
    private void processResetButton() {
        reset();
    }

    // Handles the click event on submit button
    private void processSubmitButton() {
        if (validInput()) {
            try {
                List<User> users = freeTextPanelHandler.process(inputTextArea.getKeyStrokes());
                List<String> userNameList = users.stream()
                        .map(user -> user.getFirstName() + " " + user.getLastName())
                        .collect(Collectors.toList());
                String userNames = String.join(", ", userNameList);
                message.setText("Matching user names are - " + userNames);
            } catch (Exception e) {
                Logger.error("Error while processing user input", e);
            }
        }
    }

    private boolean validInput() {
        message.setText("");
        if (inputTextArea.getText().trim().length() == 0) {
            message.setText("Please enter some text");
            return false;
        }
        return true;
    }

    public void reset() {
        // Remove the input text
        inputTextArea.reset();
        // Auto focus the text input area
        inputTextArea.requestFocus();
        message.setText("");
    }
}
