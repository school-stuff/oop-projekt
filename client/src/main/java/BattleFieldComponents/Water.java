package BattleFieldComponents;

import Scenes.BattleFieldScene;

import java.io.IOException;

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
