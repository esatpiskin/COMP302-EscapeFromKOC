package model.game.object.enemy.strategy;

import model.game.object.enemy.TimeWastingAlien;
import model.game.object.environment.Building;

/**
 * Indecisive strategy, do nothing and disappear after 2 seconds
 */
public class TimeWastingAlienStrategyIndecisive implements TimeWastingAlienStrategy {

    @Override
    public void applyStrategy(TimeWastingAlien alien, Building building) {
        if (alien.timer == 2) {
            alien.setAlive(false);
            alien.timer = 0;
        }
    }
}
