package model.game.logic;

import model.game.data.Direction;
import model.game.data.Position;
import model.game.object.Player;
import model.game.object.enemy.AlienFactory;
import model.game.object.environment.Building;
import model.game.powerUp.Power;
import ui.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This class encapsulates most of the game mode related logic, it is expected to handle player movement,
 * picking up keys, picking up power ups, using power ups, going from building to building, spawning aliens and
 * executing their behavior, and so on.
 * <br>
 * It should delegate as many of those tasks to its members as possible.
 */
public class GameLogic {

    private final List<Building> buildings;

    private ArrayList<Power> powerUpList =  new ArrayList<>();

    private int currentBuildingIndex;
    private Player player;

    private IGameLogicListener listener;

    private final DomainTimer timer = new DomainTimer();
    protected final int MAX_TIME = 300;
    private int remainingTime = MAX_TIME;

    protected final int ALIEN_SPAWN_INTERVAL = 10;

    private final Logger logger = Logger.getLogger("GameLogic");

    /**
     * Create a new GameLogic instance
     * @param buildings buildings from BuildLogic
     */
    public GameLogic(List<Building> buildings) {
        this.buildings = buildings;
        timer.scheduleTask(this::onUpdate);
        restartGame();
    }

    /**
     * Runs on every game tick
     * <br>
     * This method is periodically called to handle alien as well as power up behavior that have fixed intervals
     * as opposed to being player driven
     */
    protected void onUpdate() {
        // aliens do their actions
        getCurrentBuilding().getAliens().forEach(alien ->
                alien.doBehavior(player, getCurrentBuilding(), (double) remainingTime / (double) MAX_TIME));
        // add one alien every 10 seconds maybe
        if (remainingTime % ALIEN_SPAWN_INTERVAL == 0) {
            logger.log(Level.INFO, "Trying to spawn alien.");
            int max = getCurrentBuilding().maxAliens();
            if (getCurrentBuilding().getAliens().size() < max) {
                var alienMaybe = new AlienFactory().getRandomAlien(player.getPosition(), getCurrentBuilding());
                alienMaybe.ifPresent(value -> {
                    logger.log(Level.INFO, "Spawned an alien.");
                    getCurrentBuilding().getAliens().add(value);
                });
            } else {
                logger.log(Level.INFO, "Alien number already max.");
            }
        }
        // cleanup aliens list
        getCurrentBuilding().getAliens().removeAll(getCurrentBuilding().getAliens().stream().filter(it -> !it.isAlive()).toList());

        remainingTime--;
        if (remainingTime == 0 || player.getHealth() <= 0) {
            logger.log(Level.INFO, "Game over");
            listener.onGameVictoryUpdate();
        }
        logger.log(Level.INFO, "Remaining time: " + remainingTime);
    }

    /**
     * Check collisions and move player
     * @param direction direction to move to
     */
    public void movePlayer(Direction direction) {
        Position target = this.player.getPosition().move(direction);

        // check door collision
        if (target.equals(getCurrentBuilding().door.getPosition()) &&
                getCurrentBuilding().door.isOpen()) {
            player.move(direction);
            nextBuilding();
        }
        else if (target.equals(getCurrentBuilding().door_prev.getPosition()) && getCurrentBuilding().door_prev.isOpen()) {
            player.move(direction);
            prevBuilding();
        }

        // check wall collision
        else if (getCurrentBuilding().tryMove(target)) {
            player.move(direction);
        }
    }

    /**
     * Get the currently selected building
     * @return the currently selected building
     */
    public Building getCurrentBuilding() {
        return this.buildings.get(this.currentBuildingIndex);
    }

    /**
     * Try picking up the key from specified position <br>
     * This method does nothing if the position does not have a key
     * TODO: delegate the task to the key object
     * @param position the position to pick the key up from
     */
    public void pickUpKey(Position position) {
        getCurrentBuilding().pickupKey(position);
    }

    public void findSafe(Position position) {
        getCurrentBuilding().findSafe(position);
    }

    /**
     * Return an ordered list of Drawables to the main Animator
     * @return an ordered list of drawables
     */
    public Iterable<Drawable> getGraphics() {
        List<Drawable> drawables = new ArrayList<>();
        drawables.add(player.intoDrawable());
        drawables.add(getCurrentBuilding().intoDrawable());
        drawables.addAll(getCurrentBuilding().getDrawableChildren());

        drawables.sort(null);

        return drawables;
    }

