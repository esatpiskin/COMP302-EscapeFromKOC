package ui.drawable;

import model.game.data.Position;
import ui.drawable.data.ZOrder;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * ColoredBall Drawable
 */
public class ColoredBall extends Drawable {
    private final Color color;

    /**
     * Create a colored ball object
     * @param position position
     * @param color color
     */
    public ColoredBall(Position position,Color color) {
        // it would be better if we used some sort of external offset instead of this hack
        super(position);
        this.color = color;

    }

    {
        this.zOrder = ZOrder.Z_ORDER_FOREGROUND;
    }

    /**
     * Draw the colored ball
     * @param g graphics context
     */
    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(color);
        g2.fill(new Ellipse2D.Double(getX()*TILE_SIZE,
                getY()*TILE_SIZE, TILE_SIZE, TILE_SIZE));
    }

}
