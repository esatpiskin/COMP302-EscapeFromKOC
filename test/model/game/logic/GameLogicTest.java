package model.game.logic;

import model.game.data.Direction;
import model.game.data.Position;

import model.game.data.Size;
import model.game.object.environment.Building;
import model.game.object.environment.FurnitureType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class GameLogicTest {

    @Test
    void walkThroughObject() {
        BuildLogic buildLogic = new BuildLogic(List.of(new Building(new Size (5,5))),List.of(new Integer[]{5}));
        buildLogic.addFurniture(new Position(2,1), FurnitureType.TABLE);
        GameLogic gameLogic = new GameLogic(buildLogic.getBuildings());

        gameLogic.movePlayer(Direction.RIGHT);
        gameLogic.movePlayer(Direction.RIGHT);
        gameLogic.movePlayer(Direction.RIGHT);

        assertEquals(gameLogic.getPlayer().getPosition(), new Position(1,1));

    }

    @Test
    void walkThroughWall() {
        BuildLogic buildLogic = new BuildLogic(List.of((new Building(new Size(5, 5)))), List.of(new Integer[]{5}));
        GameLogic gameLogic = new GameLogic(buildLogic.getBuildings());

        gameLogic.movePlayer(Direction.UP);
        gameLogic.movePlayer(Direction.UP);
        gameLogic.movePlayer(Direction.UP);

        assertEquals(gameLogic.getPlayer().getPosition(),new Position(1,0));

    }

    @Test
    void passingObject() {
        BuildLogic buildLogic = new BuildLogic(List.of((new Building(new Size(5, 5)))), List.of(new Integer[]{5}));
        buildLogic.addFurniture(new Position(2,1),FurnitureType.TABLE);
        GameLogic gameLogic = new GameLogic(buildLogic.getBuildings());

        gameLogic.movePlayer(Direction.DOWN);
        gameLogic.movePlayer(Direction.RIGHT);
        gameLogic.movePlayer(Direction.RIGHT);
        gameLogic.movePlayer(Direction.UP);

        assertEquals(gameLogic.getPlayer().getPosition(), new Position(3,1));

    }


    @Test
    void walkThroughOpenDoor() {
        BuildLogic buildLogic = new BuildLogic(
                List.of(new Building(new Size(5,5)), new Building(new Size(5, 5))),
                List.of(new Integer[]{5,7}));
        buildLogic.addFurniture(new Position(2,1),FurnitureType.TABLE);
        buildLogic.finalizeBuildings();
        buildLogic.moveExitDoor(new Position(5,1));
        GameLogic gameLogic = new GameLogic(buildLogic.getBuildings());

        gameLogic.pickUpKey(new Position(2,1));

        for (int i=0; i<10; i++){
            gameLogic.movePlayer(Direction.RIGHT);

        }

        assertEquals(gameLogic.getPlayer().getPosition(), new Position(1,1));


    }

    @Test
    void walkThroughClosedDoor() {
        var building = new Building(new Size(5, 5));
        building.setExitDoorPosition(new Position(5, 1));
        GameLogic gameLogic = new GameLogic(List.of(building));
        gameLogic.getPlayer().setPosition(new Position(0,1));
        for (int i=0; i<10; i++){
            gameLogic.movePlayer(Direction.RIGHT);

        }
        assertEquals(gameLogic.getPlayer().getPosition(), new Position(4,1));

    }

    /**
     * The idea is 1 alien spawn every ALIEN_SPAWN_INTERVAL <br>
     * Building size will influence Alien spawns, so it should be large enough in this instance always
     */
    @Test
    void alienSpawningTest() {
        var building = new Building(new Size(20, 20));
        building.keys.forEach(it -> it.setPosition(new Position(-999, -999)));

        var game = new GameLogic(List.of(building));
        game.getPlayer().setPosition(new Position(-1, -1));
        for (int i = 0; i < game.ALIEN_SPAWN_INTERVAL * 2; ++i) {
            game.onUpdate();
        }
        assertEquals(2, game.getCurrentBuilding().getAliens().size());
    }

    /**
     * Win the game
     */
    @Test
    void endGameVictory() {
        var building = new Building(new Size(20, 20));
        final boolean[] success = {false};
        var game = new GameLogic(List.of(building));
        game.setListener(() -> success[0] = true);
        building.door.unlock();
        game.getPlayer().setPosition(building.getDoorPosition());
        game.movePlayer(Direction.STILL);
        game.onUpdate();
        assertTrue(success[0]);
    }

    /**
     * Run out of time
     */
    @Test
    void endGameOutOfTime() {
        var building = new Building(new Size(20, 20));
        final boolean[] success = {false};
        var game = new GameLogic(List.of(building));
        game.setListener(() -> success[0] = true);
        game.getPlayer().setPosition(new Position(-10, -10));
        for (int i = 0; i < game.MAX_TIME; ++i) {
            game.onUpdate();
            building.getAliens().clear();
        }
        assertTrue(success[0]);
    }

    /**
     * Die
     */
    @Test
    void endGameOutOfLife() {
        var building = new Building(new Size(20, 20));
        final boolean[] success = {false};
        var game = new GameLogic(List.of(building));
        game.setListener(() -> success[0] = true);
        game.getPlayer().setHealth(0);
        game.onUpdate();
        assertTrue(success[0]);
    }


}
