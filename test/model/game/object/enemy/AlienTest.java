package model.game.object.enemy;

import model.game.data.Direction;
import model.game.data.Position;
import model.game.data.Size;
import model.game.object.Player;
import model.game.object.environment.Building;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Alien abstract class
 */
class AlienTest {

    /**
     * Create an empty alien at the given position
     * @param position the position
     * @return the Alien
     */
    Alien emptyAlien(Position position) {
        return new Alien(position) {
            @Override
            public void doBehavior(Player player, Building building, double remainingTime) {

            }
        };
    }

    /**
     * <pre>
     *
     *
     * T * * * S
     *
     *
     * </pre>
     */
    @Test
    void findPathAltStraightLeft() {
        var size = new Size(5,5);

        var alien = emptyAlien(new Position(4, 2));
        var target = new Position(0, 2);

        assertEquals(Direction.LEFT, alien.findPathAlt(target, new ArrayList<>(), size));
        alien.move(Direction.LEFT);
        assertEquals(Direction.LEFT, alien.findPathAlt(target, new ArrayList<>(), size));
        alien.move(Direction.LEFT);
        assertEquals(Direction.LEFT, alien.findPathAlt(target, new ArrayList<>(), size));
        alien.move(Direction.LEFT);
        assertEquals(Direction.LEFT, alien.findPathAlt(target, new ArrayList<>(), size));
        alien.move(Direction.LEFT);
        assertEquals(Direction.STILL, alien.findPathAlt(target, new ArrayList<>(), size));
    }

    /**
     * <pre>
     *
     *
     * S * * * T
     *
     *
     * </pre>
     */
    @Test
    void findPathAltStraightRight() {
        var size = new Size(5,5);

        var alien = emptyAlien(new Position(0, 2));
        var target = new Position(4, 2);

        assertEquals(Direction.RIGHT, alien.findPathAlt(target, new ArrayList<>(), size));
        alien.move(Direction.RIGHT);
        assertEquals(Direction.RIGHT, alien.findPathAlt(target, new ArrayList<>(), size));
        alien.move(Direction.RIGHT);
        assertEquals(Direction.RIGHT, alien.findPathAlt(target, new ArrayList<>(), size));
        alien.move(Direction.RIGHT);
        assertEquals(Direction.RIGHT, alien.findPathAlt(target, new ArrayList<>(), size));
        alien.move(Direction.RIGHT);
        assertEquals(Direction.STILL, alien.findPathAlt(target, new ArrayList<>(), size));
    }

    /**
     * <pre>
     *     S
     *     *
     * * * *
     *     *
     *     T
     * </pre>
     */
    @Test
    void findPathAltStraightDown() {
        var size = new Size(5,5);

        var alien = emptyAlien(new Position(2, 0));
        var target = new Position(2, 4);

        assertEquals(Direction.DOWN, alien.findPathAlt(target, new ArrayList<>(), size));
        alien.move(Direction.DOWN);
        assertEquals(Direction.DOWN, alien.findPathAlt(target, new ArrayList<>(), size));
        alien.move(Direction.DOWN);
        assertEquals(Direction.DOWN, alien.findPathAlt(target, new ArrayList<>(), size));
        alien.move(Direction.DOWN);
        assertEquals(Direction.DOWN, alien.findPathAlt(target, new ArrayList<>(), size));
        alien.move(Direction.DOWN);
        assertEquals(Direction.STILL, alien.findPathAlt(target, new ArrayList<>(), size));
    }

    /**
     * <pre>
     *     T
     *     *
     * * * *
     *     *
     *     S
     * </pre>
     */
    @Test
    void findPathAltStraightUp() {
        var size = new Size(5,5);

        var alien = emptyAlien(new Position(2, 4));
        var target = new Position(2, 0);

        assertEquals(Direction.UP, alien.findPathAlt(target, new ArrayList<>(), size));
        alien.move(Direction.UP);
        assertEquals(Direction.UP, alien.findPathAlt(target, new ArrayList<>(), size));
        alien.move(Direction.UP);
        assertEquals(Direction.UP, alien.findPathAlt(target, new ArrayList<>(), size));
        alien.move(Direction.UP);
        assertEquals(Direction.UP, alien.findPathAlt(target, new ArrayList<>(), size));
        alien.move(Direction.UP);
        assertEquals(Direction.STILL, alien.findPathAlt(target, new ArrayList<>(), size));
    }

