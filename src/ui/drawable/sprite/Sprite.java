package ui.drawable.sprite;

import java.awt.*;

/**
 * The Sprite enum represents an abstraction for images
 */
public enum Sprite {
    NULL,
    PLAYER,
    PLAYER_HURT,

    SP_FLOOR,
    SP_DOOR,
    SP_WALL,
    SP_OPEN_DOOR,

    FURNITURE_TABLE,
    FURNITURE_BOARD,
    FURNITURE_DESK,
    FURNITURE_LIB,
    FURNITURE_TRASH,
    FURNITURE_GENERIC,
    FURNITURE_SAFE,
    FURNITURE_SAFE_OPEN,


    ITEM_KEY,
    ITEM_EXTRA_TIME,
    ITEM_EXTRA_LIFE,
    ITEM_BOTTLE,
    ITEM_HINT,
    ITEM_VEST,

    ALIEN_GENERIC,
    ALIEN_SHOOTER,
    ALIEN_BLIND,
    ALIEN_TIME_WASTING;


    /**
     * Get the image associated with the enum variant
     * @return the image
     */
    public Image getImage() {
        switch (this) {
            case PLAYER, PLAYER_HURT -> {
                return SpriteLoader.dragon;
            }
            case SP_WALL -> {
                return SpriteLoader.wall;
            }
            case SP_DOOR -> {
                return SpriteLoader.door;
            }
            case SP_OPEN_DOOR -> {
                return SpriteLoader.open_door;
            }
            case SP_FLOOR -> {
                return SpriteLoader.floor;
            }
            case ITEM_KEY -> {
                return SpriteLoader.key;
            }
            case FURNITURE_TABLE -> {
                return SpriteLoader.table;
            }
            case FURNITURE_BOARD -> {
                return SpriteLoader.board;
            }
            case FURNITURE_DESK -> {
                return SpriteLoader.desk;
            }
            case FURNITURE_LIB -> {
                return SpriteLoader.lib;
            }
            case FURNITURE_TRASH -> {
                return SpriteLoader.trash;
            }
            case ITEM_EXTRA_TIME -> {
                return SpriteLoader.extra_time;
            }
            case ITEM_EXTRA_LIFE -> {
                return SpriteLoader.extra_life;
            }
            case ITEM_BOTTLE -> {
                return SpriteLoader.bottle;
            }
            case ITEM_HINT -> {
                return SpriteLoader.hint;
            }
            case ITEM_VEST -> {
                return SpriteLoader.vest;
            }
            case ALIEN_SHOOTER -> {
                return SpriteLoader.shooting_alien;
            }
            case ALIEN_BLIND -> {
                return SpriteLoader.blind_alien;
            }
            case ALIEN_TIME_WASTING -> {
                return SpriteLoader.time_wasting_alien;
            }
            case ALIEN_GENERIC -> {
                return SpriteLoader.generic_alien;
            }
            case FURNITURE_SAFE -> {
                return SpriteLoader.safe;
            }
            case FURNITURE_SAFE_OPEN -> {
                return SpriteLoader.safe_open;
            }

            default -> {
                return SpriteLoader.getDefault();
            }
        }
    }
}
