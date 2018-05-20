package battlefield;

import enums.FlowerLifeCycle;
<<<<<<< HEAD
import services.FlowerGardener;

public class Flower extends BattleFieldSquare {

    private FlowerLifeCycle stage;
    public Flower() {
        super();
        stage = FlowerGardener.getInstance().getCurrentStage();
        addLayer(stage.getImage());
=======
import services.FlowerService;

public class Flower extends BattleFieldSquare {
    private FlowerLifeCycle stage = FlowerLifeCycle.GROWING;

    public Flower() {
        super();
        addLayer(FlowerService.getInstance().getCurrentStage().getImage());
>>>>>>> aea0aba6b4d634c771bf16742cc029a5ba2a4247
    }

    @Override
    public boolean canGoTo() {
        return stage == FlowerLifeCycle.POTION;
    }
}
