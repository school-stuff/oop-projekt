package BattleFieldComponents;
import Scenes.BattleFieldScene;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BattleFieldMap {
    private final static int VERTICAL_LENGTH = 50;
    private final static int HORISONTAL_LENGTH = 50;
    private final static char[][] battleFieldArray = new char[VERTICAL_LENGTH][HORISONTAL_LENGTH];

    public BattleFieldMap() {
        createMap();
    }

    public char[][] getBattleFieldArray() {
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

    private void createMap() {
        File map = new File("client/src/main/resources/maps/map.txt");
        try (FileInputStream fileInputStream = new FileInputStream(map)) {
            for (int i = 0; i < VERTICAL_LENGTH; i++) {
                for (int j = 0; j < HORISONTAL_LENGTH; j++) {
                    battleFieldArray[i][j] = (char) fileInputStream.read();
                }
                fileInputStream.skip(2);
            }
        } catch (IOException e) {
            System.err.println("Map parsing error: " + e.toString());
            // TODO: handle
        }
    }
}
