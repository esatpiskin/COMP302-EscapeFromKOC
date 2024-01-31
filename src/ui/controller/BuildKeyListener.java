package ui.controller;

import ui.UIController;
import ui.ViewModel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Key listener for Build Mode <br>
 * Right now, this class is not expected to implement any behavior, as there are no key stroke commands in build
 * mode
 */
public class BuildKeyListener implements KeyListener {

    UIController controller = ViewModel.getInstance().getController();

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        var key = e.getKeyCode();
        // quick save
        if (key == KeyEvent.VK_F5) {
            controller.saveGame();
        }
        // quick load
        if (key == KeyEvent.VK_F8) {
            controller.loadGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
