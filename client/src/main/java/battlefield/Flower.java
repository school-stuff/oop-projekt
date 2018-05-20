package battlefield;

import enums.FlowerLifeCycle;
import services.FlowerStageHandler;

public class Flower extends BattleFieldSquare {

    private FlowerLifeCycle stage;
    public Flower() {
        super();
        stage = FlowerStageHandler.getInstance().getCurrentStage();
        addLayer(stage.getImage());
    }

    @Override
    public boolean canGoTo() {
        return stage == FlowerLifeCycle.POTION;
    }
}
