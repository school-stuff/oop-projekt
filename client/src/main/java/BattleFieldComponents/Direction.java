package BattleFieldComponents;

public enum Direction {
    UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);

    private int incrX;
    private int incrY;
    private Direction(int incrX, int incrY) {
        this.incrX = incrX;
        this.incrY = incrY;
    }

    public int getIncrX() {
        return incrX;
    }

    public int getIncrY() {
        return incrY;
    }
}
