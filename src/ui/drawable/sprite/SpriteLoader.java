package ui.drawable.sprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * This class preloads all the necessary images into memory eliminating unnecessary IO operations <br>
 * In order to add a new sprite: <br>
 * 1. add the sprite to res/sprite/ directory <br>
 * 2. add it as a String to this class <br>
 * 3. add a public static Image member and assign the image to it in the static constructor <br>
 * 4. add an enum variant in the Sprite class and update getImage() to return the image
 */
class SpriteLoader {

    private static final URL FILE_DRAGON = SpriteLoader.class.getResource("/sprite/Dragon.png");
    private static final URL FILE_WALL = SpriteLoader.class.getResource("/sprite/Wall.png");
    private static final URL FILE_CHECKER = SpriteLoader.class.getResource("/sprite/CheckerTile.png");
    private static final URL FILE_TABLE = SpriteLoader.class.getResource("/sprite/Table.png");
    private static final URL FILE_BOARD = SpriteLoader.class.getResource("/sprite/Board.png");
    private static final URL FILE_DESK = SpriteLoader.class.getResource("/sprite/Desk.png");
    private static final URL FILE_DOOR = SpriteLoader.class.getResource("/sprite/Door.png");
    private static final URL FILE_KEY = SpriteLoader.class.getResource("/sprite/Key.png");
    private static final URL FILE_LIB = SpriteLoader.class.getResource("/sprite/Lib.png");
    private static final URL FILE_TRASH = SpriteLoader.class.getResource("/sprite/Trash.png");
    private static final URL FILE_EXTRA_TIME = SpriteLoader.class.getResource("/sprite/ExtraTime.png");
    private static final URL FILE_EXTRA_LIFE = SpriteLoader.class.getResource("/sprite/ExtraLife.png");
    private static final URL FILE_HINT = SpriteLoader.class.getResource("/sprite/Hint.png");
    private static final URL FILE_VEST = SpriteLoader.class.getResource("/sprite/Vest.png");
    private static final URL FILE_BOTTLE = SpriteLoader.class.getResource("/sprite/Bottle.png");
    private static final URL FILE_SHOOTING_ALIEN = SpriteLoader.class.getResource("/sprite/ShootingAlien.png");
    private static final URL FILE_BLIND_ALIEN = SpriteLoader.class.getResource("/sprite/BlindAlien.png");
    private static final URL FILE_TIME_WASTING_ALIEN = SpriteLoader.class.getResource("/sprite/TimeWastingAlien.png");
    private static final URL FILE_FLOOR = SpriteLoader.class.getResource("/sprite/Floor.png");
    private static final URL FILE_OPEN_DOOR = SpriteLoader.class.getResource("/sprite/OpenDoor.png");
    private static final URL FILE_SAFE = SpriteLoader.class.getResource("/sprite/Safe.png");
    private static final URL FILE_SAFE_OPEN = SpriteLoader.class.getResource("/sprite/SafeOpen.png");


    public static Image dragon;
    public static Image wall;
    public static Image checker;
    public static Image table;
    public static Image board;
    public static Image desk;
    public static Image door;
    public static Image key;
    public static Image lib;
    public static Image trash;
    public static Image extra_time;
    public static Image extra_life;
    public static Image hint;
    public static Image vest;
    public static Image bottle;
    public static Image shooting_alien;
    public static Image blind_alien;
    public static Image time_wasting_alien;
    public static Image floor;
    public static Image open_door;
    public static Image safe;
    public static Image safe_open;
    public static Image generic_alien;


    static {
        try {
            dragon = ImageIO.read(FILE_DRAGON);
            wall = ImageIO.read(FILE_WALL);
            checker = ImageIO.read(FILE_CHECKER);
            table = ImageIO.read(FILE_TABLE);
            board = ImageIO.read(FILE_BOARD);
            desk = ImageIO.read(FILE_DESK);
            door = ImageIO.read(FILE_DOOR);
            key = ImageIO.read(FILE_KEY);
            lib = ImageIO.read(FILE_LIB);
            trash = ImageIO.read(FILE_TRASH);
            extra_time = ImageIO.read(FILE_EXTRA_TIME);
            extra_life = ImageIO.read(FILE_EXTRA_LIFE);
            hint = ImageIO.read(FILE_HINT);
            vest = ImageIO.read(FILE_VEST);
            bottle = ImageIO.read(FILE_BOTTLE);
            shooting_alien = ImageIO.read(FILE_SHOOTING_ALIEN);
            blind_alien = ImageIO.read(FILE_BLIND_ALIEN);
            time_wasting_alien = ImageIO.read(FILE_TIME_WASTING_ALIEN);
            floor = ImageIO.read(FILE_FLOOR);
            open_door = ImageIO.read(FILE_OPEN_DOOR);
            safe = ImageIO.read(FILE_SAFE);
            safe_open = ImageIO.read(FILE_SAFE_OPEN);
            generic_alien = shooting_alien;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the "default" Image, ie Image for Sprite variants not associated with an Image
     * @return the image
     */
    public static Image getDefault() {
        return checker;
    }
}
