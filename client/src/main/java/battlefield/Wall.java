package battlefield;

import enums.SquareTypes;
import scenes.ImageOpener;

public class Wall extends BattleFieldSquare{
    public Wall() {
        super();
        addLayer(ImageOpener.getImage("wall"));
    }

    @Override
    public boolean canGoTo() {
        return false;
    }
}
