package ui.controller;

import model.game.data.Position;
import ui.UIController;
import ui.ViewModel;

import java.awt.event.MouseEvent;

/**
 * <pre>
 * This will be sent to the animator on the game screen
 * Inputs to be handled:
 * Left click (BUTTON1): grab key (if clicked on a key)
 * Right click (BUTTON3): grab power up
 * </pre>
 */
public class GameMouseAdapter extends GameInputAdapter {

    private final ViewModel viewModel = ViewModel.getInstance();
    private final UIController controller = viewModel.getController();

    @Override
    public void mouseClicked(MouseEvent e) {
        int tile_size = viewModel.getTileSize();
        var button = e.getButton();
        var position = new Position((e.getX() - getOffsetX()) / tile_size - 1, (e.getY() - getOffsetY()) / tile_size - 1);
        switch (button) {
            case MouseEvent.BUTTON1 -> controller.pickUpKey(position);
            case MouseEvent.BUTTON3 -> controller.pickUpPowerUp(position);
        }
        if (button == MouseEvent.BUTTON1) {
            controller.findSafe(position);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
