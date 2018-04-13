package BattleFieldComponents;

import Scenes.BattleFieldScene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

// praegu eeldan, et server kontrollib, kas ja kuhu keegi / miski liikuda saab
public class BattleFieldSquare {
    private static final int SQUARE_SIZE = 50;
    private final int locationX;
    private final int locationY;
    private List<Image> pictureLayers = new ArrayList<>();

    public BattleFieldSquare(int locationX, int locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
        pictureLayers.add(BattleFieldScene.getOpenedImages().getGrassImage());
        //TODO: correct path
    }

    public void addImageToGridPane(GridPane gridPane) {
        for (Image pictureLayer : pictureLayers) {
            ImageView imageView = new ImageView(pictureLayer);
            imageView.setFitWidth(SQUARE_SIZE);
            imageView.setFitHeight(SQUARE_SIZE);
            gridPane.add(imageView, locationY, locationX);
        }
    }

    public void addLayer(Image image) {
        pictureLayers.add(image);
    }

    public static void addExtraLayer(Image layerImage, GridPane gridPane, int x, int y){
        ImageView imageView = new ImageView(layerImage);
        imageView.setFitWidth(SQUARE_SIZE);
        imageView.setFitHeight(SQUARE_SIZE);
        gridPane.add(imageView, y, x);
    }
}
