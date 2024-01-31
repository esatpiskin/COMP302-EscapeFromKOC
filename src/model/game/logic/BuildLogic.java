package model.game.logic;

import model.game.data.Position;
import model.game.data.Size;
import model.game.object.environment.Building;
import model.game.object.environment.FurnitureType;
import ui.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * This class encapsulates most of the build mode related logic, it is responsible for resizing buildings,
 * moving the door, placing and removing furniture, ensuring sufficient number of furniture are placed
 * and ensuring everything is in a valid state
 */
public class BuildLogic {

    private final List<Building> buildings;

    private int selected;

    private final List<Integer> buildingLimits;

    private final Logger logger = Logger.getLogger("BuildLogic");


    /**
     * Create a new BuildLogic object with the given names and limits, the length of these lists determine how many
     * buildings are in the game, the order of the names are preserved for the game
     * @throws IllegalArgumentException if the names and limits lists have different sizes
     * @param buildings map of buildings
     * @param limits minimum furniture number of the buildings
     */
    public BuildLogic(List<Building> buildings, List<Integer> limits) {
        this.buildings = buildings;
        this.buildingLimits = limits;
        selected = 0;
    }

    /**
     * Initially distribute some random furniture through a building
     */
    public void placeInitialObjects() {
        buildings.forEach(Building::placeInitialObjects);
    }

    /**
     * Check if the number of placed furniture conforms to the numbers in limits
     * @return empty if OK, else the missing furniture numbers
     */
    public Optional<String> checkFurnitureLimits() {
        StringBuilder error = new StringBuilder();
        for (int i = 0; i < buildings.size(); ++i) {
            var building = buildings.get(i);
            var missing = buildingLimits.get(i) - building.getFurniture().size();
            if (missing > 0) {
                error.append("Building ").append(building.getName()).append(" missing: ").append(missing).append(" furniture").append("\n");
            }
        }
        if (error.length() > 0) {
            return Optional.of(error.toString());
        } else {
            return Optional.empty();
        }
    }

    /**
     * Get an iterator of Drawables from the encapsulated buildings
     * @return the iterable of Drawables
     */
    public Iterable<Drawable> getGraphics() {
        List<Drawable> drawables = new ArrayList<>();

        drawables.add(buildings.get(selected).intoDrawable());
        drawables.addAll(buildings.get(selected).getDrawableChildren());
        return drawables;
    }

    /**
     * Get minimum furniture number of buildings
     * @return list of minimum furniture numbers
     */
    public List<Integer> getBuildingLimits() {
        return buildingLimits;
    }

    /**
     * Get a Map object containing all buildings were the keys are the building names
     * Buildings do not know their own name, so it is container in this Map
     * @return Map of buildings
     */
    public List<Building> getBuildings() {
        return buildings;
    }

    /**
     * Get the size of the currently selected building
     * @return size of the selected building
     */
    public Size getCurrentBuildingSize() {
        return buildings.get(selected).getSize();
    }

    /**
     * Change the size of the currently selected building <br>
     * @param size the new size
     */
    public void changeBuildingSize(Size size) {
        if (size.x()> 5 && size.y() > 5) {
            buildings.get(selected).setSize(size);
        }
    }


    /**
     * Add furniture of FurnitureType to the specified position for the currently selected building
     * @param position position to place furniture to
     * @param type type of the furniture
     */
    public void addFurniture(Position position, FurnitureType type) {
        buildings.get(selected).addFurniture(position, type);
    }

    /**
     * Remove furniture from the specified position in the selected building if there is one
     * @param position position to remove furniture from
     */
    public void removeFurniture(Position position) {
        buildings.get(selected).removeFurniture(position);
    }

    /**
     * Move the exit door to the specified position in the selected building
     * @param position position to move the door to
     */
    public void moveExitDoor(Position position) {
        buildings.get(selected).setExitDoorPosition(position);
    }

    /**
     * Move the entry door to the specified position in the selected building
     * @param position position to move the door to
     */
    public void moveEntryDoor(Position position) {
        buildings.get(selected).setEntryDoorPosition(position);
    }

    /**
     * Change the current building to building with the index
     *
     * @param index new building to select
     */
    public void setCurrentBuildingIndex(int index) {
        if (index >= 0 && index < buildings.size()) {
            selected = index;
        } else {
            //logger.log(Level.SEVERE, "setCurrentBuildingIndex out of bounds: " + index);
            throw new RuntimeException("setCurrentBuildingIndex out of bounds: " + index);
        }
    }

    /**
     * This method should be called when the user is done to handle additional tasks such as automatically
     * distributing keys
     */
    public void finalizeBuildings() {
        buildings.forEach(Building::randomizeKeyPositions);
        for (var building : buildings) {
            building.door_prev.unlock();
        }
        buildings.forEach(Building::randomizeSafePosition);
        buildings.get(0).door_prev.close();
    }

    /**
     * Get the List of sizes of all buildings
     * @return the list of sizes
     */
    public List<Size> getBuildingSizes() {
        return buildings.stream().map(Building::getSize).toList();
    }
}
