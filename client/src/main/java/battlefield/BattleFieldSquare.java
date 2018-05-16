package battlefield;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import scenes.ImageOpener;

import java.util.ArrayList;
import java.util.List;

public class BattleFieldSquare {
    private List<Image> pictureLayers = new ArrayList<>();

    public BattleFieldSquare()  {
        pictureLayers.add(ImageOpener.getImage("grass"));
    }

    public void addImageToGridPane(GridPane gridPane, int locationX, int locationY) {
        for (Image pictureLayer : pictureLayers) {
            ImageView imageView = new ImageView(pictureLayer);

            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            gridPane.add(imageView, locationY, locationX);
        }
    }

    public void addLayer(Image image) {
        pictureLayers.add(image);
    }

    public boolean canGoTo(){
        return true;
    }
}
