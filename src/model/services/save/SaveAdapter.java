package model.services.save;

import model.services.save.object.Save;

/**
 * Interface for saving the game <br>
 * The Save class is used for serialization and deserialization
 */
public interface SaveAdapter {

    /**
     * Save the save object using the given username
     * @param username the username
     * @param save the save
     */
    void saveGame(String username, Save save);

    /**
     * Load the save for the given username <br>
     * If no save exists, return null
     * @param username the username
     * @return the save or null
     */
    Save loadGame(String username);
}
