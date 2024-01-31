package ui.drawable;

import model.game.data.Direction;
import ui.drawable.data.ZOrder;
import ui.drawable.sprite.Sprite;

import java.awt.*;

/**
 * Concrete Drawable instance for drawing buildings
 */
public class Building extends Drawable {

    private final int size_x;
    private final int size_y;

    // cache the images to spend less time on rotations
    private final Image wallImageTop = Sprite.SP_WALL.getImage();
    private final Image wallImageLeft = rotateImage(Sprite.SP_WALL.getImage(), Direction.RIGHT);
    private final Image wallImageRight = rotateImage(Sprite.SP_WALL.getImage(), Direction.LEFT);
    private final Image wallImageBottom = rotateImage(Sprite.SP_WALL.getImage(), Direction.UP);

    Color building_color = Color.WHITE;

    {
        this.zOrder = ZOrder.Z_ORDER_BACKGROUND;
    }

    /**
     * Construct a Building drawable with building width x and building height y
     * @param x width
     * @param y height
     */
    public Building(int x, int y) {
        size_x = x;
        size_y = y;
    }

    /**
     * Draw the building
     * @param g graphics context
     */
    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // background
        g2.setColor(building_color);
        g2.fillRect(
                0, //TILE_SIZE,
                0, //TILE_SIZE,
                size_x * TILE_SIZE,
                size_y * TILE_SIZE);


        for (int i = 0; i < size_x + 2; i++) {
            // top
            g.drawImage(wallImageTop,
                    //i * TILE_SIZE, 0, TILE_SIZE, TILE_SIZE, null);
            i * TILE_SIZE - TILE_SIZE, -TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
            // bottom
            g.drawImage(wallImageBottom,
                    //i * TILE_SIZE, (size_y + 1) * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
            i * TILE_SIZE - TILE_SIZE, (size_y) * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
        }
        for (int i = 1; i < size_y + 1; i++) {
            // left
            g.drawImage(wallImageLeft,
                    //0, i * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
            -TILE_SIZE, i * TILE_SIZE - TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
            // right
            g.drawImage(wallImageRight,
                    //(size_x + 1) * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
            (size_x) * TILE_SIZE, i * TILE_SIZE - TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
        }
    }
}
