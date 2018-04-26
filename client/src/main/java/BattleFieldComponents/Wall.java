package BattleFieldComponents;

public class Wall extends BattleFieldSquare{
    public Wall() {
        super();
        addLayer(ImageOpener.getWallImage());
    }

    @Override
    public boolean canGoTo() {
        return false;
    }
}