    /**
     * Called when the player moves to the next building, it should handle related tasks and end the game
     * if the player is in the final building
     */
    private void nextBuilding() {

        currentBuildingIndex++;
        if (currentBuildingIndex >= buildings.size()) {
            currentBuildingIndex = 0;
            if (listener != null) {
                buildings.forEach((building) -> building.door.close());
                listener.onGameVictoryUpdate();
            }
            // TODO: STOP TIMER
        }
        // set player position
        var doorPosition = getCurrentBuilding().door_prev.getPosition();
        if (doorPosition.x() == -1) {
            player.setPosition(doorPosition.move(Direction.RIGHT));
            player.setLastMove(Direction.RIGHT);
        } else if (doorPosition.y() == -1) {
            player.setPosition(doorPosition.move(Direction.DOWN));
            player.setLastMove(Direction.DOWN);
        } else if (doorPosition.x() == getCurrentBuilding().getSize().x()) {
            player.setPosition(doorPosition.move(Direction.LEFT));
            player.setLastMove(Direction.LEFT);
        } else if (doorPosition.y() == getCurrentBuilding().getSize().y()) {
            player.setPosition(doorPosition.move(Direction.UP));
            player.setLastMove(Direction.UP);
        }
    }

    /**
     * Called when player moves to the previous building
     */
    private void prevBuilding() {
         if (currentBuildingIndex != 0){
            currentBuildingIndex--;
             var doorPosition = getCurrentBuilding().door.getPosition();
             if (doorPosition.x() == -1) {
                 player.setPosition(doorPosition.move(Direction.RIGHT));
                 player.setLastMove(Direction.RIGHT);
             } else if (doorPosition.y() == -1) {
                 player.setPosition(doorPosition.move(Direction.DOWN));
                 player.setLastMove(Direction.DOWN);
             } else if (doorPosition.x() == getCurrentBuilding().getSize().x()) {
                 player.setPosition(doorPosition.move(Direction.LEFT));
                 player.setLastMove(Direction.LEFT);
             } else if (doorPosition.y() == getCurrentBuilding().getSize().y()) {
                 player.setPosition(doorPosition.move(Direction.UP));
                 player.setLastMove(Direction.UP);
             }
        }
    }

    /**
     * Pauses onUpdate execution
     * This function should not be called from the UI layer to prevent potential data races
     */
    public synchronized void pauseGame() {
        timer.pause();
    }

    /**
     * Starts or restarts onUpdate execution
     * This function should not be called from the UI layer to prevent potential data races
     */
    public synchronized void resumeGame() {
        timer.start();
    }

    /**
     * This function sets up the initial state for the game phase, it should also be called to restart the game
     */
    public void restartGame() {
        player = new Player(new Position(1, 1));
        currentBuildingIndex = 0;
        buildings.forEach((building) -> building.door.close());
    }

    public void setListener(IGameLogicListener listener) {
        this.listener = listener;
    }

    public IGameLogicListener getListener() {
        return listener;
    }


    /** Setting powerup logic for the user
     */
    public void addBottlePower(Power pwr) {
        powerUpList.add(pwr);
    }
    public void addBExtraTimePower(Power pwr) {
        powerUpList.add(pwr);
    }
    public void addVestPower(Power pwr) {
        powerUpList.add(pwr);
    }  public void addExtraLifePower(Power pwr) {
        powerUpList.add(pwr);
    }
    public void addHintPower(Power pwr) {
        powerUpList.add(pwr);
    }


    /**
     * Get the Player object
     * @return the player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Set the Player object
     * @param player new player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Get the current building index
     * @return the building index
     */
    public int getCurrentBuildingIndex() {
        return this.currentBuildingIndex;
    }

    /**
     * Set the current building index
     * @param index new building index
     */
    public void setCurrentBuildingIndex(int index) {
        if (index >= 0 && index < buildings.size()) {
            this.currentBuildingIndex = index;
        } else {
            logger.log(Level.SEVERE, "Building index out of bounds: " + index);
        }
    }

    /**
     * Get the remaining time
     * @return the remaining time
     */
    public int getRemainingTime() {
        return remainingTime;
    }
}
