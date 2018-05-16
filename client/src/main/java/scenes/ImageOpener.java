package scenes;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class ImageOpener {
    private static Map<String, Image> images = new HashMap<>();
    static {
        String[] fileNames = {"images/character.png", "images/grass.png", "images/rock.png", "images/water.png", "images/wall.png"};
        for (String name : fileNames) {
            images.put(name, null);
        }
    }

    public static Image getImage(String imageType) {
        String filename = "images/" + imageType + ".png";
        if (!images.containsKey(filename)) {
            throw new RuntimeException("Imagefile " + filename + " not found!");
        }

        if (images.get(filename) == null) {
            Image image = new Image(filename);
            images.put(filename, image);
            return image;
        }
        return images.get(filename);
    }
}
