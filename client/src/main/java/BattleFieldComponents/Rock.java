package BattleFieldComponents;

import Scenes.BattleFieldScene;

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
