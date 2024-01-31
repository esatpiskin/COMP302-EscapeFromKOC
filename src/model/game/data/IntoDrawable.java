package model.game.data;

import ui.drawable.Drawable;

/**
 * This interface is implemented by classes which have a visual representation within the UI
 * The callers can call the intoDrawable method to fetch that information without having to construct
 * the Drawable themselves
 */
public interface IntoDrawable {

    /**
     * Get the Drawable associated with the object, implementors should use a relevant factory method
     * from DrawableFactory to simplify implementation
     * @return the Drawable
     */
    Drawable intoDrawable();

}
