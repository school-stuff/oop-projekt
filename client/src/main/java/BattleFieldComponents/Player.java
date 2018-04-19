package BattleFieldComponents;

import Scenes.BattleFieldScene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Player {
    private static int SQUARES_IN_ROW = 11;
    private static int NUMBER_OF_COLUMNS = 11;
    private int x = 0;
    private int y = 0;


    public int move(Direction direction){
        return 0;
    }

    public int userXVisible(int userX){
        if (userX < NUMBER_OF_COLUMNS / 2) {
            return userX;
        }
        return NUMBER_OF_COLUMNS / 2;
    }

    public int userYVisible(int userY) {
        if (userY < SQUARES_IN_ROW / 2) {
            return userY;
        }
        return SQUARES_IN_ROW / 2;
    }

    public boolean userInTheMiddle(int userX, int userY){
        return userY >= SQUARES_IN_ROW / 2 && userX >= NUMBER_OF_COLUMNS / 2;
    }

    public void addCharacterLayer(GridPane gridPane){
        ImageView imageView = new ImageView(BattleFieldScene.getOpenedImages().getCharacterImage());
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        gridPane.add(imageView, userYVisible(y), userXVisible(x));
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
