package model.game.object.environment;

import ui.drawable.sprite.Sprite;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * enum class for different furniture types <br>
 * Allows for a convenient type safe wrapper for adding additional furniture types only discriminated by their name
 * and how they look. Simply add additional enum variants and update the getSprite() method with a relevant sprite.
 */
public enum FurnitureType {
    TABLE,
    DESK,
    TRASH,
    BOARD,
    LIB,
    ;

    /**
     * Get a list of all furniture names by calling name() on all enum variants. It is possible to then use
     * FurnitureType.valueOf() to convert them back to an enum.
     * @return list of furniture types as a string
     */
    public static List<String> getFurnitureTypeNames() {
        return Arrays.stream(FurnitureType.values()).map(FurnitureType::toString).collect(Collectors.toList());
    }

    /**
     * Return random furniture type
     * @return random furniture type
     */
    public static FurnitureType random() {
        return FurnitureType.values()[new Random().nextInt(values().length)];
    }

    @Override
    public String toString() {
        return this.name();
    }

    /**
     * Get the sprite associated with the enum variant
     * @return the Sprite
     */
    public Sprite getSprite() {
        switch (this) {
            case TABLE -> {
                return Sprite.FURNITURE_TABLE;
            }
            case DESK -> {
                return Sprite.FURNITURE_DESK;
            }
            case TRASH -> {
                return Sprite.FURNITURE_TRASH;
            }
            case BOARD -> {
                return Sprite.FURNITURE_BOARD;
            }
            case LIB -> {
                return Sprite.FURNITURE_LIB;
            }
        }
        return Sprite.FURNITURE_GENERIC;
    }
}
