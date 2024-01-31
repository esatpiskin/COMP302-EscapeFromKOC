package ui.drawable;

import model.game.data.Direction;
import model.game.data.Position;
import ui.drawable.data.ZOrder;

import java.awt.*;

/**
 * SpriteRenderer is a simple class for drawing a single image
 */
public class SpriteRenderer extends Drawable {

    {
        zOrder = ZOrder.Z_ORDER_FURNITURE;
    }

    /**
     * Create a new SpriteRenderer object at position facing direction with the given image
     * @param position the position
     * @param direction the direction
     * @param image image to draw
     */
    public SpriteRenderer(Position position, Direction direction, Image image) {
        super(position, direction);
        this.image = rotateImage(image, direction);
    }

    /**
     * Create a new SpriteRenderer object at position with the given image
     * @param position the position
     * @param image image to draw
     */
    public SpriteRenderer(Position position, Image image) {
        super(position, Direction.STILL, ZOrder.Z_ORDER_FOREGROUND);
        this.image = image;
    }

    /**
     * Draw the image
     * @param g graphics context
     */
    @Override
    public void draw(Graphics g) {

        g.drawImage(image,
                getX()*TILE_SIZE, getY()*TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
    }
}
