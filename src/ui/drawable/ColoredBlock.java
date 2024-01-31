package ui.drawable;

import model.game.data.Position;
import ui.drawable.data.ZOrder;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Colored Block drawable
 */
public class ColoredBlock extends Drawable {

    private final Color color;

    {
        zOrder = ZOrder.Z_ORDER_FURNITURE;
    }

    /**
     * Construct a Colored Block object
     * @param position position
     * @param color color
     */
    public ColoredBlock(Position position, Color color) {
        super(position);
        this.color = color;
    }

    /**
     * Draw the colored block
     * @param g graphics context
     */
    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(color);
        g2.fill(new Rectangle2D.Double(getX()*TILE_SIZE, getY()*TILE_SIZE, TILE_SIZE, TILE_SIZE));
    }
}
