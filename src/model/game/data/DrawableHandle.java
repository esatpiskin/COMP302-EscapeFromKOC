package model.game.data;

/**
 * Helper class to cache information about a drawable's state. <br>
 * This object is used with DrawableFactory primarily to make movement interpolation possible, but it has to be stored
 * inside the relevant domain object. Do not use this for objects whose position are not expected to be changed
 * smoothly.
 */
public class DrawableHandle {
    protected Long uid;
    protected static long total = 0;

    /**
     * Return a Drawable handle instance
     */
    public DrawableHandle() {
        total++;
        this.uid = total;
    }

    @Override
    public int hashCode() {
        return uid.hashCode();
    }
}
