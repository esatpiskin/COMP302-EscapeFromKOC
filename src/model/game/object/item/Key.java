package model.game.object.item;

import model.game.data.Direction;
import model.game.data.IntoDrawable;
import model.game.data.Position;
import model.game.object.environment.ILocked;
import ui.drawable.Drawable;
import ui.drawable.DrawableFactory;
import ui.drawable.data.DrawableProperties;
import ui.drawable.sprite.Sprite;

/**
 * The Key class unlocks an ILocked object when it is picked up
 */
public class Key implements IntoDrawable {
    private Position position;
    private boolean visible;
    private boolean isEnabled;
    private boolean picked;
    private ILocked lock;

    /**
     * Construct a new Key object with the given position and lock
     * @param position the position
     * @param lock object to unlock when picked up
     */
    public Key(Position position, ILocked lock) {
        this.isEnabled = true;
        this.lock = lock;
        this.position = position;
        this.visible = false;
        this.picked = false;
    }

    /**
     * Get the position of the Key
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Set the position of the key
     * @param position new position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Get the lock of the key
     * @return the lock
     */
    public ILocked getLock() {
        return lock;
    }

    /**
     * Change the lock of the key, should probably not be changed when the game is running
     * @param lock new lock
     */
    public void setLock(ILocked lock) {
        this.lock = lock;
    }

    /**
     * check if the key is currently visible
     * @return the visibility
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Set the key's visibility
     * @param visible new visibility
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Check if the Key is enabled
     * @return if the key is enabled
     */
    public boolean isEnabled() {
        return isEnabled;
    }

    /**
     * Set the Enabled status
     * @param enabled new status
     */
    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    /**
     * Get if the key is picked up
     * @return true if already picked up, false otherwise
     */
    public boolean isPicked() {
        return picked;
    }

    /**
     * Pickup the key and call unlock() on its ILocked object <br>
     * Subsequent calls to this method does nothing
     */
    public void pickUp() {
        if (isEnabled()) {
            picked = true;
            lock.unlock();
            setPosition(new Position(-1, -1));
        }
    }

    /**
     * Get the Drawable
     * The key should normally be invisible, unless it is made visible externally
     * TODO: implement this
     * @return the Drawable
     */
    @Override
    public Drawable intoDrawable() {
        // TODO: hide the key based on isVisible
        if (isEnabled()) {
            Sprite sprite = Sprite.ITEM_KEY;

            return DrawableFactory.getInstance().getSingleTileStatic(new DrawableProperties(
                    sprite, this.position, Direction.STILL
            ));
        } else {
            return Drawable.invisible();
        }
    }
}
