package model.game.object.enemy;

import model.game.object.Player;
import model.game.object.environment.Building;

/**
 * Interface used to make Alien do a single task of its choosing
 */
public interface AlienBehavior {

    /**
     * Make the alien perform the behavior
     * This behavior may include moving, hurting the player, changing the position of the key or more
     * TODO: update the interface to include remaining time
     * @param player the player instance
     * @param building the current building
     * @param remainingTime amount of time remaining in 0-1 scale
     */
    void doBehavior(Player player, Building building, double remainingTime);

}
