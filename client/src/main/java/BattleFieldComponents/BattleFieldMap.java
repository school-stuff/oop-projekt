package BattleFieldComponents;

import Scenes.BattleFieldScene;

public class BattleFieldMap {
    private final static int VERTICAL_LENGTH = Map.getMapHeight();
    private final static int HORISONTAL_LENGTH = Map.getMapWidth();
    private final static int[][] battleFieldArray = Map.getMap();

    public BattleFieldMap() { }

    public int[][] getBattleFieldArray() {
        return battleFieldArray;
    }

    public static boolean canGoToSquare(int x, int y) {
        if (x < 0 || y < 0 || x > battleFieldArray[0].length - 1 || y > battleFieldArray.length - 1){
            return false;
        }
        return SquareTypes.getSquare(battleFieldArray[y][x]).canGoTo();
    }

    public static int heigth() {
        return VERTICAL_LENGTH;
    }

    public static int width() {
        return HORISONTAL_LENGTH;
    }
}
