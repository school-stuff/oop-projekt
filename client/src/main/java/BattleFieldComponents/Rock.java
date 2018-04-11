package BattleFieldComponents;

import Scenes.BattleFieldScene;

import java.io.IOException;

public class Rock extends BattleFieldSquare {

    public Rock(int locationX, int getLocationY) throws IOException {
        super(locationX, getLocationY);
        addLayer(BattleFieldScene.getOpenedImages().getRockImage());
    }
}
