package model.game.logic;

import model.game.data.Position;
import model.game.data.Size;
import model.game.object.environment.Building;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuildLogicTest {

    @Test
    void checkFurnitureLimitsEmpty() {
        var buildings = List.of(
                new Building(new Size(5, 5)),
                new Building(new Size(5, 5))
        );
        var limits = List.of(
                0, 0
        );

        BuildLogic logic = new BuildLogic(buildings, limits);
        assertTrue(logic.checkFurnitureLimits().isEmpty());
    }

    @Test
    void checkFurnitureLimitsFail() {
        var buildings = List.of(
                new Building(new Size(5, 5)),
                new Building(new Size(5, 5))
        );
        var limits = List.of(
                0, 1
        );

        BuildLogic logic = new BuildLogic(buildings, limits);
        var result = logic.checkFurnitureLimits();
        assertTrue(result.isPresent());
    }

    @Test
    void checkFurnitureLimitsSuccess() {
        var buildings = List.of(
                new Building(new Size(5, 5)),
                new Building(new Size(5, 5))
        );
        var limits = List.of(
                2, 1
        );
        buildings.get(0).addFurniture(new Position(2, 2));
        buildings.get(0).addFurniture(new Position(2, 3));
        buildings.get(1).addFurniture(new Position(2, 2));
        buildings.get(1).addFurniture(new Position(2, 3));

        BuildLogic logic = new BuildLogic(buildings, limits);
        var result = logic.checkFurnitureLimits();
        assertTrue(result.isEmpty());
    }
}