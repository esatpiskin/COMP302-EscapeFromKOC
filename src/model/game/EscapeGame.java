package model.game;

import model.game.data.Phase;
import model.game.data.Position;
import model.game.data.Size;
import model.game.logic.BuildLogic;
import model.game.logic.GameLogic;
import model.game.logic.IGameLogicListener;
import model.game.object.Player;
import model.game.object.enemy.AlienFactory;
import model.game.object.enemy.AlienType;
import model.game.object.environment.Building;
import model.game.object.environment.Door;
import model.game.object.environment.FurnitureType;
import model.game.object.environment.Safe;
import model.game.object.item.Key;
import model.services.save.object.BuildingSave;
import model.services.save.object.GameObject;
import model.services.save.object.PlayerSave;
import model.services.save.object.Save;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The top level class for the model.game package encapsulating all game related domain logic <br>
 * Also keeps track of and controls state information and handles saving and loading <br>
 */
public class EscapeGame {

    private String playerName = "dev";

    private final List<Building> buildings = new ArrayList<>();

    private BuildLogic buildLogic;
    private GameLogic gameLogic;

    private Phase gameState = Phase.BUILD;

    public IGameLogicListener listener;

    private final Logger logger = Logger.getLogger("EscapeGame");

    /**
     * Get the game phase
     * @return the phase
     */
    public Phase getGameState() {
        return gameState;
    }

    /**
     * This function handles transitions between certain game states
     * A transition from build mode to game mode and a transition between
     * paused to game mode is handled differently
     * @param gameState game state to transition to
     */
    public void setGameState(Phase gameState) {
        if (gameState == Phase.PLAY_RUNNING && this.gameState == Phase.BUILD) {
            buildLogic.finalizeBuildings();
            gameLogic.resumeGame();
        } else if (gameState == Phase.PLAY_RUNNING) {
            gameLogic.resumeGame();
        } else if (gameState == Phase.PLAY_PAUSED) {
            gameLogic.pauseGame();
        }
        this.gameState = gameState;
    }

    /**
     * Construct a new EscapeGame object with the internally specified building names and limits
     */
    public EscapeGame(IGameLogicListener listener) {
        this.listener = listener;
        newGame();
    }

    /**
     * Start a new game
     */
    public void newGame() {
        List<String> buildingNames = List.of(new String[]{"OMER", "CASE", "SOS", "SCI", "ENG", "SNA"});
        List<Integer> buildingLimits = List.of(new Integer[]{5, 7, 10, 14, 19, 25});
        // Static check
        if (buildingNames.size() != buildingLimits.size()) {
            throw new IllegalArgumentException(
                    String.format("Size of names and limits must match, but were %d and %d", buildingNames.size(), buildingLimits.size()));
        }
        buildings.clear();
        for (var name: buildingNames) {
            var building = new Building(new Size(5, 5));
            building.setName(name);
            buildings.add(building);
        }

        buildLogic = new BuildLogic(buildings, buildingLimits);
        gameLogic = new GameLogic(buildings);
        gameLogic.setListener(listener);
        buildLogic.placeInitialObjects();
    }



    /**
     * Get the GameLogic object
     * @return the GameLogic object
     */
    public GameLogic getGameLogic() {
        return gameLogic;
    }

    /**
     * Get the BuildLogic object
     * @return the BuildLogic object
     */
    public BuildLogic getBuildLogic() {
        return buildLogic;
    }

    /**
     * Get the player name
     * @return the player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Set the player name
     * @param playerName the player name
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Get the list of building names
     * @return building names
     */
    public List<String> getBuildingNames() {
        return this.buildings.stream().map(Building::getName).toList();
    }

    /**
     * Set current building to buildingName
     * @param buildingName name of the building
     */
    public void setCurrentBuilding(String buildingName) {
        var idx = getBuildingNames().indexOf(buildingName);
        buildLogic.setCurrentBuildingIndex(idx);
    }

