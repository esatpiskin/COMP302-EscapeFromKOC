package model.game.object.environment;

import model.game.data.Direction;
import model.game.data.IntoDrawable;
import model.game.data.Position;
import ui.drawable.ColoredBlock;
import ui.drawable.Drawable;
import ui.drawable.DrawableFactory;
import ui.drawable.data.DrawableProperties;
import ui.drawable.sprite.Sprite;

import java.awt.*;

/**
 * <pre>
 * Each building has a door that leads to another building
 * Doors are responsible for unlocking themselves, but they are not responsible for providing the building they lead to
 * </pre>
 */
public class Door implements IntoDrawable, ILocked {

    private Position position;
    private boolean isOpen;

    /**
     * Create a new door at position relative to the building <br>
     * The caller needs to ensure that the Door is in a valid
     * position
     * @param position position of the door relative to the building
     */
    public Door(Position position) {
        this.position = position;
        this.isOpen = false;
    }

    /**
     * Get the position of the door
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Set the position of the door
     * @param position new position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Close the door, only expected to be called if the game is restarted
     */
    public void close() {
        this.isOpen = false;
    }

    /**
     * Check if the door is open
     * @return true if the door is open, false otherwise
     */
    public boolean isOpen() {
        return isOpen;
    }


    /**
     * Get the door's Drawable <br>
     * Returned Drawable will reflect whether the door is open or closed
     * @return the Drawable
     */
    @Override
    public Drawable intoDrawable() {
        Sprite sprite = Sprite.SP_DOOR;

        if (isOpen()) {
            Sprite sprite2 = Sprite.SP_OPEN_DOOR;
            return DrawableFactory.getInstance().getSingleTileStatic(new DrawableProperties(
                    sprite2, this.position, Direction.STILL));
        }


        return DrawableFactory.getInstance().getSingleTileStatic(new DrawableProperties(
                sprite, this.position, Direction.STILL
        ));
    }

    /**
     * Unlock the door
     */
    @Override
    public void unlock() {
        this.isOpen = true;
    }
}
