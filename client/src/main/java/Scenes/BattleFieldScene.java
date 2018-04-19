package Scenes;

import BattleFieldComponents.*;
import io.reactivex.Observable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import services.GameService;

import java.io.IOException;

public class BattleFieldScene {
    private static int[][] map;
    private static final int SQUARES_IN_ROW = 11;
    private static final int SQUARES_IN_COLUMNS = 11;

    private DisplayLocation userDisplayLocation;

    public BattleFieldScene() throws IOException {
        map = new BattleFieldMap().getBattleFieldArray();
        GridPane gridPane = createGridPane();
        Scene battleFieldScene = new Scene(gridPane, Color.BLACK);
        Render.getInstance().showScene(battleFieldScene);
        //createObserver(gridPane);
        setVisiblePart(gridPane, new DisplayLocation(5, 5));
    }

    private void createObserver (GridPane gridPane) {
       Observable<int[]> ourObservable = GameService.getInstance().getCharacterLocation();
        ourObservable.subscribe(data -> {
            userDisplayLocation = new DisplayLocation(data[0], data[1]);
            setVisiblePart(gridPane, userDisplayLocation);
        });
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(1);
        gridPane.setHgap(1);

        gridPane.setPadding(new Insets(0, 0, 0, 0));
        return gridPane;
    }

    private void setVisiblePart(GridPane gridPane, DisplayLocation userDisplayLocation) {
        gridPane.getChildren().removeAll();
        BattleFieldSquare square;
        for (int i = 0; i < SQUARES_IN_ROW; i++) {
            for (int j = 0; j < SQUARES_IN_COLUMNS; j++) {
                square = SquareTypes.getSquare(map[userDisplayLocation.upperRowIndex() + j][userDisplayLocation.leftColumnIndex() + i]);
                square.addImageToGridPane(gridPane, j, i);
            }
        }
        addCharacterLayer(gridPane, userDisplayLocation);
    }

    public void addCharacterLayer(GridPane gridPane, DisplayLocation userDisplayLocation) {
        ImageView imageView = new ImageView(ImageOpener.getCharacterImage());
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        gridPane.add(imageView, userDisplayLocation.userY(), userDisplayLocation.userX());
    }
}
