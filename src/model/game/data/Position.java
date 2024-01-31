package model.game.data;

import model.services.save.object.IntPair;

/**
 * Integer Pair used to represent the position of some object, the position is x-right, and y-down <br>
 * In other words, larger x values indicate further right, and larger y values indicate further down *
 * @param x x position
 * @param y y position
 */
public record Position(int x, int y) {
    /**
     * Returns a new Position record with position updated by 1 in the given direction
     * <pre>
     *  UP    -> y - 1
     *  DOWN  -> y + 1
     *  LEFT  -> x - 1
     *  RIGHT -> x + 1
     * </pre>
     * @param direction direction to offset
     * @return new Position record
     */
    public Position move(Direction direction) {
        Position result = this;
        switch (direction) {
            case UP -> result = new Position(x, y - 1);
            case DOWN -> result = new Position(x, y + 1);
            case LEFT -> result = new Position(x - 1, y);
            case RIGHT -> result = new Position(x + 1, y);
        }
        return result;
    }

    /**
     * Returns a new Position record with position updated by distance in the given direction
     * <pre>
     *     UP    -> y - distance
     *     DOWN  -> y + distance
     *     LEFT  -> x - distance
     *     RIGHT -> x + distance
     * </pre>
     * @param direction direction to offset
     * @param distance distance to offset
     * @return new position record
     */
    public Position move(Direction direction, int distance) {
        Position result = this;
        switch (direction) {
            case UP -> result = new Position(x, y - distance);
            case DOWN -> result = new Position(x, y + distance);
            case LEFT -> result = new Position(x - distance, y);
            case RIGHT -> result = new Position(x + distance, y);
        }
        return result;
    }

    /**
     * Returns true if this position is adjacent to other, else returns false <br>
     * also returns false if the positions are the same
     * @param other other position
     * @return true or false
     */
    public boolean isAdjacent(Position other) {
        return this.move(Direction.UP).equals(other) ||
                this.move(Direction.DOWN).equals(other) ||
                this.move(Direction.LEFT).equals(other) ||
                this.move(Direction.RIGHT).equals(other);
    }

    /**
     * Return the distance between two points
     * @param other other point
     * @return distance
     */
    public double distance(Position other) {
        double x2 = (this.x() - other.x()) * (this.x() - other.x());
        double y2 = (this.y() - other.y()) * (this.y() - other.y());
        return Math.sqrt(x2 + y2);
    }

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
     * Construct Position from IntPair POJO
     * @param pair new position
     */
    public Position(IntPair pair) {
        this(pair.getFirst(), pair.getSecond());
    }

}
