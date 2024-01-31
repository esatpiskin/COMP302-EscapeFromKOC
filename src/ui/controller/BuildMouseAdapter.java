package ui.controller;

import model.game.data.Position;
import model.game.data.Size;
import ui.UIController;
import ui.ViewModel;

import java.awt.event.MouseEvent;

/**
 * <pre>
 * This will be sent to the animator on the build screen
 * Left click: place selected furniture
 * Right click: clear furniture
 * Left click on walls: move door
 * Drag the lower right corner: resize building
 * </pre>
 */
public class BuildMouseAdapter extends GameInputAdapter {

    private final ViewModel viewModel = ViewModel.getInstance();
    private final UIController controller = viewModel.getController();
    private boolean held;

    /**
     * Handles placing furniture, removing furniture, and moving the door
     * @param e mouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int tile_size = viewModel.getTileSize();
        var button = e.getButton();
        var bounds = viewModel.getBuildingBounds();
        var position = new Position((e.getX() - getOffsetX()) / tile_size, (e.getY() - getOffsetY()) / tile_size);
        if ((position.x() == 0 && position.y() < bounds.y()) || // top
                (position.y() == 0 && position.x() < bounds.x()) || // left
            (position.y() < bounds.y() && position.x() == bounds.x()) || // right
                (position.x() < bounds.x() && position.y() == bounds.y()) // bottom
        ) {
            switch (button) {
                case MouseEvent.BUTTON1 -> controller.moveExitDoor(new Position(position.x() -1 , position.y() -1));
                case MouseEvent.BUTTON3 -> controller.moveEntryDoor(new Position(position.x() -1 , position.y() -1));
            }
        } else {
            switch (button) {
                case MouseEvent.BUTTON1 -> controller.addFurniture(new Position(position.x() -1 , position.y() -1));
                case MouseEvent.BUTTON3 -> controller.removeFurniture(new Position(position.x() -1 , position.y() -1));
            }
        }
    }

    /**
     * Handles building resizing
     * @param e mouseEvent
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        int tile_size = viewModel.getTileSize();
        if (!held) {
            var bounds = viewModel.getBuildingBounds();
            var position = new Position((e.getX() - getOffsetX()) / tile_size, (e.getY() - getOffsetY()) / tile_size);
            if (bounds.equals(position)) {
                held = true;
            }
        } else {
            var position = new Size((e.getX() - getOffsetX()) / tile_size - 1, (e.getY() - getOffsetY()) / tile_size - 1);
            controller.changeBuildingSize(position);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        held = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {
        held = false;
    }
}
