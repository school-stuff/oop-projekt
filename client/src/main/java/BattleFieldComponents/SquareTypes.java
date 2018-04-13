package BattleFieldComponents;

public enum SquareTypes {
    Rock (-1),
    Grass (0),
    Water (2),
    Wall (3);

    private int num;
    SquareTypes(int i) {
        this.num = i;
    }

    public int getNum() {
        return num;
    }
}
