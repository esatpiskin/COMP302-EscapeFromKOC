package ui.drawable.data;

import model.game.data.Direction;
import model.game.data.Position;
import ui.drawable.sprite.Sprite;

/**
 * Drawable properties record is for passing information to DrawableFactory
 * @param sprite sprite to use
 * @param position position of the object
 * @param facingOrMotion facing for static objects, motion for dynamic objects
 * @param zOrder z order preference
 * @param visible visible or not
 */
public record DrawableProperties(Sprite sprite,
                                 Position position,
                                 Direction facingOrMotion,
                                 ZOrder zOrder,
                                 boolean visible) {


    public DrawableProperties(Sprite sprite, Position position, Direction facing) {
        this(sprite, position, facing, ZOrder.Z_ORDER_FURNITURE, true);
    }

    public static DrawableProperties playerProperties(Sprite sprite, Position position, Direction motionVector) {
        return new DrawableProperties(sprite, position, motionVector, ZOrder.Z_ORDER_FOREGROUND, true);
    }

    public static DrawableProperties invisible() {
        return new DrawableProperties(Sprite.NULL, new Position(0, 0), Direction.STILL, ZOrder.Z_ORDER_BACKGROUND, false);
    }
}
