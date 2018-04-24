package Scenes;

import BattleFieldComponents.*;
import io.reactivex.Observable;
import javafx.embed.swing.SwingNode;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Window;
import services.GameService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class BattleFieldScene extends JPanel {
    private static int[][] map;
    private DisplayLocation userLocation;
    private GridPane gridPane;

    public BattleFieldScene() {
        map = new BattleFieldMap().getBattleFieldArray();
        createGridPane();
        userLocation = new DisplayLocation(4, 5);
        Scene battleFieldScene = new Scene(gridPane, Color.BLACK);
        Render render = Render.getInstance();
        render.showScene(battleFieldScene);
        render.addEKeyEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                for (Direction direction : Direction.values()) {
                    if (direction.getKeyCode() == event.getCode()){
                        int newX = userLocation.getUserFullMapX() + direction.getX();
                        int newY = userLocation.getUserFullMapY() + direction.getY();
                        userLocation = new DisplayLocation(newX, newY);
                        setDisplayMap();
                    }
                }
            }
        });
        //createObserver(gridPane);
        setDisplayMap();
    }

    private void createObserver() {
        Observable<int[]> userLocationObservable = GameService.getInstance().getCharacterLocation();
        userLocationObservable.subscribe(data -> {
            userLocation = new DisplayLocation(data[0], data[1]);
            setDisplayMap();
        });
    }

    private void createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(1);
        gridPane.setHgap(1);

        gridPane.setPadding(new Insets(0, 0, 0, 0));
        this.gridPane = gridPane;
    }

    private void setDisplayMap() {
        gridPane.getChildren().removeAll();
        BattleFieldSquare square;
        for (int i = 0; i < userLocation.squaresInRow(); i++) {
            for (int j = 0; j < userLocation.squaresInColumns(); j++) {
                square = SquareTypes.getSquare(map[userLocation.upperRowIndex() + j][userLocation.leftColumnIndex() + i]);
                square.addImageToGridPane(gridPane, j, i);
            }
        }
        addCharacterLayer();
    }

    public void addCharacterLayer() {
        ImageView imageView = new ImageView(ImageOpener.getCharacterImage());
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setFocusTraversable(true);
        gridPane.add(imageView, userLocation.userX(), userLocation.userY());
    }
}
