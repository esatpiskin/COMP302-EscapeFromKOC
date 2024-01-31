package model.game.object.enemy.strategy;

import model.game.object.enemy.TimeWastingAlien;
import model.game.object.environment.Building;

import java.util.Random;

/**
 * Bored strategy, change the location of the key and disappear after 2 seconds
 */
public class TimeWastingAlienStrategyBored implements TimeWastingAlienStrategy {

    @Override
    public void applyStrategy(TimeWastingAlien alien, Building building) {
        if (alien.timer == 2) {
            Random rng = new Random();
            var positions = building.getFurniturePositions();
            if (positions.size() > 0) {
                var pos = positions.get(rng.nextInt(positions.size()));
                building.keys.get(rng.nextInt(building.keys.size())).setPosition(pos);
            }
            alien.setAlive(false);
            alien.timer = 0;
        }
    }
}
