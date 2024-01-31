package ui.controller;

import javax.swing.event.MouseInputAdapter;

/**
 * A wrapper over MouseInputAdapter that contains additional offset values for the Animator
 */
public abstract class GameInputAdapter extends MouseInputAdapter {

    private int offsetX = 0;
    private int offsetY = 0;

    /**
     * Set the y offset, higher values are further down
     * @param offsetY the offset
     */
    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    /**
     * Set the x offset, higher values are further right
     * @param offsetX the offset
     */
    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    /**
     * Get the y offset, higher values are further down
     * @return the offset
     */
    public int getOffsetX() {
        return offsetX;
    }

    /**
     * Get the x offset, higher values are further right
     * @return the offset
     */
    public int getOffsetY() {
        return offsetY;
    }
}
