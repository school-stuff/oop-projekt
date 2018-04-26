package BattleFieldComponents;

import javafx.scene.input.KeyCode;

public enum Direction {
    UP(0, -1, KeyCode.UP), DOWN(0, 1, KeyCode.DOWN), LEFT(-1, 0, KeyCode.LEFT), RIGHT(1, 0, KeyCode.RIGHT);

    private int X;
    private int Y;
    private KeyCode keyCode;
    private Direction(int X, int Y, KeyCode keyCode) {
        this.X = X;
        this.Y = Y;
        this.keyCode = keyCode;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public KeyCode getKeyCode() {
        return keyCode;
    }
}
