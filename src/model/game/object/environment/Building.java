package model.game.object.environment;


import model.game.data.IntoDrawable;
import model.game.data.Position;
import model.game.data.Size;
import model.game.object.enemy.Alien;
import model.game.object.item.Key;
import ui.drawable.Drawable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Building class holds its furniture, the door, and the key
 */
public class Building implements IntoDrawable {

    private String name = "UNNAMED";
    private Size size;
    private List<Furniture> furniture;
    public final Door door;
    public final Door door_prev;
    public final List<Key> keys;
    private final List<Alien> aliens;

    public Safe safe;

    /**
     * Construct a new Building with the size of size
     * Also automatically places a Door on (1, -1) and a key on (-1, -1)
     * @param size new building size
     */
    public Building(Size size) {
        this.size = size;
        this.door = new Door(new Position(1, -1));
        this.door_prev = new Door(new Position(4, -1));
        this.keys = new ArrayList<>();
        this.furniture = new ArrayList<>();
        this.aliens = new ArrayList<>();
        addSafe();
    }

    /**
     * Get the name of the building
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the building
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the size of the building
     * @return the size
     */
    public Size getSize() {
        return size;
    }

    /**
     * Set the size of the building <br>
     * No checks are performed to ensure the objects in the building remain inside
     * @param size new size
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Get the position of the door
     * @return the position
     */
    public Position getDoorPosition() { return door.getPosition(); }

    public Position getPrevDoorPosition() { return door_prev.getPosition(); }

    /**
     * Set the exit door position to the given position iff the position is valid
     * @param position position to place the door to
     */
    public void setExitDoorPosition(Position position) {
        if(isDoorPositionValid(position)){
            door.setPosition(position);
        }
    }

    /**
     * Set the exit door position to the given position iff the position is valid
     * @param position position to place the door to
     */
    public void setEntryDoorPosition(Position position) {
        if(isDoorPositionValid(position)){
            door_prev.setPosition(position);
        }
    }

    public void createSafe(Position position, Key key) {
        this.safe = new Safe(position, key);
    }

    /**
     * Add the safe, create a key, and register the key inside it for the door
     */
    @Deprecated
    public void addSafe() {

        Key key = new Key(new Position(-1, -1), this.door);
        keys.add(key);
        this.safe = new Safe(new Position(-1,1), key);

        Key safeKey = new Key(new Position(-1, -1), safe);
        keys.add(safeKey);
    }

    /**
     * Check if a specific door position is valid for the specified position
     * @param position position to check
     * @return true if position is valid, false otherwise
     */
    public boolean isDoorPositionValid(Position position) {
        if (position.equals(door.getPosition()) || position.equals(door_prev.getPosition())) {
            return false;
        }

        int x = position.x();
        int y = position.y();

        boolean objectCheck;

        boolean positionCheck = (y == -1 && x < size.x() && x > -1) || // top
        (y == size.y() && x < size.x() && x > -1) || // bottom
                (x == -1 && y < size.y() && y > -1) ||
                (x == size.x() && y < size.y() && y > -1);
        // early return to not iterate over all buildings
        if (!positionCheck)
            return false;

        if (x == -1 || x == this.size.x()) {
            objectCheck =
                    furniture.parallelStream().anyMatch(furniture ->
                            furniture.getPosition().equals(new Position(x + 1, y))
                                    || furniture.getPosition().equals(new Position(x - 1, y)));
        } else {
            objectCheck =
                    furniture.parallelStream().anyMatch(furniture ->
                            furniture.getPosition().equals(new Position(x, y + 1)) ||
                                    furniture.getPosition().equals(new Position(x, y - 1))
                    );
        }
        return !objectCheck;
    }

    public void setSafePosition(Position position) { safe.setPosition(position); }

    public Position getSafePosition() { return safe.getPosition(); }

    /**
     * Put the key on a random furniture, or -1, -1 if there are no other furniture
     */
    public void randomizeKeyPositions() {
        if (keys.size() > furniture.size()) {
            placeRandomObjects(keys.size() - furniture.size());
        }

        var positions = new ArrayList<>(getFurniturePositions());
        Collections.shuffle(positions);

        for (int i = 0; i < keys.size(); ++i) {
            keys.get(i).setPosition(positions.get(i));
        }
    }

    /**
     * Place n number of random objects
     * @param n number of
     */
    public void placeRandomObjects(int n) {
        int target = furniture.size() + n;
        while (furniture.size() < target) {
            addFurniture(this.getRandomFreeSquare(), FurnitureType.random());
        }
    }

    @Deprecated
    public void placeInitialObjects(){
        int size = furniture.size();
        if (size < 3) {
            placeRandomObjects(3);
        }
    }

    /**
     * Get a list of Position objects containing the position of every furniture in the building
     * @return the list of furniture positions
     */
    public List<Position> getFurniturePositions() {
        return furniture.stream().map(Furniture::getPosition).collect(Collectors.toList());
    }

