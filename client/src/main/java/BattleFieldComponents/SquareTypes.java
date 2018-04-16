package BattleFieldComponents;

import java.util.HashMap;
import java.util.Map;

public enum SquareTypes {
    ROCK(-1, new Rock()),
    GRASS(0, new BattleFieldSquare()),
    WATER(1, new Water()),
    WALL(2, new Wall());

    private static final Map<Integer, SquareTypes> myMap = new HashMap<>();
    static {
        for (SquareTypes squareTypes : values()) {
            myMap.put(squareTypes.getType(), squareTypes);
        }
    }
    private BattleFieldSquare square;
    private int type;

    SquareTypes(int type, BattleFieldSquare square) {
        this.type = type;
        this.square = square;
    }

    public static BattleFieldSquare getSquare(int type) {
        return myMap.get(type).getSquare();
    }

    public BattleFieldSquare getSquare() {
        return square;
    }

    public int getType() {
        return type;
    }
}
