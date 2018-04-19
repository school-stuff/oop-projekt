package BattleFieldComponents;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Action extends AbstractAction {
    private int newX;
    private int newY;

    public Action(int newX, int newY) {
        this.newX = newX;
        this.newY = newY;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (new BattleFieldMap().canGoToSquare(newX, newY)) {
            //send server request
        }
    }
}
