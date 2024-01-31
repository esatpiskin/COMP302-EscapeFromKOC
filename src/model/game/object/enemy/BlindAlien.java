package model.game.object.enemy;
import model.game.data.Direction;
import model.game.data.Position;
import model.game.object.Player;
import model.game.object.environment.Building;

/**
 * Blind Alien chases after the player and hurts it when near
 * It will chase after
 */
public class BlindAlien extends Alien {

    /**
     * Create a new BlindAlien at the specified position
     * @param position the position
     */
    public BlindAlien(Position position) {
        super(position);
        this.alienType = AlienType.BLIND;
    }

    @Override
    public void doBehavior(Player player, Building building, double remainingTime) {
        int distance = (int) Math.floor(player.getPosition().distance(this.position));
        // if player is nearby, move towards the player
        // TODO: make blind alien distracted with the bottle power up
        if (distance < 6) {
            this.move(this.findPath(player.getPosition(), building.getFreeSquares()));
        } else {
            // otherwise, move randomly
            var randomDir = Direction.random();
            if (building.tryMove(this.position.move(randomDir))) {
                this.move(randomDir);
            }
        }
        // try to hurt the player
        if (this.position.isAdjacent(player.getPosition()) || this.position.equals(player.getPosition())) {
            player.strangle();
        }
    }

}


