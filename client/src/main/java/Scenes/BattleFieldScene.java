package Scenes;

import BattleFieldComponents.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class BattleFieldScene {
    private final int[][] map;
    private static int VISIBLE_HEIGHT = 11;
    private static int VISIBLE_WIDTH = 11;
    private static OpenedImages images;
    private int userX = 10;
    private int userY = 10;

    public BattleFieldScene() throws IOException {
        map = new BattleFieldMap().createMapVol1();
        images = new OpenedImages();
        createScene();
        Render.getInstance().showScene(createScene());
    }

    public static OpenedImages getOpenedImages() {
        return images;
    }

    private Scene createScene() throws IOException {
        GridPane gridPane = createGridPane();
        getVisiblePart(gridPane);
        return new Scene(gridPane, Color.BLACK);
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(1);
        gridPane.setHgap(1);
        gridPane.setPadding(new Insets(0, 0, 0, 0));
        return gridPane;
    }

    private void getVisiblePart(GridPane gridPane) throws IOException {
        BattleFieldSquare square;
        for (int i = 0; i < VISIBLE_HEIGHT; i++) {
            for (int j = 0; j < VISIBLE_WIDTH; j++) {
                if (userInTheMiddle()) {
                   square = createSquare(map[j + userX][i + userY], j, i);
                } else {
                    square = createSquare(map[j][i], j, i);
                }
                square.addImageToGridPane(gridPane);
            }
        }
        BattleFieldSquare.addExtraLayer(images.getCharacterImage(),gridPane, userXVisible(), userYVisible());
    }

    private BattleFieldSquare createSquare(int type, int x, int y) throws IOException {
        if (type == SquareTypes.Grass.getNum()) {
            return new BattleFieldSquare(x, y);
        }
        if (type == SquareTypes.Rock.getNum()) {
            return new Rock(x, y);
        }
        if (type == SquareTypes.Water.getNum()) {
            return new Water(x, y);
        }
        throw new RuntimeException("not correct type");
    }

    private boolean userInTheMiddle(){
        return userY >= VISIBLE_HEIGHT / 2 && userX >= VISIBLE_WIDTH / 2;
    }

    private int userXVisible(){
        if (userX < VISIBLE_WIDTH / 2) {
            return userX;
        }
        return VISIBLE_WIDTH / 2;
    }

    private int userYVisible() {
        if (userY < VISIBLE_HEIGHT / 2) {
            return userY;
        }
        return VISIBLE_HEIGHT / 2;
    }
}
