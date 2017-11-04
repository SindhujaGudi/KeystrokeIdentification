import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;


public class OptionsPanel extends HBox {
    private final EventHandler<ActionEvent> createProfileHandler;
    private final EventHandler<ActionEvent> enterFreeTextHandler;

    OptionsPanel(EventHandler<ActionEvent> createProfileHandler, EventHandler<ActionEvent> enterFreeTextHandler) {
        this.createProfileHandler = createProfileHandler;
        this.enterFreeTextHandler = enterFreeTextHandler;
        init();
    }

    private void init() {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);

        Button createProfileBtn = new Button("Create User Profile");
        createProfileBtn.setPrefSize(150, 100);
        createProfileBtn.setOnAction(createProfileHandler);

        Button enterFreeTextBtn = new Button("Enter Free Text");
        enterFreeTextBtn.setPrefSize(150, 100);
        enterFreeTextBtn.setOnAction(enterFreeTextHandler);

        this.getChildren().addAll(createProfileBtn, enterFreeTextBtn);
    }
}
