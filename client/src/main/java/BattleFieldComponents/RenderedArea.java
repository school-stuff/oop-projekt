package BattleFieldComponents;

public class RenderedArea {
    private static final int SQUARES_IN_ROWS = 11;
    private static final int SQUARES_IN_COLUMNS = 11;

    private final int userFullMapX;
    private final int userFullMapY;
    private final boolean userInTheMiddle;

    public RenderedArea(int userFullMapX, int userFullMapY) {
        this.userFullMapX = userFullMapX;
        this.userFullMapY = userFullMapY;
        userInTheMiddle = isUserInTheMiddle();
    }

    public int userX() {
        if (isNearRightSide()) {
            return userFullMapX - leftColumnIndex();

        }
        if (isNearLeftSide()) {
            return userFullMapX;
        }
        return horisontalCenter();
    }

    public int userY() {
        if (isNearBottom()) {
            return userFullMapY - upperRowIndex();
        }
        if (isNearUpperRow()) {
            return userFullMapY;
        }
        return verticalCenter();
    }

    public int leftColumnIndex() {
        if (isNearRightSide()) {
            return BattleFieldMap.width() - SQUARES_IN_COLUMNS;
        }
        if (isNearLeftSide()) {
            return 0;
        }
        return userFullMapX - horisontalCenter();
    }

    public int upperRowIndex() {
        if (isNearBottom()) {
            return BattleFieldMap.heigth() - SQUARES_IN_ROWS;
        }
        if (isNearUpperRow()) {
            return 0;
        }
        return userFullMapY - verticalCenter();
    }

    public int squaresInRow() {
        return SQUARES_IN_ROWS;
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

    private boolean isNearLeftSide() {
        return userFullMapX < horisontalCenter();
    }

    private boolean isNearRightSide() {
        return userFullMapX > BattleFieldMap.width() - horisontalCenter() - 1;
    }

    private boolean isNearBottom() {
        return userFullMapY > BattleFieldMap.heigth() - 1 - verticalCenter();
    }

    private boolean isNearUpperRow() {
        return userFullMapY < verticalCenter();
    }

    private boolean isUserInTheMiddle() {
        return !isNearBottom() && !isNearUpperRow() && !isNearRightSide() && !isNearLeftSide();
    }

    private int verticalCenter() {
        return (SQUARES_IN_ROWS - 1) / 2;
    }

    private int horisontalCenter() {
        return (SQUARES_IN_COLUMNS - 1) / 2;
    }
}
