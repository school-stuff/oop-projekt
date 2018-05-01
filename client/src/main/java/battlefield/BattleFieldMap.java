package battlefield;

import enums.SquareTypes;

public class BattleFieldMap {

    public BattleFieldMap() { }

    public int[][] getBattleFieldArray() {
        return Maps.map;
    }

    public static boolean canGoToSquare(int x, int y) {
        if (x < 0 || y < 0 || x > Maps.map[0].length - 1 || y > Maps.map.length - 1){
            return false;
        }
        return SquareTypes.getSquare(Maps.map[y][x]).canGoTo();
    }

    public int height() {
        return Maps.map.length;
    }

    public int width() {
        return Maps.map[0].length;
    }

    public void setMap(int coordinateX, int coordinateY, int squareType) {
        Maps.map[coordinateY][coordinateX] = squareType;
    }
}
