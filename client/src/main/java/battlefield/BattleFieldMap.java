package battlefield;

import enums.SquareTypes;

public class BattleFieldMap {
    private final static int VERTICAL_LENGTH = 50;
    private final static int HORISONTAL_LENGTH = 50;
    private final static int[][] battleFieldArray = new int[VERTICAL_LENGTH][HORISONTAL_LENGTH];

    public BattleFieldMap() {
        createMap();
    }

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

    private void createMap(){
        for (int i = 0; i < 50; i++) {
            if (i == 0 || i == 49){
                for (int j = 0; j < 50; j++) {
                    battleFieldArray[i][j] = -1;
                }
            } else {
                for (int j = 0; j < 50; j++) {
                    if (j == 0 || j == 49) {
                        battleFieldArray[i][j] = -1;
                    } else if (j > 3 && j < 5 && i > 6 && i < 12){
                        battleFieldArray[i][j] = 1;
                    } else if (j == 7 && i > 4) {
                        battleFieldArray[i][j] = 2;
                    } else {
                        battleFieldArray[i][j] = 0;
                    }
                }
            }
        }
    }
}
