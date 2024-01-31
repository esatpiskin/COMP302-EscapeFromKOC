package model.game.object.enemy.strategy;

import model.game.object.enemy.TimeWastingAlien;
import model.game.object.environment.Building;

import java.util.Random;

/**
 * Aggressive strategy, change the location of the key once every 3 seconds.
 */
public class TimeWastingAlienStrategyAggressive implements TimeWastingAlienStrategy {

    @Override
    public void applyStrategy(TimeWastingAlien alien, Building building) {
        Random rng = new Random();
        // every 3 seconds
        if (alien.timer == 3) {
            var positions = building.getFurniturePositions();
            if (positions.size() > 0) {
                var pos = positions.get(rng.nextInt(positions.size()));
                building.keys.get(rng.nextInt(building.keys.size())).setPosition(pos);
            }
            alien.timer = 0;
        }
    }
}
