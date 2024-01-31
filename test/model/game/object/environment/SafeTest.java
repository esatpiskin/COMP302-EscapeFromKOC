package model.game.object.environment;

import model.game.data.Position;
import model.game.data.Size;
import model.game.object.item.*;
import model.game.logic.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SafeTest {

    // Testing does safe have a key
    @Test
    @Disabled // ignoring until this is figured out
    void SafeTest_1() {

        // creating buildLogic and gameLogic to start the game
        BuildLogic buildLogic = new BuildLogic(List.of(new Building(new Size(5, 5))),List.of(new Integer[]{5}));
        buildLogic.finalizeBuildings();

        GameLogic gameLogic = new GameLogic(buildLogic.getBuildings());

        /* checking for key that assigned to open the safe
        * key.getLock() gives which object does it open (door, safe)
        * gameLogic.getCurrentBuilding().safe == safe that in the current building
        */
        gameLogic.getCurrentBuilding().keys.forEach(key -> assertEquals(key.getLock(),gameLogic.getCurrentBuilding().safe));
    }
    // testing if safe is created
    @Test
    void SafeTest_2() {
        // only creating a building simplifies the test
        var building = new Building(new Size(3, 3));

        // adding safe and key that opens the safe
        building.addSafe();

        // changing the place of safe (initially all safes are out of bounds)
        building.setSafePosition(new Position(1,1));

        // checking safe is in the position that we put
        assertEquals(building.getSafePosition(), new Position(1,1));
    }

    //testing does safe have a key inside
    @Test
    void SafeTest_3() {
        // creating building and safe, initializing safe's place
        var building = new Building(new Size(3, 3));
        building.addSafe();
        building.setSafePosition(new Position(1,1));

        // checking key list to find there is a key inside of safe or not.
        // also if it is the key that inside in safe, it should open the door
        building.keys.forEach(key -> {
            if (key.getPosition() == building.getSafePosition()) {
                assertEquals(key.getLock(), building.door);
            }
        });
    }

    //testing if getting normal key opens the door
    @Test
    void SafeTest_4() {
        // creating building and safe, initializing safe's place
        var building = new Building(new Size(3, 3));
        building.addSafe();
        building.keys.add(new Key(new Position(2,2), building.safe));
        building.setSafePosition(new Position(1,1));

        // picking up normal key
        building.pickupKey(new Position(2,2));

        // door shouldn't be open
        assertFalse(building.door.isOpen());

    }
    //Testing key inside of safe opens the door
    @Test
    void SafeTest_5() {
        // creating building and safe, initializing safe's place

        var building = new Building(new Size(3, 3));
        building.addSafe();
        building.keys.add(new Key(new Position(2,2), building.safe));
        building.setSafePosition(new Position(1,1));

        //picking up normal key
        building.pickupKey(new Position(2,2));

        //picking up safe key
        building.pickupKey(new Position(1,1));

        //door should be opened
        assertTrue(building.door.isOpen());

    }

    /*testing randomizing places of safe and key, and also checking they are on top of
    each other or not  */
    @Test
    @Disabled
    void SafeTest_6() {

        // creating building and two furniture to put key and safe behind them

        var building = new Building(new Size(3, 3));
        building.addFurniture(new Position(1,2));
        building.addFurniture(new Position(2,2));

        // adding safe and key
        building.addSafe();
        building.keys.add(new Key(new Position(0,0), building.safe));

        //randomizing safe's and key's place
        building.randomizeKeyPositions();
        building.randomizeSafePosition();

        //checking if they are on top of each other or not
        building.keys.forEach(key -> {

            // if key doesn't open the door, it should be opening the safe and that
            // means key and safe are seperate object
            if (key.getLock() != building.door){

                //if their position is not equal, test should be passed
                assertNotEquals(key.getPosition(),building.getSafePosition());
            }
        });

    }

}

