package BattleFieldComponents;

public class BattleFieldMap {
    private final static int[][] battleFieldArray = new int[50][50];

    public BattleFieldMap() {
        createMap();
    }

    public int[][] getBattleFieldArray() {
        return battleFieldArray;
    }

    public static boolean canGoToSquare(int x, int y) {
        return SquareTypes.getSquare(battleFieldArray[y][x]).canGoTo();
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
