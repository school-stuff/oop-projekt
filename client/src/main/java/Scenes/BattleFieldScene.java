package Scenes;

import BattleFieldComponents.*;
import io.reactivex.Observable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import services.GameService;

import java.io.IOException;

public class BattleFieldScene {
    private final int[][] map;
    private static int VISIBLE_HEIGHT = 11;
    private static int VISIBLE_WIDTH = 11;
    private static OpenedImages images;

    public BattleFieldScene() throws IOException {
        map = new BattleFieldMap().createMapVol1();
        images = new OpenedImages();
        GridPane gridPane = createGridPane();
        creatingBattleField(gridPane);
    }

    private void creatingBattleField(GridPane gridPane) {
        Observable<int[]> ourObservable = GameService.getInstance().getCharacterLocation();
        ourObservable.subscribe(data -> {
            getVisiblePart(gridPane, data[0], data[1]);
            Render.getInstance().showScene(new Scene(gridPane, Color.BLACK));
        });
    }

    public static OpenedImages getOpenedImages() {
        return images;
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(1);
        gridPane.setHgap(1);
        gridPane.setPadding(new Insets(0, 0, 0, 0));
        return gridPane;
    }

    private void getVisiblePart(GridPane gridPane, int userX, int userY) throws IOException {
        BattleFieldSquare square;
        for (int i = 0; i < VISIBLE_HEIGHT; i++) {
            for (int j = 0; j < VISIBLE_WIDTH; j++) {
                if (userInTheMiddle(userX, userY)) {
                   square = createSquare(map[userX - (VISIBLE_WIDTH / 2) + j][userY - (VISIBLE_HEIGHT / 2) + i], j, i);
                } else {
                    square = createSquare(map[j][i], j, i);
                }
                square.addImageToGridPane(gridPane);
            }
        }
        BattleFieldSquare.addExtraLayer(images.getCharacterImage(),gridPane, userXVisible(userX), userYVisible(userY));
    }

    private BattleFieldSquare createSquare(int type, int x, int y) throws IOException {
        if (type == SquareTypes.GRASS.getNum()) {
            return new BattleFieldSquare(x, y);
        }
        if (type == SquareTypes.ROCK.getNum()) {
            return new Rock(x, y);
        }
        if (type == SquareTypes.WATER.getNum()) {
            return new Water(x, y);
        } if (type == SquareTypes.WALL.getNum()) {
            return new Wall(x, y);
        }

        throw new RuntimeException("not correct type");
    }

    private boolean userInTheMiddle(int userX, int userY){
        return userY >= VISIBLE_HEIGHT / 2 && userX >= VISIBLE_WIDTH / 2;
    }

    private int userXVisible(int userX){
        if (userX < VISIBLE_WIDTH / 2) {
            return userX;
        }
        return VISIBLE_WIDTH / 2;
    }

    private int userYVisible(int userY) {
        if (userY < VISIBLE_HEIGHT / 2) {
            return userY;
        }
        return VISIBLE_HEIGHT / 2;
    }
}
