package model.game.object.enemy;

import model.game.data.Position;
import model.game.object.Player;
import model.game.object.environment.Building;
import model.game.object.item.Key;

import java.util.ArrayList;
import java.util.Random;

public class MentorAlien extends Alien{

    private Position destination = null;
    /**
     * Create a new Alien object in the specified positions
     *
     * @param position the position
     */
    public MentorAlien(Position position) {
        super(position);
        this.alienType = AlienType.MENTOR;
    }

    @Override
    public void doBehavior(Player player, Building building, double remainingTime) {
        if (this.destination == null) {
            Random rng = new Random();
            if (rng.nextInt(2) == 1) {
                this.destination = building.getRandomFurnitureSquare();
            } else {
                var destinationMaybe = building.keys.stream().filter(Key::isEnabled).findAny();
                destinationMaybe.ifPresent(it -> this.destination = it.getPosition());
            }
        }
        var squares = new ArrayList<>(building.getFreeSquares());
        squares.add(destination);
        this.move(this.findPath(destination, squares));

        if (this.position.equals(destination)) {
            this.setAlive(false);
        }
    }
}