    /**
     * Convert the Model structures into a serializable Save POJO
     * @implNote this can only handle two keys, one in the safe, one outside
     * @return the Save
     */
    public Save saveGame() {
        Save save = new Save();
        save.setPlayerName(this.playerName);
        save.setGameState(gameState.toString());

        // player related info
        PlayerSave playerSave = new PlayerSave();
        playerSave.setPosition(gameLogic.getPlayer().getPosition().toPair());
        playerSave.setHealth(gameLogic.getPlayer().getHealth());
        playerSave.setCurrentBuilding(gameLogic.getCurrentBuildingIndex());
        // TODO: power up bag
        save.setPlayer(playerSave);


        List<BuildingSave> buildings = new ArrayList<>();
        int index = 0;
        for (String name: this.getBuildingNames()) {
            var building = this.buildings.get(index);
            var buildingSave = new BuildingSave();
            buildingSave.setName(name);
            buildingSave.setSize(building.getSize().toPair());
            List<GameObject> gameObjects = new ArrayList<>();
            // furniture
            for (var furniture : building.getFurniture()) {
                GameObject object = new GameObject();
                object.setType("furniture");
                object.setPosition(furniture.getPosition().toPair());
                object.setSubType(furniture.getFurnitureType().name());
                gameObjects.add(object);
            }
            // aliens
            for (var alien : building.getAliens()) {
                GameObject object = new GameObject();
                object.setType("alien");
                object.setPosition(alien.getPosition().toPair());
                object.setSubType(alien.getAlienType().name());
                gameObjects.add(object);
                // TODO: projectiles maybe?
            }
            // doors
            {
                GameObject door1 = new GameObject();
                door1.setType("door");
                door1.setPosition(building.door.getPosition().toPair());
                door1.setSubType("exit");
                var prop1 = new HashMap<String, String>();
                prop1.put("open", String.valueOf(building.door.isOpen()));
                door1.setProperties(prop1);
                gameObjects.add(door1);

                GameObject door2 = new GameObject();
                door2.setType("door");
                door2.setPosition(building.door_prev.getPosition().toPair());
                door2.setSubType("entry");
                var prop2 = new HashMap<String, String>();
                prop2.put("open", String.valueOf(building.door_prev.isOpen()));
                door2.setProperties(prop2);
                gameObjects.add(door2);
            }
            // keys
            for (var key : building.keys) {
                GameObject object = new GameObject();
                object.setType("key");
                object.setPosition(key.getPosition().toPair());
                var prop = new HashMap<String, String>();
                prop.put("picked", String.valueOf(key.isPicked()));
                prop.put("enabled", String.valueOf(key.isEnabled()));
                if (key.getLock() instanceof Door) {
                    prop.put("lock", "door");
                } else if (key.getLock() instanceof Safe) {
                    prop.put("lock", "safe");
                }
                object.setProperties(prop);
                gameObjects.add(object);
            }
            // safe
            {
                GameObject safe = new GameObject();
                safe.setType("safe");
                safe.setPosition(building.safe.getPosition().toPair());
                gameObjects.add(safe);
            }
            // TODO: power up
            buildingSave.setObjects(gameObjects);
            buildings.add(buildingSave);
            index++;
        }
        save.setBuildings(buildings);
        
        return save;
    }

    /**
     * Restore domain structures using a Save object
     * @param save save to restore
     */
    public void loadGame(Save save) {
        if (save == null) {
            logger.log(Level.INFO, "No save files available.");
            return;
        }
        this.playerName = save.getPlayerName();
        this.gameState = Phase.valueOf(save.getGameState());

        Player player = new Player(new Position(save.getPlayer().getPosition()));
        player.setHealth(save.getPlayer().getHealth());
        this.gameLogic.setPlayer(player);

        AlienFactory alienFactory = new AlienFactory();

        // buildings
        List<Building> newBuildings = new ArrayList<>();
        for (var buildingSave: save.getBuildings()) {
            Building building = new Building(new Size(buildingSave.getSize()));
            building.setName(buildingSave.getName());
            for (var obj: buildingSave.getObjects()) {
                switch (obj.getType()) {
                    case "furniture" -> building.addFurniture(new Position(obj.getPosition()), FurnitureType.valueOf(obj.getSubType()));
                    case "alien" -> building.getAliens().add(alienFactory.getAlienOfTypeOn(AlienType.valueOf(obj.getSubType()), new Position(obj.getPosition())));
                    case "door" -> {
                        switch (obj.getSubType()) {
                            case "entry" -> {
                                building.door_prev.setPosition(new Position(obj.getPosition()));
                                if ("true".equals(obj.getProperties().get("open"))) {
                                    building.door_prev.unlock();
                                }
                            }
                            case "exit" -> {
                                building.door.setPosition(new Position(obj.getPosition()));
                                if ("true".equals(obj.getProperties().get("open"))) {
                                    building.door.unlock();
                                }
                            }
                        }
                    }
                    case "key" -> {
                        var key = new Key(new Position(obj.getPosition()), null);
                        if (obj.getProperties().get("lock").equals("door")) {
                            key.setLock(building.door);
                            building.safe.setKeyInside(key);
                        } else if (obj.getProperties().get("lock").equals("safe")) {
                            key.setLock(building.safe);
                        }
                        if ("true".equals(obj.getProperties().get("picked"))) {
                            key.pickUp();
                        }
                        if ("true".equals(obj.getProperties().get("enabled"))) {
                            key.setEnabled(true);
                        } else {
                            key.setEnabled(false);
                        }
                        building.keys.add(key);
                    }
                    case "safe" -> building.safe.setPosition(new Position(obj.getPosition()));
                    default -> {

                    }
                }
            }
            newBuildings.add(building);
        }
        this.buildings.clear();
        this.buildings.addAll(newBuildings);
        this.gameLogic.setCurrentBuildingIndex(save.getPlayer().getCurrentBuilding());
    }

}
