package enums;

import javafx.scene.image.Image;
import scenes.ImageOpener;

public enum Item {
    NULL(0, "Empty", null),
    SWORD(1, "Weapon", "sword"),
    PICKAXE(2, "Utility", "pickaxe"),
    POTION(3, "Consumable", "potion"),
    SHIELD(4, "Consumable", "shield");

    private int id;
    private String type;
    private String image;

    Item(int id, String type, String image) {
        this.id = id;
        this.type = type;
        this.image = image;
    }

    public static Item fromId(int id)  {
        for (Item item : Item.values()) {
            if (item.id == id) {
                return item;
            }
        }
        throw new RuntimeException("Item with id value " + id + "does not exist");
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getImageType() {
        return image;
    }


}
