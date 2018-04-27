package Scenes;


import BattleFieldComponents.*;
import io.reactivex.Observable;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import services.GameService;

public class BattleFieldScene extends JPanel {
    private static int[][] map;
    private DisplayLocation userLocation;
    private GridPane gridPane;

    public BattleFieldScene() {
        map = new BattleFieldMap().getBattleFieldArray();
        Render render = Render.getInstance();
        createGridPane();
        showScene(render);
        addKeyEventHandler(render);
        createObserver();
    }

    private void showScene(Render render) {
        Scene battleFieldScene = new Scene(gridPane, Color.BLACK);
        render.showScene(battleFieldScene);
    }

    private void addKeyEventHandler(Render render) {
        render.addEKeyEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println("Key pressed: " + event.getCode().getName());
                for (Direction direction : Direction.values()) {
                    if (direction.getKeyCode() == event.getCode()){
                        int newX = userLocation.getUserFullMapX() + direction.getX();
                        int newY = userLocation.getUserFullMapY() + direction.getY();
                        if (BattleFieldMap.canGoToSquare(newX, newY)) {
                            // send server info
                        }
                    }
                }
                showMapNodes();
            }
        });
    }

    private void createObserver() {
        Observable<int[]> userLocationObservable = GameService.getInstance().getCharacterLocation();
        userLocationObservable.subscribe(data -> {
            userLocation = new DisplayLocation(data[0], data[1]);
            showMapNodes();
        });
    }

    private void createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(1);
        gridPane.setHgap(1);

        gridPane.setPadding(new Insets(0, 0, 0, 0));
        this.gridPane = gridPane;
    }

    private void showMapNodes() {
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
        gridPane.add(imageView, userLocation.userX(), userLocation.userY());
    }
}
