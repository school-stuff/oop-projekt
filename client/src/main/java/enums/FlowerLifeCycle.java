package enums;

import javafx.scene.image.Image;

public enum FlowerLifeCycle {
    GROWING (1, null),
    SPREADING (2, null),
    POTION (3, null);

    private int cycle;
    private Image image;

    FlowerLifeCycle(int cycle, Image image) {
        this.cycle = cycle;
        this.image = image;
    }

    public static FlowerLifeCycle getCycle(int stage){
        for(FlowerLifeCycle cycle : FlowerLifeCycle.values()){
            if(cycle.getCycle() == stage) return cycle;
        }
        throw new RuntimeException("FlowerCycle stage " + stage + " is not an accepted value.");
    }

    public int getCycle() {
        return cycle;
    }

    public Image getImage() {
        return image;
    }
}
