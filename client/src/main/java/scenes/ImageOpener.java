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
            characterImage = getImageFromFile("client/src/main/resources/images/character.png");
        }
        return characterImage;
    }

    public static Image getGrassImage() {
        if (grassImage == null) {
            grassImage = getImageFromFile("client/src/main/resources/images/grass.png");
        }
        return grassImage;
    }

    public static Image getRockImage() {
        if (rockImage == null) {
            rockImage = getImageFromFile("client/src/main/resources/images/stone.png");
        }
        return rockImage;
    }

    public static Image getWaterImage() {
        if (waterImage == null) {
            waterImage = getImageFromFile("client/src/main/resources/images/water.png");
        }
        return waterImage;
    }

    public static Image getWallImage() {
        if (wallImage == null) {
            wallImage = getImageFromFile("client/src/main/resources/images/wall.png");
        }
        return wallImage;
    }

    public static Image getSwordImage() {
        if (swordImage == null) {
            swordImage = getImageFromFile(""); //TODO find image
        }
        return swordImage;
    }

    public static Image getPickaxeImage() {
        if (pickaxeImage == null) {
            pickaxeImage = getImageFromFile(""); //TODO find image
        }
        return pickaxeImage;
    }

    public static Image getPotionImage() {
        if (potionImage == null) {
            potionImage = getImageFromFile(""); //TODO find image
        }
        return potionImage;
    }

    public static Image getShieldImage() {
        if (shieldImage == null) {
            shieldImage = getImageFromFile(""); //TODO find image
        }
        return shieldImage;
    }

    private static Image getImageFromFile(String file) {
        try (InputStream inputStream = new FileInputStream(new File(file))) {
            return new Image(inputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File " + file + "not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
