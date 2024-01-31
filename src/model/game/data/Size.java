package model.game.data;

import model.services.save.object.IntPair;

/**
 * Integer pair used to indicate the size of a building, separate from Position mainly for type safety
 * @param x width
 * @param y height
 */
public record Size(int x, int y) {

    /**
     * Utility method to convert the class into a POJO
     * @return pair equivalent
     */
    public IntPair toPair() {
        var pair = new IntPair();
        pair.setFirst(this.x());
        pair.setSecond(this.y());
        return pair;
    }

    /**
     * Construct Size from IntPair POJO
     * @param pair new size
     */
    public Size(IntPair pair) {
        this(pair.getFirst(), pair.getSecond());
    }

    /**
     * Get the area of the size
     * @return the area
     */
    public int area() {
        return x() * y();
    }
}

