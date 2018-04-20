package Scenes;

import BattleFieldComponents.*;
import io.reactivex.Observable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import services.GameService;

public class BattleFieldScene {
    private static int[][] map;
    private DisplayLocation userLocation;

    public BattleFieldScene() {
        map = new BattleFieldMap().getBattleFieldArray();
        GridPane gridPane = createGridPane();
        Scene battleFieldScene = new Scene(gridPane, Color.BLACK);
        Render.getInstance().showScene(battleFieldScene);
        //createObserver(gridPane);
        setDisplayMap(gridPane, new DisplayLocation(9, 7));
    }

    private void createObserver (GridPane gridPane) {
       Observable<int[]> userLocationObservable = GameService.getInstance().getCharacterLocation();
        userLocationObservable.subscribe(data -> {
            userLocation = new DisplayLocation(data[0], data[1]);
            setDisplayMap(gridPane, userLocation);
        });
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(1);
        gridPane.setHgap(1);

        gridPane.setPadding(new Insets(0, 0, 0, 0));
        return gridPane;
    }

    private void setDisplayMap(GridPane gridPane, DisplayLocation displayLocation) {
        gridPane.getChildren().removeAll();
        BattleFieldSquare square;
        for (int i = 0; i < userLocation.squaresInRow(); i++) {
            for (int j = 0; j < userLocation.squaresInColumns(); j++) {
                square = SquareTypes.getSquare(map[displayLocation.upperRowIndex() + j][displayLocation.leftColumnIndex() + i]);
                square.addImageToGridPane(gridPane, j, i);
            }
        }
        addCharacterLayer(gridPane, displayLocation);
    }

    public void addCharacterLayer(GridPane gridPane, DisplayLocation userDisplayLocation) {
        ImageView imageView = new ImageView(ImageOpener.getCharacterImage());
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        gridPane.add(imageView, userDisplayLocation.userY(), userDisplayLocation.userX());
    }
}
