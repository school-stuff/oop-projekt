package BattleFieldComponents;

import Scenes.BattleFieldScene;

import java.io.IOException;

public class Water extends BattleFieldSquare{
    public Water(int locationX, int getLocationY) throws IOException {
        super(locationX, getLocationY);
        addLayer(BattleFieldScene.getOpenedImages().getWaterImage());
    }
}
