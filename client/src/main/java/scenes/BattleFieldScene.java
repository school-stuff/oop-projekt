package scenes;


import battlefield.BattleFieldMap;
import battlefield.BattleFieldSquare;
import battlefield.RenderedArea;
import enums.*;
import game.Player;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import services.GameService;
import shared.match.item.RenderItem;
import shared.match.location.Location;

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
        Player.slotEquipped.onNext(0);
        createObserver();
    }

    public void handleKeyEvent(KeyEvent event) {
        for (KeyPress keyPress : keyPresses) {
            if (keyPress.getKeyCode() == event.getCode()) {
                if (event.isAltDown()) {
                    if (keyPress instanceof Direction) {
                        Direction direction = (Direction) keyPress;
                        GameService.getInstance().interactWith(direction.getX(), direction.getY());
                    }
                    // TODO:
                    // else if (keyPress instanceof InventorySelection) Player.dropSlot(((InventorySelection)keyPress).getValue());
                } else {
                    if (keyPress instanceof Direction) {
                        Direction direction = (Direction) keyPress;
                        int newX = userLocation.getPlayerX() + direction.getX();
                        int newY = userLocation.getPlayerY() + direction.getY();
                        GameService.getInstance().sendLocationRequest(newX, newY);
                    } else if (keyPress instanceof InventorySelection) {
                        InventorySelection inventorySelection = (InventorySelection) keyPress;
                        Player.slotEquipped.onNext(inventorySelection.getValue());
                    }
                }
            }
        }
    }

    private void showScene(Render render) {
        Scene battleFieldScene = new Scene(borderPane, Color.BLACK);
        render.showScene(battleFieldScene);
    }

    private void createObserver() {
        GameService.getInstance().getOpponentLocation().subscribe(data -> {
            Platform.runLater(() -> {
                showOpponent((Location.UserLocation) data);
            });
        });
        GameService.getInstance().getItem().subscribe(data -> {
            Platform.runLater(() -> {
                showItem((RenderItem.ItemData) data);
            });
        });
        GameService.getInstance().getCharacterLocation().subscribe(data -> {
            Platform.runLater(() -> {
                userLocation = new RenderedArea(
                        ((Location.UserLocation) data).getX(),
                        ((Location.UserLocation) data).getY());
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
        addImageLayer("character", userLocation.renderedX(), userLocation.renderedY());
    }

    private void showOpponent(Location.UserLocation location) {
        int relativeToUserX = location.getX() - userLocation.getPlayerX();
        int relativeToUserY = location.getY() - userLocation.getPlayerY();
        addImageLayer(
                "character",
                userLocation.renderedX() + relativeToUserX,
                userLocation.renderedY() + relativeToUserY);
    }

    private void showItem(RenderItem.ItemData itemData) {
        String image = Item.fromId(itemData.getId()).getImageType();
        addImageLayer(image,
                itemData.getX(),
                itemData.getY());
    }
      
    private void createKeyPressesList() {
        keyPresses = new ArrayList<>();
        keyPresses.addAll(Arrays.asList(Direction.values()));
        keyPresses.addAll(Arrays.asList(InventorySelection.values()));
        keyPresses.addAll(Arrays.asList(Action.values()));
    }

    public void addImageLayer(String type, int x, int y) {
        ImageView imageView = new ImageView(ImageOpener.getImage(type));
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        gridPane.add(imageView, x, y);
    }
}
