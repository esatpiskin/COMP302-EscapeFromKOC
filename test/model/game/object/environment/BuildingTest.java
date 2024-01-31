package model.game.object.environment;

import model.game.data.Position;
import model.game.data.Size;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {

    @Test
    void getFurniturePositionsEmpty() {
        Building building = new Building(new Size(3, 3));

        var pos = building.getFurniturePositions();

        assertEquals(pos.size(), 0);
    }

    @Test
    void getFurniturePositions() {
        Building building = new Building(new Size(3, 3));
        building.addFurniture(new Position(0, 1));
        building.addFurniture(new Position(1, 2));

        var pos = building.getFurniturePositions();

        assertEquals(pos.size(), 2);

        assertAll(
                () -> assertTrue(pos.parallelStream().anyMatch(it -> it.equals(new Position(0, 1)))),
                () -> assertTrue(pos.parallelStream().anyMatch(it -> it.equals(new Position(1, 2))))
        );

    }

    @Test
    void getFreeSquares() {
        var building = new Building(new Size(2, 3));
        building.addFurniture(new Position(0, 1));
        building.addFurniture(new Position(1, 2));

        var squares = building.getFreeSquares();

        assertEquals(squares.size(), 4);
        assertTrue(squares.parallelStream().anyMatch(
                it -> it.equals(new Position(0, 0))
        ));
        assertTrue(squares.parallelStream().anyMatch(
                it -> it.equals(new Position(0, 2))
        ));
        assertTrue(squares.parallelStream().anyMatch(
                it -> it.equals(new Position(1, 0))
        ));
        assertTrue(squares.parallelStream().anyMatch(
                it -> it.equals(new Position(1, 1))
        ));
    }

    @Test
    void addFurniture() {
        var building = new Building(new Size(2, 3));
        building.addFurniture(new Position(0, 1));
        building.addFurniture(new Position(1, 2));
        // dupe
        building.addFurniture(new Position(1, 2));

        var squares = building.getFurniturePositions();

        assertEquals(squares.size(), 2);
    }

    @Test
    void isDoorPositionValid() {
        var building = new Building(new Size(2, 3));

        // valid position
        assertTrue(building.isDoorPositionValid(new Position(0, -1)));

        // inside the building
        assertFalse(building.isDoorPositionValid(new Position(0, 0)));
        // corner
        assertFalse(building.isDoorPositionValid((new Position(-1, -1))));
        // opposing corner
        assertFalse(building.isDoorPositionValid(new Position(2, 3)));
        // outside
        assertFalse(building.isDoorPositionValid((new Position(4, 5))));

    }



    @Test
    void addTwoTablesInside() {
        var building = new Building(new Size(3, 3));
        // 2 tables inside building
        building.addFurniture(new Position(2, 1), FurnitureType.TABLE);
        building.addFurniture(new Position(1, 1), FurnitureType.TABLE);


        var squares = building.getFurniturePositions();

        assertEquals(squares.size(), 2);
    }

    @Test
    void addTwoDeskInSamePosition() {
        var building = new Building(new Size(3, 3));
        // 2 Desk's in same position
        building.addFurniture(new Position(2, 2), FurnitureType.DESK);
        building.addFurniture(new Position(2, 2), FurnitureType.DESK);

        var squares = building.getFurniturePositions();

        assertEquals(squares.size(), 1);
    }

    @Test
    void addOneLibInsideOneOutside() {
        var building = new Building(new Size(3, 3));
        // 1 Lib in building 1 outside of the building
        building.addFurniture(new Position(2, 1), FurnitureType.LIB);
        building.addFurniture(new Position(4, 4), FurnitureType.LIB);

        var squares = building.getFurniturePositions();

        assertEquals(squares.size(), 1);
    }

    @Test
    void addOneBoardInsideOneOnTheWall() {
        var building = new Building(new Size(3, 3));
        // 1 Board in the bulding 1 on the wall
        building.addFurniture(new Position(1, 2), FurnitureType.BOARD);
        building.addFurniture(new Position(2, 3), FurnitureType.BOARD);

        var squares = building.getFurniturePositions();

        assertEquals(squares.size(), 1);
    }

    @Test
    void addTwoDifferentFurnituresInSameLocation() {
        var building = new Building(new Size(3, 3));

        building.addFurniture(new Position(0, 1), FurnitureType.TRASH);
        building.addFurniture(new Position(2, 2), FurnitureType.DESK);
        building.addFurniture(new Position(0, 1), FurnitureType.BOARD);


        var squares = building.getFurniturePositions();

        assertEquals(squares.size(), 2);
    }
}