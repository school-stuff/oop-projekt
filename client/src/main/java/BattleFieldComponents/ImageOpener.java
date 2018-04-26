package BattleFieldComponents;

import javafx.scene.image.Image;

import java.io.*;

public class ImageOpener {
    private static Image grassImage;
    private static Image rockImage;
    private static Image characterImage;
    private static Image waterImage;
    private static Image wallImage;

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
