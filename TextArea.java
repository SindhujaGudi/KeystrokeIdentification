import javafx.scene.input.KeyEvent;

import java.util.Stack;

public class TextArea extends javafx.scene.control.TextArea {
    private Stack<KeyStroke> keyStrokes;

    TextArea() {
        super();
        keyStrokes = new Stack<>();
        this.setWrapText(true);
        this.setOnKeyPressed(this::processKeyPressed);
        this.setOnKeyReleased(this::processKeyReleased);
    }

    public void reset() {
        this.clear();
        keyStrokes.clear();
    }

    // It processes each key that is typed in the text area
    private void processKeyPressed(KeyEvent keyEvent) {
        if (!isAllowedKey(keyEvent)) {
            return;
        }
        long currentTime = System.currentTimeMillis();
        String key = keyEvent.getText();
        if ("\r".equals(key) || "\n".equals(key)) {
            key = "";
        }
        keyStrokes.push(new KeyStroke(key, currentTime));
    }

    // It processes each key that is released in the text area
    private void processKeyReleased(KeyEvent keyEvent) {
        if (!isAllowedKey(keyEvent)) {
            return;
        }
        long currentTime = System.currentTimeMillis();
        KeyStroke keyStroke = keyStrokes.peek();
        keyStroke.setEndTime(currentTime);
    }

    private boolean isAllowedKey(KeyEvent keyEvent) {
        return keyEvent.getCode().isLetterKey() || keyEvent.getCode().isWhitespaceKey();
    }

    public Stack<KeyStroke> getKeyStrokes() {
        return keyStrokes;
    }
}
