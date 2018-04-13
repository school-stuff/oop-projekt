package BattleFieldComponents;

public enum SquareTypes {
    ROCK(-1),
    GRASS(0),
    WATER(2),
    WALL(3);

    private int num;
    SquareTypes(int i) {
        this.num = i;
    }

    public int getNum() {
        return num;
    }
}
