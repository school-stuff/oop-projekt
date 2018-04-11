package BattleFieldComponents;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class OpenedImages {
    private static Image grassImage;
    private final Image rockImage;
    private final Image characterImage;
    private final Image waterImage;


    public OpenedImages() throws IOException {
        this.grassImage = getImageFromFile("client/src/main/resources/images/grass.png");
        this.rockImage = getImageFromFile("client/src/main/resources/images/stone.png");
        this.characterImage = getImageFromFile("client/src/main/resources/images/character.png");
        this.waterImage = getImageFromFile(("client/src/main/resources/images/water.png"));
    }

    public Image getCharacterImage() {
        return characterImage;
    }

    public Image getGrassImage() {
        return grassImage;
    }

    public Image getRockImage() {
        return rockImage;
    }

    public Image getWaterImage() {
        return waterImage;
    }

    private Image getImageFromFile(String file) throws IOException {
        try (InputStream inputStream = new FileInputStream(new File(file))) {
            return new Image(inputStream);
        }
    }
}
