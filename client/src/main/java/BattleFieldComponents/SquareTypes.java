package BattleFieldComponents;

public enum SquareTypes {
    Grass(0),
    Rock (-1),
    Water (2);

    private int num;
    SquareTypes(int i) {
        this.num = i;
    }

    public int getNum() {
        return num;
    }
}
