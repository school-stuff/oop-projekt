package scenes;


import battlefield.*;
import enums.Direction;
import enums.InventorySelection;
import enums.KeyPress;
import enums.SquareTypes;
import game.Player;
import io.reactivex.Observable;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import services.GameService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class BattleFieldScene {
    private static int[][] map;
    private RenderedArea userLocation;
    private GridPane gridPane;
    private HUD hud;
    private BorderPane borderPane;
    private ArrayList<KeyPress> keyPresses;


    public BattleFieldScene() {
        createKeyPressesList();
        map = new BattleFieldMap().getBattleFieldArray();
        Render render = Render.getInstance();
        createView();
        showScene(render);
        render.addEKeyEventHandler(KeyEvent.KEY_PRESSED, event -> handleKeyEvent(event));
        createObserver();
    }

    public void handleKeyEvent(KeyEvent event) {
        for (KeyPress keyPress : keyPresses) {
            if (keyPress.getKeyCode() == event.getCode()) {
                if (keyPress.getClass() == Direction.class) {
                    Direction direction = (Direction) keyPress;
                    int newX = userLocation.getPlayerX() + direction.getX();
                    int newY = userLocation.getPlayerY() + direction.getY();
                    GameService.getInstance().sendLocationRequest(newX, newY);
                    break;
                }
                if (keyPress.getClass() == InventorySelection.class) {
                    InventorySelection inventorySelection = (InventorySelection) keyPress;
                    Player.slotEquipped.onNext(inventorySelection.getValue());
                }
            }
        }
    }

    private void showScene(Render render) {
        Scene battleFieldScene = new Scene(borderPane, Color.BLACK);
        render.showScene(battleFieldScene);
    }

    private void createObserver() {
        Observable<int[]> userLocationObservable = GameService.getInstance().getCharacterLocation();
        userLocationObservable.subscribe(data -> {
            Platform.runLater(() -> {
                userLocation = new RenderedArea(data[0], data[1]);
                showMapNodes();
            });
        });
    }

    private void createView() {
        borderPane = new BorderPane();
        createGridPane();
        hud = new HUD();
        borderPane.setTop(gridPane);
        borderPane.setBottom(hud.getGridPane());
    }

    private void createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #222222;");
        gridPane.setVgap(1);
        gridPane.setHgap(1);

        gridPane.setPadding(new Insets(0, 0, 0, 0));
        this.gridPane = gridPane;
    }

    private void showMapNodes() {
        gridPane.getChildren().clear();
        BattleFieldSquare square;
        for (int i = 0; i < userLocation.squaresInRow(); i++) {
            for (int j = 0; j < userLocation.squaresInColumns(); j++) {
                square = SquareTypes.getSquare(map[userLocation.upperRowIndex() + j][userLocation.leftColumnIndex() + i]);
                square.addImageToGridPane(gridPane, j, i);
            }
        }
        addCharacterLayer();
    }

    private void createKeyPressesList() {
        keyPresses = new ArrayList<>();
        keyPresses.addAll(Arrays.asList(Direction.values()));
        keyPresses.addAll(Arrays.asList(InventorySelection.values()));
    }

    public void addCharacterLayer() {
        ImageView imageView = new ImageView(ImageOpener.getCharacterImage());
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        gridPane.add(imageView, userLocation.renderedX(), userLocation.renderedY());
    }
}
