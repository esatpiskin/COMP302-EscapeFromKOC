package ui.drawable;

import model.game.data.Position;
import ui.drawable.data.DrawableProperties;
import model.game.data.DrawableHandle;
import ui.drawable.data.ZOrder;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

public class DrawableFactory {

    private static DrawableFactory instance = null;
    private final HashMap<DrawableHandle, Drawable> cache = new HashMap<>();

    private final int INTERPOLATION_STEPS = 5;


    public static DrawableFactory getInstance() {
        if (instance == null) {
            instance = new DrawableFactory();
        }
        return instance;
    }


    /**
     * Create or return a cached Drawable that is interpolated according to its movement
     * @param handle drawable handle or null to create a new handle
     * @param record properties to create or update the handle
     * @return the same drawable handle
     */
    public DrawableHandle getSingleTileCached(DrawableHandle handle, DrawableProperties record) {
        Drawable cached = null;
        // generate a new handle
        if (handle == null) {
            handle = new DrawableHandle();
        } else {
            cached = cache.remove(handle);
        }


        if (cached != null) {
            // update sprite TODO
            cached.image = record.sprite().getImage();
            // Update position
            cached.position = record.position();
            // apply interpolation
            cached.interpolate(record.facingOrMotion(), INTERPOLATION_STEPS);
            // change facing
            cached.updateFacing(record.facingOrMotion());
        } else if (!record.visible()) {
            cached = Drawable.invisible();
        } else {
            Image img = record.sprite().getImage();
            cached = new Drawable(record.position(), record.facingOrMotion(), ZOrder.Z_ORDER_FOREGROUND) {
                {
                    facing = record.facingOrMotion();
                    image = img;
                }
                @Override
                public void draw(Graphics g) {
                    g.drawImage(rotateImage(image, facing),
                            (int) (( getX() + this.getOffsetX(interpolationStep))*TILE_SIZE),
                            (int) (( getY() + this.getOffsetY(interpolationStep))*TILE_SIZE), TILE_SIZE, TILE_SIZE, null);
                }
            };
        }
        cache.put(handle, cached);
        return handle;
    }

    /**
     * Create a singular non-cached Drawable object given the properties
     * @param properties properties of the Drawable
     * @return the Drawable
     */
    public Drawable getSingleTileStatic(DrawableProperties properties) {
        return new SpriteRenderer(
                properties.position(),
                properties.facingOrMotion(),
                properties.sprite().getImage()
        );
    }

    /**
     * Fetch the Drawable given the handle, this enables interpolation operations
     * @param handle handle of the drawable
     * @return the handle
     */
    public Drawable fromHandle(DrawableHandle handle) {
        return cache.get(handle);
    }

    /**
     * Create a colored block
     * @param position position
     * @param color color
     * @return the Drawable
     */
    public Drawable rectangularFill(Position position, Color color) {
        return new Drawable(position) {
            @Override
            public void draw(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g.setColor(color);
                g2.fill(new Rectangle2D.Double(getX()*TILE_SIZE,
                        getY()*TILE_SIZE, TILE_SIZE, TILE_SIZE));
            }
        };
    }

    /**
     * Create a colored ball
     * @param position position
     * @param color color
     * @return the Drawable
     */
    public Drawable ellipticFill(Position position, Color color) {
        return new Drawable(position) {
            @Override
            public void draw(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g.setColor(color);
                g2.fill(new Ellipse2D.Double(getX()*TILE_SIZE,
                        getY()*TILE_SIZE, TILE_SIZE, TILE_SIZE));
            }
        };
    }
}
