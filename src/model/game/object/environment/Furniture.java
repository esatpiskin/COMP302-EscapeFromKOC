package model.game.object.environment;

import model.game.data.Direction;
import model.game.data.IntoDrawable;
import model.game.data.Position;
import ui.drawable.Drawable;
import ui.drawable.DrawableFactory;
import ui.drawable.data.DrawableProperties;
import ui.drawable.sprite.Sprite;

/**
 * Static objects in the play field
 */
public class Furniture implements IntoDrawable {

    private final Position position;
    private final FurnitureType furnitureType;

    /**
     * Create a new Furniture object with given furniture type and position
     * @param position the position
     * @param name the furniture type
     */
    public Furniture(Position position , FurnitureType name) {
        this.position = position;
        this.furnitureType = name;
    }

    /**
     * Get the position of the furniture
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Get the furniture type
     * @return the furniture type
     */
    public FurnitureType getFurnitureType() {
        return furnitureType;
    }

    /**
     * Get the Drawable associated with the furniture object <br>
     * The Drawable object will be different based on the type of furniture
     * @return the Drawable
     */
    @Override
    public Drawable intoDrawable() {
        Sprite sprite = furnitureType.getSprite();

        return DrawableFactory.getInstance().getSingleTileStatic(new DrawableProperties(
                sprite, this.position, Direction.STILL
        ));
    }
}
