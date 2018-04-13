package BattleFieldComponents;

import Scenes.BattleFieldScene;

public class Wall extends BattleFieldSquare{
    public Wall(int locationX, int locationY) {
        super(locationX, locationY);
        addLayer(BattleFieldScene.getOpenedImages().getWallImage());
    }
}
