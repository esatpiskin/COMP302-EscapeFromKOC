package ui.controller;

import model.game.data.Direction;
import ui.UIController;
import ui.ViewModel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * <pre>
 * TODO: implement powerups
 * This will be sent to the animator on the game screen
 *
 * Inputs to be handled:
 * Arrow keys - movement
 * H - use hint power up
 * P - use protection vest power up
 *
 * B - equip plastic bottle power up
 * ADWX - throw plastic bottle (after equipping)
 * </pre>
 */
public class GameKeyListener implements KeyListener {
    private final UIController controller = ViewModel.getInstance().getController();

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO: handle diagonal holds
        int key = e.getKeyCode();

        // ESC to pause
        if (key == KeyEvent.VK_ESCAPE) {
            controller.ShowPauseScreen();
        }

        if (key == KeyEvent.VK_UP) {
            controller.movePlayer(Direction.UP);

        }
        if (key == KeyEvent.VK_DOWN) {
            controller.movePlayer(Direction.DOWN);

        }
        if (key == KeyEvent.VK_LEFT) {
            controller.movePlayer(Direction.LEFT);

        }
        if (key == KeyEvent.VK_RIGHT) {
            controller.movePlayer(Direction.RIGHT);
        }

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
