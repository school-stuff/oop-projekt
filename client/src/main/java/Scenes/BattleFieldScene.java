package Scenes;

import BattleFieldComponents.*;
import io.reactivex.Observable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import services.GameService;

import java.io.IOException;

public class BattleFieldScene {
    private static int[][] map;
    private static OpenedImages images;
    private static final int SQUARES_IN_ROW = 11;
    private static final int NUMBER_OF_COLUMNS = 11;
    private final Player player = new Player();

    public BattleFieldScene() throws IOException {
        map = new BattleFieldMap().createMap();
        images = new OpenedImages();
        GridPane gridPane = createGridPane();
        Scene battleFieldScene = new Scene(gridPane, Color.BLACK);
        Render.getInstance().showScene(battleFieldScene);
        createObserver(gridPane);
    }

    private void createObserver (GridPane gridPane) {
       Observable<int[]> ourObservable = GameService.getInstance().getCharacterLocation();
        ourObservable.subscribe(data -> {
            player.setX(data[0]);
            player.setY(data[1]);
            setVisiblePart(gridPane, data[0], data[1]);
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

    private void setVisiblePart(GridPane gridPane, int userX, int userY) {
        gridPane.getChildren().removeAll();
        BattleFieldSquare square;
        for (int i = 0; i < SQUARES_IN_ROW; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                if (player.userInTheMiddle(userX, userY)) {
                   square = SquareTypes.getSquare(map[userX - (NUMBER_OF_COLUMNS / 2) + j][userY - (SQUARES_IN_ROW / 2) + i]);
                } else {
                    square = SquareTypes.getSquare(map[j][i]);
                }
                square.addImageToGridPane(gridPane, j, i);
            }
        }
        player.addCharacterLayer(gridPane);
    }
}
