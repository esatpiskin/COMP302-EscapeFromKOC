package model.game.data;

import java.util.Random;

/**
 * Direction enumeration, use for passing a direction to other functions
 * in a type safe manner
 */
public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    STILL;

    private static final Random rng = new Random();

    /**
     * Returns the direction opposite to this e.g. LEFT.reverse() -> RIGHT <br>
     * STILL returns STILL
     * @return the opposite direction
     */
    public Direction reverse() {
        switch (this) {
            case UP -> {
                return DOWN;
            }
            case DOWN -> {
                return UP;
            }
            case LEFT -> {
                return RIGHT;
            }
            case RIGHT -> {
                return LEFT;
            }
        }
        return this;
    }

    /**
     * Returns a random Direction except for STILL
     * @return a random direction
     */
    public static Direction random() {
        return Direction.values()[rng.nextInt(Direction.values().length - 1)];
    }
}
