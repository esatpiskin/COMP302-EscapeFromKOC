package model.game.object.environment;


import model.game.data.Direction;
import model.game.data.IntoDrawable;
import model.game.data.Position;
import model.game.object.item.Key;
import ui.drawable.Drawable;
import ui.drawable.DrawableFactory;
import ui.drawable.data.DrawableProperties;
import ui.drawable.sprite.Sprite;

public class Safe implements IntoDrawable, ILocked {

    private Position position;
    private boolean isOpen;

    private boolean isVisible = false;
    private Key keyInside;

    public Safe(Position position, Key keyInside) {
        this.position = position;
        this.isOpen = false;
        this.keyInside = keyInside;
        this.keyInside.setEnabled(false);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Key getKeyInside() {
        return keyInside;
    }

    public void setKeyInside(Key keyInside) {
        this.keyInside = keyInside;
    }

    public boolean isOpen() {
        return isOpen;
    }
    public boolean getVisible() {
        return isVisible;
    }


    @Override
    public Drawable intoDrawable() {
        Sprite sprite;
        if (isOpen()){
            sprite = Sprite.FURNITURE_SAFE_OPEN;
        } else {
            sprite = Sprite.FURNITURE_SAFE;
        }
        return DrawableFactory.getInstance().getSingleTileStatic(new DrawableProperties(
                sprite, this.position, Direction.STILL));
    }

    @Override
    public void unlock() {
        this.isOpen = true;
        this.keyInside.setPosition(position);
        this.keyInside.setEnabled(true);
    }
    public void findSafe() {
        this.isVisible = true;
    }
}
