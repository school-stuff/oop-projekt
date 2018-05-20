package scenes;

import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageOpener {
    private static Map<String, Image> images = new HashMap<>();
    private static List<String> fileNames = Arrays.asList("character.png", "grass.png", "rock.png", "water.png", "wall.png", "sword.png", "pickaxe.png", "potion.png", "shield.png");

    public static Image getImage(String imageType) {
        String filename = imageType + ".png";
        if (!fileNames.contains(filename)) {
            throw new RuntimeException("Imagefile image/" + filename + " not found!");
        }

        if (!images.containsKey(filename)) {
            Image image = new Image("images/" + filename);
            images.put(filename, image);
            return image;
        }
        return images.get(filename);
    }
}
