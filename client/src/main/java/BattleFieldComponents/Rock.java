package BattleFieldComponents;

import Scenes.BattleFieldScene;

public class Rock extends BattleFieldSquare {

    public Rock() {
        super();
        addLayer(BattleFieldScene.getOpenedImages().getRockImage());
    }
}
