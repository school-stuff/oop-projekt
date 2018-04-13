package BattleFieldComponents;

import Scenes.BattleFieldScene;

public class Rock extends BattleFieldSquare {

    public Rock(int locationX, int getLocationY) {
        super(locationX, getLocationY);
        addLayer(BattleFieldScene.getOpenedImages().getRockImage());
    }
}
