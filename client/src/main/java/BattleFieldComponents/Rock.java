package BattleFieldComponents;

import java.io.IOException;

public class Rock extends BattleFieldSquare {

    public Rock()  {
        super();
        addLayer(ImageOpener.getRockImage());
    }

    @Override
    public boolean canGoTo() {
        return false;
    }
}
