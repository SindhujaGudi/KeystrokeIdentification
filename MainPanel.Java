import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

/*
This is the class containing the main() method.
This class creates the GUI.
It controls the flow of the application.
 */
public class MainPanel extends Application {
    // The entry point to the application. It launches the user interface.
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // This is the main panel and contains the other components
        BorderPane mainPanel = new BorderPane();
        HBox topNavigation = new HBox();
        topNavigation.setPadding(new Insets(5));
        Button backButton = new Button("Back");
        backButton.setShape(new Polygon(0.0, 10.0,
                12.0, 0.0,
                80.0, 0.0,
                80.0, 20.0,
                12.0, 20.0));
        topNavigation.getChildren().addAll(backButton);

        FreeTextPanel freeTextPanel = new FreeTextPanel();
        CreateProfilePanel createProfilePanel = new CreateProfilePanel();

        EventHandler<ActionEvent> createProfileHandler = event -> {
            primaryStage.setTitle("Create Profile");
            mainPanel.setCenter(createProfilePanel);
            mainPanel.setTop(topNavigation);
            createProfilePanel.reset();
        };
        EventHandler<ActionEvent> enterFreeTextHandler = event -> {
            primaryStage.setTitle("Enter Free Text");
            mainPanel.setCenter(freeTextPanel);
            mainPanel.setTop(topNavigation);
            freeTextPanel.reset();
        };
        OptionsPanel optionsPanel = new OptionsPanel(createProfileHandler, enterFreeTextHandler);

        mainPanel.setCenter(optionsPanel);

        backButton.setOnAction(event -> {
            primaryStage.setTitle("Keystroke Authentication");
            mainPanel.setCenter(optionsPanel);
            mainPanel.setTop(null);
        });

        // Make the main panel visible
        Scene scene = new Scene(mainPanel, 500, 250);
        primaryStage.setMinHeight(200);
        primaryStage.setMinWidth(400);
        primaryStage.setTitle("Keystroke Authentication");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
