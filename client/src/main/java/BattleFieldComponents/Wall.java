package BattleFieldComponents;

import Scenes.BattleFieldScene;

public class Wall extends BattleFieldSquare{
    public Wall() {
        super();
        addLayer(BattleFieldScene.getOpenedImages().getWallImage());
    }
}
