package BattleFieldComponents;

//this class calculates displayable map info from user Location
public class DisplayLocation {
    private static final int SQUARES_IN_ROW = 11;
    private static final int SQUARES_IN_COLUMNS = 11;

    private int userFullMapX;
    private int userFullMapY;

    public DisplayLocation(int userFullMapX, int userFullMapY) {
        this.userFullMapX = userFullMapX;
        this.userFullMapY = userFullMapY;
    }

    public int userX(){
        if (inTheMiddleOfMap()) {
            return horisontalHalf();
        }
        return userFullMapX;
    }

    public int userY() {
        if (inTheMiddleOfMap()) {
            return verticalHalf();
        }
        return userFullMapY;
    }

    public int leftColumnIndex() {
        if (inTheMiddleOfMap()) {
            return userFullMapX - verticalHalf();
        }
        return 0;
    }

    public int upperRowIndex() {
        if (inTheMiddleOfMap()) {
            return userFullMapY - horisontalHalf();
        }
        return 0;
    }

    public int squaresInRow() {
        return SQUARES_IN_ROW;
    }

    public int squaresInColumns() {
        return SQUARES_IN_COLUMNS;
    }

    private boolean inTheMiddleOfMap(){
        return userFullMapY >= verticalHalf() && userFullMapX >= horisontalHalf();
        //TODO: add the end point of map
    }

    private int verticalHalf() {
        return SQUARES_IN_ROW / 2;
    }

    private int horisontalHalf() {
        return SQUARES_IN_COLUMNS / 2;
    }

}
