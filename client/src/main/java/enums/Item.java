package enums;

import javafx.scene.image.Image;
import scenes.ImageOpener;

public enum Item {
    NULL(0, "Empty", null),
    SWORD(1, "Weapon", ImageOpener.getImage("sword")),
    PICKAXE(2, "Utility", ImageOpener.getImage("pickaxe")),
    POTION(3, "Consumable", ImageOpener.getImage("potion")),
    SHIELD(4, "Consumable", ImageOpener.getImage("shield"));

    private int id;
    private String type;
    private Image image;

    Item(int id, String type, Image image) {
        this.id = id;
        this.type = type;
        this.image = image;
    }

    public static Item fromId(int id) throws Exception {
        for (Item item : Item.values()) {
            if (item.id == id) {
                return item;
            }
        }
        throw new Exception();
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Image getImage() {
        return image;
    }
}
