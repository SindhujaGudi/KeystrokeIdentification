import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class CreateProfilePanel extends BorderPane {
    private TextArea inputTextArea;
    private Label errorMessage;
    private CreateProfileHandler createProfileHandler;
    private TextField firstName;
    private TextField lastName;

    CreateProfilePanel() {
        createProfileHandler = new CreateProfileHandler();
        init();
    }

    private void init() {
        this.setPadding(new Insets(5));
        inputTextArea = new TextArea();
        // make the text area the center component
        this.setCenter(inputTextArea);

        firstName = new TextField();
        firstName.setPromptText("First Name");
        lastName = new TextField();
        lastName.setPromptText("Last Name");
        HBox userNameBox = new HBox();
        userNameBox.setPadding(new Insets(10, 0, 0, 0));
        userNameBox.setSpacing(10);
        userNameBox.getChildren().addAll(firstName, lastName);

        // Create the reset button
        Button resetBtn = new Button("Reset");
        resetBtn.setPrefWidth(120);
        resetBtn.onActionProperty().set(event -> processResetButton());

        // Create the submit button
        Button submitBtn = new Button("Submit");
        submitBtn.setPrefWidth(120);
        submitBtn.onActionProperty().set(event -> processSubmitButton());

        // Place the reset and submit buttons in a horizontal box
        HBox buttonsBox = new HBox();
        buttonsBox.setPadding(new Insets(10));
        buttonsBox.setSpacing(10);
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);
        buttonsBox.getChildren().addAll(resetBtn, submitBtn);

        errorMessage = new Label();
        VBox vBox = new VBox();
        vBox.getChildren().addAll(userNameBox, buttonsBox, errorMessage);

        this.setBottom(vBox);
    }

    // Handles the click event on reset button
    private void processResetButton() {
        // Remove the input text
        inputTextArea.reset();
        // Auto focus the text input area
        inputTextArea.requestFocus();
        errorMessage.setText("");
    }

    public void reset() {
        processResetButton();
        firstName.setText("");
        lastName.setText("");
    }

    // Handles the click event on submit button
    private void processSubmitButton() {
        if (validateInput()) {
            User user = getUser();
            try {
                createProfileHandler.create(user, inputTextArea.getKeyStrokes());
                errorMessage.setText("Profile created successfully");
            } catch (IOException e) {
                Logger.error("Error while creating profile", e);
            }
        }
    }

    private User getUser() {
        return new User(firstName.getText().trim(), lastName.getText().trim());
    }

    private boolean validateInput() {
        errorMessage.setText("");
        if (isInvalidText(inputTextArea.getText())) {
            errorMessage.setText("Please enter some text");
            return false;
        }
        if (isInvalidText(firstName.getText()) || isInvalidText(lastName.getText())) {
            errorMessage.setText("Please enter valid user name");
            return false;
        }
        return true;
    }

    private boolean isInvalidText(String text) {
        return text.trim().length() == 0;
    }
}
