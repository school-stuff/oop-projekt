package BattleFieldComponents;

import Scenes.BattleFieldScene;

import java.io.IOException;

public class Water extends BattleFieldSquare{
    public Water() {
        super();
        addLayer(BattleFieldScene.getOpenedImages().getWaterImage());
    }
}
