package model.game.object.enemy.strategy;

import model.game.object.enemy.TimeWastingAlien;
import model.game.object.environment.Building;

/**
 * This interface defines different strategies a TimeWastingAlien might choose during the game
 */
public interface TimeWastingAlienStrategy {

    void applyStrategy(TimeWastingAlien alien, Building building);

    static TimeWastingAlienStrategy getStrategy(double remainingTime) {
        if (remainingTime > 0.7) {
            return new TimeWastingAlienStrategyAggressive();
        } else if (remainingTime > 0.4) {
            return new TimeWastingAlienStrategyIndecisive();
        } else {
            return new TimeWastingAlienStrategyBored();
        }
    }
}
