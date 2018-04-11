package BattleFieldComponents;

public class BattleFieldMap {
    private static int[][] battleFieldArray = new int[50][50];

    public int[][] createMapVol1(){
        for (int i = 0; i < 50; i++) {
            if (i == 0 || i == 49){
                for (int j = 0; j < 50; j++) {
                    battleFieldArray[i][j] = -1;
                }
            } else {
                for (int j = 0; j < 50; j++) {
                    if (j == 0 || j == 49) {
                        battleFieldArray[i][j] = -1;
                    } else if (j == 3){
                        battleFieldArray[i][j] = 2;
                    } else {
                        battleFieldArray[i][j] = 0;
                    }
                }
            }
        } return battleFieldArray;
    }



}