    /**
     * Add furniture if one does not exist in position
     * @param position   position to add furniture, 0 indexed from inside the building
     * @param furnitureType type of furniture to be added
     */
    public void addFurniture(Position position, FurnitureType furnitureType) {
        if (furniture.stream().noneMatch(furniture ->
                furniture.getPosition().equals(position))) {
            if (position.x() < this.size.x() && position.y() < this.size.y()) {

                double x1 = (getDoorPosition().x() - position.x()) * (getDoorPosition().x() - position.x());
                double y1 = (getDoorPosition().y() - position.y()) * (getDoorPosition().y() - position.y());

                double dist1 = Math.sqrt((x1 + y1));


                double x2 = (getPrevDoorPosition().x() - position.x()) * (getPrevDoorPosition().x() - position.x());
                double y2 = (getPrevDoorPosition().y() - position.y()) * (getPrevDoorPosition().y() - position.y());

                double dist2 = Math.sqrt((x2 + y2));

                if(dist1 > 1 && dist2 > 1){
                    furniture.add(new Furniture(position, furnitureType));
                }
            }


        }
    }

    @Deprecated
    public void randomizeSafePosition() {
        placeInitialObjects();
        var rng = new Random();
        int size = furniture.size();
        var pos1 = rng.nextInt(size);
        safe.setPosition(getFurniturePositions().get(pos1));
        keys.forEach(key -> {
            safe.setPosition(
                    getFurniturePositions().stream().filter(it -> !it.equals(key.getPosition())).findAny().get());
        });


    }

    /**
     * Add furniture of type TABLE to a specified position, mostly used for convenience
     * @param position position to add furniture, 0 indexed from inside the building
     */
    public void addFurniture(Position position) {
        addFurniture(position, FurnitureType.TABLE);
    }

    /**
     * Remove furniture from position if one exists there
     * @param position position to remove furniture, 0 indexed from inside the building
     */
    public void removeFurniture(Position position) {
        furniture = furniture.stream().filter(furniture ->
            !furniture.getPosition().equals(position)
        ).collect(Collectors.toList());
    }

    /**
     * Get the Drawable object of the building
     * @return the building Drawable
     */
    public Drawable intoDrawable() {
        return new ui.drawable.Building(this.size.x(), this.size.y());
    }


    /**
     * Get the Drawable objects of other objects inside the building implementing IntoDrawable <br>
     * TODO: consider merging this method with intoDrawable as any caller would likely want the building drawwable
     * included with this
     * @return the collection of drawables
     */
    public Collection<? extends Drawable> getDrawableChildren() {
        List<Drawable> drawables = new ArrayList<>();
        drawables.add(door.intoDrawable());
        drawables.add(door_prev.intoDrawable());
        drawables.addAll(keys.stream().map(Key::intoDrawable).toList());
        drawables.addAll(aliens.stream().map(Alien::intoDrawable).toList());
        drawables.add(safe.intoDrawable());
        for (var furn: furniture) {
            drawables.add(furn.intoDrawable());
        }

        return drawables;
    }

    /**
     * Return true if target position is not occupied
     * Return false if target position is occupied
     * @param target position to try to move
     * @return if it is possible to move
     */
    public boolean tryMove(Position target) {
        if (target.x() < 0 || target.x() >= size.x() || target.y() < 0 || target.y() >= size.y()) {
            return false;
        }
        return furniture.stream().noneMatch(furniture -> furniture.getPosition().equals(target));
    }

    /**
     * Get squares unoccupied by furniture
     * @return list of free squares
     */
    public List<Position> getFreeSquares() {
        var all_squares = IntStream.range(0, this.size.x()).mapToObj(i ->
                IntStream.range(0, this.size.y()).mapToObj(j -> new Position(i, j))).flatMap(Function.identity()).toList();

        return all_squares.stream().
                filter(square -> this.getFurniturePositions().stream().noneMatch(square::equals))
                .filter(square -> this.getAliens().stream().map(Alien::getPosition).noneMatch(square::equals))
                .toList();
    }

    /**
     * Get a random square that does not contain any furniture
     * @return a position that is unoccupied
     */
    public Position getRandomFreeSquare() {
        Random rng = new Random();
        var free = getFreeSquares();
        return free.get(rng.nextInt(free.size()));
    }

    /**
     * Get a random square that contains furniture
     * @return a position that is occupied
     */
    public Position getRandomFurnitureSquare() {
        Random rng = new Random();
        var filled = getFurniturePositions();
        return filled.get(rng.nextInt(filled.size()));
    }

    /**
     * Attempt picking up a key from position
     * @param position position to pick up from
     */
    public void pickupKey(Position position) {
        Key found = null;
        for (var key: keys) {
            if (key.getPosition().equals(position)) {
                found = key;
                break;
            }
        }
        if (found != null)
            found.pickUp();
    }

    public void findSafe(Position position) {

        if (safe.getPosition().equals(position)) {
            safe.findSafe();
        }
    }
    public List<Furniture> getFurniture() {
        return this.furniture;
    }

    public void setFurniture(List<Furniture> furniture) {
        this.furniture = furniture;
    }

    /**
     * Get the list of aliens
     * @return the list of aliens
     */
    public List<Alien> getAliens() {
        return aliens;
    }

    /**
     * Get the maximum number of aliens this building should contain <br>
     * The max is ceil(tiles / 20)
     * @return maximum alien number
     */
    public int maxAliens() {
        int area = this.size.area();
        int div = area / 20;
        var rem = (area % 20) > 0 ? 1 : 0;
        return div + rem;
    }
}
