package battlefield;

import enums.FlowerLifeCycle;
import services.FlowerGardener;

public class Flower extends BattleFieldSquare {

    private FlowerLifeCycle stage;
    public Flower() {
        super();
        stage = FlowerGardener.getInstance().getCurrentStage();
        addLayer(stage.getImage());
    }

    @Override
    public boolean canGoTo() {
        return stage == FlowerLifeCycle.POTION;
    }
}
