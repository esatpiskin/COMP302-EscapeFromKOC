package model.game.object.enemy;

import model.game.data.Direction;
import model.game.data.IntoDrawable;
import model.game.data.Position;
import model.game.object.Player;
import model.game.object.environment.Building;
import ui.drawable.ColoredBall;
import ui.drawable.Drawable;

import java.awt.*;

/**
 * Shooter alien fires at the player
 */
public class ShooterAlien extends Alien {

    Shot shot = null;

    /**
     * Create a new Alien object in the specified position
     * @param position the position
     */
    public ShooterAlien(Position position) {
        super(position);
        this.alienType = AlienType.SHOOTER;
    }

    @Override
    public void doBehavior(Player player, Building building, double remainingTime) {
        int distanceX = this.getPosition().x() - player.getPosition().x();
        int distanceXAbs = Math.abs(distanceX);
        int distanceY = this.getPosition().y() - player.getPosition().y();
        int distanceYAbs = Math.abs(distanceY);

        var move = Direction.STILL;
        // try to close the gap
        if (distanceXAbs > 3 || distanceYAbs > 3) {
            this.move(this.findPath(player.getPosition(), building.getFreeSquares()));
        } else if (distanceXAbs > 0 || distanceYAbs > 0) {
            // try to line up to the player

            if (distanceXAbs > distanceYAbs) {
                // move horizontally, fire vertically
                move = this.findPath(new Position(this.getPosition().x(), player.getPosition().y()), building.getFreeSquares());

            } else {
                // move vertically, fire horizontally
                move = this.findPath(new Position(player.getPosition().x(), this.getPosition().y()), building.getFreeSquares());

            }

            // determine a firing direction
            var fireDirection = Direction.STILL;
            if (distanceX == 0) {
                if (distanceY > 0) {
                    fireDirection = Direction.UP;
                } else {
                    fireDirection = Direction.DOWN;
                }
            } else if (distanceY == 0) {
                if (distanceX > 0) {
                    fireDirection = Direction.LEFT;
                } else {
                    fireDirection = Direction.RIGHT;
                }
            }
            fire(fireDirection, player, building);
            if (building.tryMove(this.position.move(move))) {
                this.move(move);
            }
        }
    }

    /**
     * Fire a shot or traverse an already fired shot
     * @param direction direction to fire, or STILL if don't want to fire
     * @param player the player
     * @param building the building
     */
    private void fire(Direction direction, Player player, Building building) {
        if (this.shot == null && !direction.equals(Direction.STILL)) {
            shot = new Shot(this.position, direction);
        } else if (this.shot != null ) {
            if (!shot.traverse(player, building))
                shot = null;
        }
    }

    /**
     * Shot class represents a shot fired by the Shooter Alien
     */
    private static class Shot implements IntoDrawable {
        Position position;
        Direction direction;
        private int life;
        private final int MAX_LIFE = 4;

        /**
         * Create a new shot from the origin going towards direction
         * @param origin shooter's position
         * @param direction direction to shoot
         */
        Shot(Position origin, Direction direction) {
            this.position = origin.move(direction);
            this.direction = direction;
            this.life = 0;
        }

        /**
         * Advance the shot, terminate it if it hits a wall or furniture
         * Hurt the player if it hits the player
         * @param player the player
         * @param building building
         * @return true if shot can continue, false if it is terminated
         */
        public boolean traverse(Player player, Building building) {
            life++;
            if (life >= MAX_LIFE) {
                return false;
            } else {
                this.position = position.move(this.direction);
                if (this.position.x() == -1 ||
                        this.position.y() == -1 ||
                        this.position.x() == building.getSize().x() ||
                        this.position.y() == building.getSize().y() ||
                        building.getFurniturePositions().stream().anyMatch(it -> it.equals(this.position))) {
                    return false;
                } else if (player.getPosition().equals(this.position)) {
                    player.shoot();
                }
                return true;
            }
        }

        @Override
        public Drawable intoDrawable() {
            return new ColoredBall(this.position, Color.ORANGE);
        }
    }

    /**
     * This override allows both the Alien and the shot it fires to appear
     * @return the Drawable
     */
    @Override
    public Drawable intoDrawable() {
        Drawable top = super.intoDrawable();
        return new Drawable() {
            @Override
            public void draw(Graphics g) {
                top.draw(g);
                if (shot != null) {
                    shot.intoDrawable().draw(g);
                }
            }
        };
    }
}
