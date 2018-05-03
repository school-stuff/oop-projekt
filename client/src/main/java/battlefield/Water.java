package battlefield;

import scenes.ImageOpener;

public class Water extends BattleFieldSquare{
    public Water()  {
        super();
        addLayer(ImageOpener.getWaterImage());
    }

    @Override
    public boolean canGoTo() {
        return false;
    }
}
