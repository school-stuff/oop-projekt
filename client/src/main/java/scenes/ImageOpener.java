package scenes;

import javafx.scene.image.Image;

import java.io.*;

public class ImageOpener {
    private static Image grassImage;
    private static Image rockImage;
    private static Image characterImage;
    private static Image waterImage;
    private static Image wallImage;
    private static Image swordImage;
    private static Image pickaxeImage;
    private static Image potionImage;
    private static Image shieldImage;

    public static Image getCharacterImage() {
        if (characterImage == null) {
            characterImage = new Image("images/character.png");
        }
        return characterImage;
    }

    public static Image getGrassImage() {
        if (grassImage == null) {
            grassImage = new Image("images/grass.png");
        }
        return grassImage;
    }

    public static Image getRockImage() {
        if (rockImage == null) {
            rockImage = new Image("images/stone.png");
        }
        return rockImage;
    }

    public static Image getWaterImage() {
        if (waterImage == null) {
            waterImage = new Image("images/water.png");
        }
        return waterImage;
    }

    public static Image getWallImage() {
        if (wallImage == null) {
            wallImage = new Image("images/wall.png");
        }
        return wallImage;
    }

    public static Image getSwordImage() {
        if (swordImage == null) {
            swordImage = new Image(""); //TODO find image
        }
        return swordImage;
    }

    public static Image getPickaxeImage() {
        if (pickaxeImage == null) {
            pickaxeImage = new Image(""); //TODO find image
        }
        return pickaxeImage;
    }

    public static Image getPotionImage() {
        if (potionImage == null) {
            potionImage = new Image(""); //TODO find image
        }
        return potionImage;
    }

    public static Image getShieldImage() {
        if (shieldImage == null) {
            shieldImage = new Image(""); //TODO find image
        }
        return shieldImage;
    }
}
