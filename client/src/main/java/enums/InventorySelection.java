package enums;

import javafx.scene.input.KeyCode;

public enum InventorySelection implements KeyPress{
    FIRST(0, KeyCode.DIGIT1),
    SECOND(1, KeyCode.DIGIT2),
    THIRD(2, KeyCode.DIGIT3),
    FOURTH(3, KeyCode.DIGIT4),
    FIFTH(4, KeyCode.DIGIT5),
    SIXTH(5, KeyCode.DIGIT6);

    int value;
    KeyCode keyCode;

    InventorySelection(int value, KeyCode keyCode) {
        this.value = value;
        this.keyCode = keyCode;
    }

    public int getValue() {
        return value;
    }

    @Override
    public KeyCode getKeyCode() {
        return keyCode;
    }
}