    /**
     * <pre>
     * * T
     * S O
     * UP RIGHT
     *
     * O T
     * S *
     * RIGHT UP
     *
     * S *
     * O T
     * RIGHT DOWN
     *
     * S O
     * * T
     * DOWN RIGHT
     *
     * </pre>
     */
    @Test
    void findPathAltSimpleObstacle1() {
        var size = new Size(2,2);
        var alien = emptyAlien(new Position(0, 1));
        var target = new Position(1, 0);
        var obstacles = new ArrayList<Position>();
        obstacles.add(new Position(1, 1));

        assertEquals(Direction.UP, alien.findPathAlt(target, obstacles, size));
        alien.move(Direction.UP);
        assertEquals(Direction.RIGHT, alien.findPathAlt(target, obstacles, size));
        alien.move(Direction.RIGHT);
        assertEquals(Direction.STILL, alien.findPathAlt(target, obstacles, size));
    }

    @Test
    void findPathAltSimpleObstacle2() {
        var size = new Size(2,2);
        var alien = emptyAlien(new Position(0, 1));
        var target = new Position(1, 0);
        var obstacles = new ArrayList<Position>();
        obstacles.add(new Position(0, 0));

        assertEquals(Direction.RIGHT, alien.findPathAlt(target, obstacles, size));
        alien.move(Direction.RIGHT);
        assertEquals(Direction.UP, alien.findPathAlt(target, obstacles, size));
        alien.move(Direction.UP);
        assertEquals(Direction.STILL, alien.findPathAlt(target, obstacles, size));
    }

    @Test
    void findPathAltSimpleObstacle3() {
        var size = new Size(2,2);
        var alien = emptyAlien(new Position(0, 0));
        var target = new Position(1, 1);
        var obstacles = new ArrayList<Position>();
        obstacles.add(new Position(0, 1));

        assertEquals(Direction.RIGHT, alien.findPathAlt(target, obstacles, size));
        alien.move(Direction.RIGHT);
        assertEquals(Direction.DOWN, alien.findPathAlt(target, obstacles, size));
        alien.move(Direction.DOWN);
        assertEquals(Direction.STILL, alien.findPathAlt(target, obstacles, size));
    }

    @Test
    void findPathAltSimpleObstacle4() {
        var size = new Size(2,2);
        var alien = emptyAlien(new Position(0, 0));
        var target = new Position(1, 1);
        var obstacles = new ArrayList<Position>();
        obstacles.add(new Position(1, 0));

        assertEquals(Direction.DOWN, alien.findPathAlt(target, obstacles, size));
        alien.move(Direction.DOWN);
        assertEquals(Direction.RIGHT, alien.findPathAlt(target, obstacles, size));
        alien.move(Direction.RIGHT);
        assertEquals(Direction.STILL, alien.findPathAlt(target, obstacles, size));
    }

    /**
     * The performance of the function is a bit concerning with large sizes due to O(n2) allocations and no caching
     */
    @Test
    void findPathAltVeryLargeLocation() {
        var size = new Size(30, 20);
        var alien = emptyAlien(new Position(0, 0));
        var target = new Position(29, 19);
        var obstacles = new ArrayList<Position>();

        var freeSquares = IntStream.range(0, size.x())
                .mapToObj(i -> IntStream.range(0, size.y()).mapToObj(j -> new Position(i, j)))
                .flatMap(Function.identity())
                .filter(square -> obstacles.stream().noneMatch(square::equals)).toList();

        Direction move;
        for (int i = 0; i < 48; ++i) {
            move = alien.findPath(target, freeSquares);
            alien.move(move);
        }
        assertEquals(alien.position, target);
    }
}