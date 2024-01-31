package model.game.object.enemy;

import model.game.data.Direction;
import model.game.data.Position;
import model.game.data.Size;
import model.game.object.Player;
import model.game.object.environment.Building;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Factory class for Aliens
 */
public class AlienFactory {

    Random rng = new Random();

    /**
     * Return a concrete Alien object of random behavior
     * @param playerPosition player current position
     * @param currentBuilding current building
     * @return the Alien or Empty if no suitable spawn location
     */
    public Optional<Alien> getRandomAlien(Position playerPosition, Building currentBuilding) {
        var maybePosition = findSuitableSpawnLocation(playerPosition, currentBuilding.getFreeSquares());
        if (maybePosition.isEmpty()) {
            return Optional.empty();
        }
        int number;
        // make time-wasting alien more likely if there are none, and impossible if there is one
        if (currentBuilding.getAliens().stream().noneMatch(it -> it instanceof TimeWastingAlien)) {
            number = rng.nextInt(5);
        } else {
            number = rng.nextInt(3);
        }
        if (number == 0) {
            return Optional.of(getAlienOfTypeOn(AlienType.SHOOTER, maybePosition.get()));
        } else if (number == 1) {
            return Optional.of(getAlienOfTypeOn(AlienType.BLIND, maybePosition.get()));
        } else if (number == 2) {
            return Optional.of(getAlienOfTypeOn(AlienType.MENTOR, maybePosition.get()));
        } else {
            return Optional.of(getAlienOfTypeOn(AlienType.TIME_WASTING, maybePosition.get()));
        }
    }

    /**
     * Return a concrete alien object of a specific behavior
     * @param type Alien type
     * @param playerPosition player current position
     * @param currentBuilding current building
     * @return the Alien or Empty if no suitable spawn location
     */
    public Optional<Alien> getAlienOfType(AlienType type, Position playerPosition, Building currentBuilding) {
        var maybePosition = findSuitableSpawnLocation(playerPosition, currentBuilding.getFreeSquares());
        if (maybePosition.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(getAlienOfTypeOn(type, maybePosition.get()));
    }

    /**
     * Return a concrete Alien object with the simplest behavior
     * @param playerPosition player current position
     * @param currentBuilding current building
     * @return the Alien or Empty if no suitable spawn location
     */
    public Optional<Alien> getBasicAlien(Position playerPosition, Building currentBuilding) {
        var maybePosition = findSuitableSpawnLocation(playerPosition, currentBuilding.getFreeSquares());
        if (maybePosition.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new BasicAlien(maybePosition.get()));
    }

    /**
     * Instantiate an alien of type on location
     * @param type alien type
     * @param position location
     * @return the Alien
     */
    public Alien getAlienOfTypeOn(AlienType type, Position position) {
        switch (type) {
            case SHOOTER -> {
                return new ShooterAlien(position);
            }
            case BLIND -> {
                return new BlindAlien(position);
            }
            case TIME_WASTING -> {
                return new TimeWastingAlien(position);
            }
            case MENTOR -> {
                return new MentorAlien(position);
            }
            default -> {
                return new BasicAlien(position);
            }
        }
    }

    /**
     * Find a place to spawn an alien, it should be at least 4 spaces away from the player, not on top of any furniture
     * and inside bounds
     * @param playerPosition the position of the player
     * @param freePositions free positions
     * @return suitable position to spawn new Alien
     */
    private Optional<Position> findSuitableSpawnLocation(Position playerPosition, List<Position> freePositions) {
        var spawns = freePositions.stream().filter(it -> Math.floor(it.distance(playerPosition)) > 4).toList();
        int len = spawns.size();
        if (len == 0) {
            return Optional.empty();
        } else {
            return Optional.of(spawns.get(rng.nextInt(len)));
        }
    }

    /**
     * Basic Alien follows the player anywhere and hurts them when in contact
     */
    private static class BasicAlien extends Alien {

        /**
         * Create a new Basic Alien object in the specified positions
         *
         * @param position the position
         */
        public BasicAlien(Position position) {
            super(position);
        }

        @Override
        public void doBehavior(Player player, Building building, double remainingTime) {
            Direction direction = this.findPath(player.getPosition(), building.getFreeSquares());
            // move
            this.move(direction);
            // hurt the player if overlapping
            if (this.position.equals(player.getPosition())) {
                player.reduceHealth();
            }

        }
    }


}