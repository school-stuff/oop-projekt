package BattleFieldComponents;

import java.util.HashMap;
import java.util.Map;

public enum SquareTypes {
    ROCK('R', new Rock()),
    GRASS('G', new BattleFieldSquare()),
    WATER('W', new Water()),
    WALL('X', new Wall());

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
