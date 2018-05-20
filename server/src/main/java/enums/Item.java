package enums;

public enum Item {
    NULL(0, "Empty", 7),
    SWORD(1, "Weapon", 40),
    PICKAXE(2, "Utility", 14),
    POTION(3, "Consumable", null),
    SHIELD(4, "Consumable", null);

    private int id;
    private String type;
    private Integer baseDamage;


    Item(int id, String type, Integer baseDamage) {
        this.id = id;
        this.type = type;
        this.baseDamage = baseDamage;
    }

    public static Item fromId(int id) {
        for (Item item : Item.values()) {
            if (item.id == id) {
                return item;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Integer getBaseDamage() {
        return baseDamage;
    }
}
