package BattleFieldComponents;

public class DisplayLocation {
    private static final int SQUARES_IN_ROW = 11;
    private static final int SQUARES_IN_COLUMNS = 11;

    private int x;
    private int y;

    public DisplayLocation(int userX, int userY) {
        this.x = userX;
        this.y = userY;
    }

    public int userX(){
        if (inTheMiddleOfMap()) {
            return horisontalHalf();
        }
        return x;
    }

    public int userY() {
        if (inTheMiddleOfMap()) {
            return verticalHalf();
        }
        return y;
    }

    public boolean inTheMiddleOfMap(){
        return y >= verticalHalf() && x >= horisontalHalf();
        //TODO: add the end point of map
    }

    public int leftColumnIndex() {
        if (inTheMiddleOfMap()) {
            return x - verticalHalf();
        }
        return 0;
    }

    public int upperRowIndex() {
        if (inTheMiddleOfMap()) {
            return y - horisontalHalf();
        }
        return 0;
    }

    private int verticalHalf() {
        return SQUARES_IN_ROW / 2;
    }

    private int horisontalHalf() {
        return SQUARES_IN_COLUMNS / 2;
    }

}
