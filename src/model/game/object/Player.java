package model.game.object;

import model.game.data.Direction;
import model.game.data.DrawableHandle;
import model.game.data.IntoDrawable;
import model.game.data.Position;
import ui.drawable.*;
import ui.drawable.data.DrawableProperties;
import ui.drawable.sprite.Sprite;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The player object encapsulates state and logic regarding the status of the player in the field <br>
 * The player
 */
public class Player implements IntoDrawable {

    private Position position;
    private int health;
    private Direction lastMove = Direction.STILL;
    private DrawableHandle handle;

    private final Logger logger = Logger.getLogger("Player");

    public Player(Position initialPos) {
        this.position = initialPos;
        this.health = 3;
    }

    /**
     * @param direction enumerated direction to go to
     * @return updated position
     */
    public Position move(Direction direction) {
        position = position.move(direction);
        lastMove = direction;
        return position;
    }

    /**
     * Get the position of the player
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Set the position of the player
     * @param position new position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Get the health of the player
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Set the health of the player
     * @param health new health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * reduce health by one
     */
    public void reduceHealth() {
        health -= 1;
        logger.log(Level.INFO, "Player health: " + health);
    }

    /**
     * Modify lastMove, useful when trying to animate the Player after
     * using setPosition
     * @param lastMove modify the "last movement"
     */
    public void setLastMove(Direction lastMove) {
        this.lastMove = lastMove;
    }

    /**
     * increase health by one
     * TODO: max health?
     */
    public void restoreHealth() {
        health += 1;
    }

    /**
     * Get the Player's Drawable
     * @return the Drawable
     */
    @Override
    public Drawable intoDrawable() {
        var factory = DrawableFactory.getInstance();
        handle = factory.getSingleTileCached(handle, DrawableProperties.playerProperties(Sprite.PLAYER, this.position, this.lastMove));
        this.lastMove = Direction.STILL;
        return factory.fromHandle(handle);
    }

    /**
     * Attack the player by shooting at it
     */
    public void shoot() {
        // TODO: Vest power up negates
        this.reduceHealth();
    }

    /**
     * Attack the player by strangling it
     */
    public void strangle() {
        this.reduceHealth();
    }
}
