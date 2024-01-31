package model.game.object.enemy;

import model.game.data.*;
import ui.drawable.Drawable;
import ui.drawable.DrawableFactory;
import ui.drawable.data.DrawableProperties;
import ui.drawable.sprite.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * An Alien is an enemy in the game, it implements AlienBehavior which is called to apply its effects
 */
public abstract class Alien implements AlienBehavior, IntoDrawable {

    protected Position position;
    protected AlienType alienType = AlienType.GENERIC;
    protected boolean alive = true;

    private Direction lastMove = Direction.STILL;
    private DrawableHandle handle;

    /**
     * Create a new Alien object in the specified position
     * @param position the position
     */
    public Alien(Position position) {
        this.position = position;
    }

    /**
     * Get the Alien's position
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Set the Alien's position
     * @param position new position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Move the alien in the specified direction <br>
     * No checks are performed to ensure the movement is valid <br>
     * This method is defined as protected as it is expected to only be called internally inside doBehavior()     *
     * @param direction the direction
     */
    protected void move(Direction direction) {
        lastMove = direction;
        this.position = this.position.move(direction);
    }

    /**
     * Call findPath but using obstacles instead of free Squares
     * @param target target location
     * @param obstacles invalid positions in the graph
     * @param buildingSize building bounds
     * @return direction to move to
     */
    protected Direction findPathAlt(Position target, List<Position> obstacles, Size buildingSize) {
        var free_squares = IntStream.range(0, buildingSize.x())
                .mapToObj(i -> IntStream.range(0, buildingSize.y()).mapToObj(j -> new Position(i, j)))
                .flatMap(Function.identity())
                .filter(square -> obstacles.stream().noneMatch(square::equals)).toList();
        return findPath(target, free_squares);
    }

    /**
     * Find the direction to go to get from this.position to target by taking the fewest number of steps <br>
     * This method uses Dijkstra's shortest path algorithm to figure out a path to the destination
     * @param target target location
     * @param freeSquares graph to traverse
     * @return direction to move to
     */
    protected Direction findPath(Position target, List<Position> freeSquares) {
        // TODO: remove buildingSize
        List<Position> graph = new ArrayList<>(freeSquares);
        graph.add(this.position);

        class Dist implements Comparable<Dist> {
            Position pos;
            Dist prev;
            Integer dist;
            @Override
            public int compareTo(Dist o) {
                return this.dist.compareTo(o.dist);
            }
        }
        PriorityQueue<Dist> backing = new PriorityQueue<>();
        PriorityQueue<Dist> Q = new PriorityQueue<>();
        Dist finalPoint = null;

        for (var square: graph) {
            var v = new Dist();
            v.pos = square;
            v.prev = null;
            if (square.equals(this.position)) {
                v.dist = 0;
            } else {
                v.dist = Integer.MAX_VALUE;
            }
            if (square.equals(target)) {
                finalPoint = v;
            }

            backing.offer(v);
            Q.offer(v);
        }

        while (!Q.isEmpty()) {
            var u = Q.poll();
            var adjacent = backing.stream().filter(v -> v.pos.isAdjacent(u.pos)).toList();
            for (var v: adjacent) {
                var alt = u.dist + 1;
                if (alt < v.dist) {
                    v.dist = alt;
                    v.prev = u;
                }
                if (Q.remove(v)) {
                    Q.add(v);
                }
            }
            if (u.pos.equals(target)) {
                break;
            }
        }

        Position nextHop = null;
        while (finalPoint != null && finalPoint.prev != null) {
            nextHop = finalPoint.pos;
            finalPoint = finalPoint.prev;
        }

        if (nextHop == null)  {
            return Direction.STILL;
        } else if (nextHop.x() > this.position.x()) {
            return Direction.RIGHT;
        } else if (nextHop.x() < this.position.x()) {
            return Direction.LEFT;
        } else if (nextHop.y() > this.position.y()) {
            return Direction.DOWN;
        } else if (nextHop.y() < this.position.y()) {
            return Direction.UP;
        } else {
            return Direction.STILL;
        }
    }

    /**
     * Get the Drawable associated with the Alien <br>
     * Subclasses should set the alienType for the desired visuals
     * @return the Drawable
     */
    @Override
    public Drawable intoDrawable() {
        Sprite sprite = alienType.getSprite();
        var factory = DrawableFactory.getInstance();
        handle = factory.getSingleTileCached(handle, DrawableProperties.playerProperties(sprite, this.position, this.lastMove));
        this.lastMove = Direction.STILL;
        return factory.fromHandle(handle);
    }

    /**
     * Check if the alien is alive
     * @return true if alien is alive
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Set alien livelihood
     * @param alive the livelihood
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Get the alien type
     * @return the alien type
     */
    public AlienType getAlienType() {
        return alienType;
    }
}
