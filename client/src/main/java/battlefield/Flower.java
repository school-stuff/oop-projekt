package battlefield;

import enums.FlowerLifeCycle;
import services.FlowerService;

public class Flower extends BattleFieldSquare {
    private FlowerLifeCycle stage = FlowerLifeCycle.GROWING;

    public Flower() {
        super();
        addLayer(FlowerService.getInstance().getCurrentStage().getImage());
    }

    @Override
    public boolean canGoTo() {
        return stage == FlowerLifeCycle.POTION;
    }
}
