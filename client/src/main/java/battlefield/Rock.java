package battlefield;

import enums.SquareTypes;
import scenes.ImageOpener;

public class Rock extends BattleFieldSquare {

    public Rock()  {
        super();
        addLayer(ImageOpener.getImage("rock"));
    }

    @Override
    public boolean canGoTo() {
        return false;
    }
}
