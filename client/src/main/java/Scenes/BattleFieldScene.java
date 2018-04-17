package Scenes;

import BattleFieldComponents.BattleFieldMap;
import BattleFieldComponents.BattleFieldSquare;
import BattleFieldComponents.OpenedImages;
import BattleFieldComponents.SquareTypes;
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
    private final int[][] map;
    private static int SQUARES_IN_ROW = 11;
    private static int NUMBER_OF_COLUMNS = 11;
    private static OpenedImages images;
    private boolean sceneOpened = false;

    public BattleFieldScene() throws IOException, InterruptedException {
        map = new BattleFieldMap().createMap();
        images = new OpenedImages();
        GridPane gridPane = createGridPane();
        creatingBattleField(gridPane);
    }

    private void creatingBattleField(GridPane gridPane) throws IOException{

       Observable<int[]> ourObservable = GameService.getInstance().getCharacterLocation();
        ourObservable.subscribe(data -> {
            if (!sceneOpened) {
                Scene battleFieldScene = new Scene(gridPane);
                battleFieldScene.setFill(Color.BLACK);
                getVisiblePart(gridPane, data[0], data[1]);
                Render.getInstance().showScene(battleFieldScene);
                sceneOpened = true;
            }
            getVisiblePart(gridPane, data[0], data[1]);
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

    private void getVisiblePart(GridPane gridPane, int userX, int userY) {
        gridPane.getChildren().removeAll();
        BattleFieldSquare square;
        for (int i = 0; i < SQUARES_IN_ROW; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                if (userInTheMiddle(userX, userY)) {
                   square = createSquare(map[userX - (NUMBER_OF_COLUMNS / 2) + j][userY - (SQUARES_IN_ROW / 2) + i]);
                } else {
                    square = createSquare(map[j][i]);
                }
                square.addImageToGridPane(gridPane, j, i);
            }
        }
        addExtraLayer(images.getCharacterImage(),gridPane, userXVisible(userX), userYVisible(userY));
    }

    private BattleFieldSquare createSquare(int type) {
        return SquareTypes.getSquare(type);
    }

    private void addExtraLayer(Image layerImage, GridPane gridPane, int x, int y){
        ImageView imageView = new ImageView(layerImage);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        gridPane.add(imageView, y, x);
    }

    private boolean userInTheMiddle(int userX, int userY){
        return userY >= SQUARES_IN_ROW / 2 && userX >= NUMBER_OF_COLUMNS / 2;
    }

    private int userXVisible(int userX){
        if (userX < NUMBER_OF_COLUMNS / 2) {
            return userX;
        }
        return NUMBER_OF_COLUMNS / 2;
    }

    private int userYVisible(int userY) {
        if (userY < SQUARES_IN_ROW / 2) {
            return userY;
        }
        return SQUARES_IN_ROW / 2;
    }
}
