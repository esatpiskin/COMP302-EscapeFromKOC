package model.game.object.enemy;

import ui.drawable.sprite.Sprite;

import java.util.Random;

/**
 * Enumeration for different alien types
 */
public enum AlienType {
    GENERIC,
    SHOOTER,
    BLIND,
    TIME_WASTING,
    MENTOR;

    /**
     * Get the sprite associated with the enum variant
     * @return the Sprite
     */
    public Sprite getSprite() {
        switch (this) {
            case SHOOTER -> {
                return Sprite.ALIEN_SHOOTER;
            }
            case BLIND -> {
                return Sprite.ALIEN_BLIND;
            }
            case TIME_WASTING -> {
                return Sprite.ALIEN_TIME_WASTING;
            }
            case MENTOR -> {
                return  Sprite.ALIEN_TIME_WASTING;
            }
            default -> {
                return Sprite.ALIEN_GENERIC;
            }
        }
    }
}
