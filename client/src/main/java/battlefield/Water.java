package battlefield;

import enums.SquareTypes;
import scenes.ImageOpener;

public class Water extends BattleFieldSquare{
    public Water()  {
        super();
        addLayer(ImageOpener.getImage("water"));
    }

    @Override
    public boolean canGoTo() {
        return false;
    }
}
