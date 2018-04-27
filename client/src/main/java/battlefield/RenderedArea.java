package battlefield;

public class RenderedArea {
    private static final int SQUARES_IN_ROWS = 11;
    private static final int SQUARES_IN_COLUMNS = 11;

    private final int playerX;
    private final int playerY;

    public RenderedArea(int playerX, int playerY) {
        this.playerX = playerX;
        this.playerY = playerY;
    }

    public int renderedX() {
        if (isNearRightSide()) {
            return playerX - leftColumnIndex();

        }
        if (isNearLeftSide()) {
            return playerX;
        }
        return horisontalCenter();
    }

    public int renderedY() {
        if (isNearBottom()) {
            return playerY - upperRowIndex();
        }
        if (isNearUpperRow()) {
            return playerY;
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
        return playerX - horisontalCenter();
    }

    public int upperRowIndex() {
        if (isNearBottom()) {
            return BattleFieldMap.heigth() - SQUARES_IN_ROWS;
        }
        if (isNearUpperRow()) {
            return 0;
        }
        return playerY - verticalCenter();
    }

    public int squaresInRow() {
        return SQUARES_IN_ROWS;
    }

    public int squaresInColumns() {
        return SQUARES_IN_COLUMNS;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    private boolean isNearLeftSide() {
        return playerX < horisontalCenter();
    }

    private boolean isNearRightSide() {
        return playerX > BattleFieldMap.width() - horisontalCenter() - 1;
    }

    private boolean isNearBottom() {
        return playerY > BattleFieldMap.heigth() - 1 - verticalCenter();
    }

    private boolean isNearUpperRow() {
        return playerY < verticalCenter();
    }

    private int verticalCenter() {
        return (SQUARES_IN_ROWS - 1) / 2;
    }

    private int horisontalCenter() {
        return (SQUARES_IN_COLUMNS - 1) / 2;
    }
}
