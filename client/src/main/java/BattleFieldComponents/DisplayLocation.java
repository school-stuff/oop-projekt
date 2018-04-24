package BattleFieldComponents;

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
        if (userFullMapX > horisontalHalf() && userFullMapX < BattleFieldMap.mapSize()[1] - horisontalHalf()){
            return horisontalHalf();
        }
        if (userFullMapX >= BattleFieldMap.mapSize()[1] - horisontalHalf()) {
            return SQUARES_IN_COLUMNS - (BattleFieldMap.mapSize()[1] - userFullMapX);
        }
        return userFullMapX;
    }

    public int userY() {
        if (userFullMapY > verticalHalf() && userFullMapY < BattleFieldMap.mapSize()[0] - verticalHalf()) {
            return verticalHalf();
        }
        if (userFullMapY >= BattleFieldMap.mapSize()[0] - verticalHalf()) {
            return SQUARES_IN_ROW - (BattleFieldMap.mapSize()[0] - userFullMapY);
        }
        return userFullMapY;
    }

    public int leftColumnIndex() {
        if (userFullMapX < horisontalHalf()){
            return 0;
        }
        if ((BattleFieldMap.mapSize()[1] - horisontalHalf()) <= userFullMapX){
            return BattleFieldMap.mapSize()[1] - SQUARES_IN_COLUMNS;
        }
        return userFullMapX - horisontalHalf();
    }

    public int upperRowIndex() {
        if (userFullMapY < verticalHalf()) {
            return 0;
        }
        if ((BattleFieldMap.mapSize()[0] - verticalHalf()) <= userFullMapY){
            return BattleFieldMap.mapSize()[0] - SQUARES_IN_ROW;
        }
        return userFullMapY - verticalHalf();
    }

    public int squaresInRow() {
        return SQUARES_IN_ROW;
    }

    public int squaresInColumns() {
        return SQUARES_IN_COLUMNS;
    }

    public int getUserFullMapX() {
        return userFullMapX;
    }

    public int getUserFullMapY() {
        return userFullMapY;
    }

    private int verticalHalf() {
        return SQUARES_IN_ROW / 2;
    }

    private int horisontalHalf() {
        return SQUARES_IN_COLUMNS / 2;
    }

}
