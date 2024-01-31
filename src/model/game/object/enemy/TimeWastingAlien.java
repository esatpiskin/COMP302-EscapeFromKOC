package model.game.object.enemy;

import model.game.data.Position;
import model.game.object.Player;
import model.game.object.enemy.strategy.TimeWastingAlienStrategy;
import model.game.object.environment.Building;

/**
 * Time Wasting Alien moves the location of the key and may also disappear based on strategy
 */
public class TimeWastingAlien extends Alien {

    TimeWastingAlienStrategy strategy = TimeWastingAlienStrategy.getStrategy(1.0);

    public int timer = 0;

    /**
     * Create a new Time Wasting Alien at the specified position
     * @param position the position
     */
    public TimeWastingAlien(Position position) {
        super(position);
        this.alienType = AlienType.TIME_WASTING;
    }

    @Override
    public void doBehavior(Player player, Building building, double remainingTime) {
        // needlessly hacky way to reset the timer at strategy changes
        var strategy = TimeWastingAlienStrategy.getStrategy(remainingTime);
        if (this.strategy.getClass().equals(strategy.getClass())) {
            timer++;
        } else {
            timer = 0;
            this.strategy = strategy;
        }
        this.strategy.applyStrategy(this, building);
    }
}
