package enums;

import javafx.scene.input.KeyCode;

public enum Action implements KeyPress{
    INTERACT(KeyCode.X);

    KeyCode keyCode;

    Action(KeyCode keyCode) {
        this.keyCode = keyCode;
    }

    @Override
    public KeyCode getKeyCode() {
        return null;
    }
}
