package model.services.save;

import com.mongodb.MongoClientException;
import model.services.save.object.Save;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SaveManager manages writing and reading save files
 */
public class SaveManager {

    private static SaveManager instance = null;

    private SaveAdapter adapter;

    private final Logger logger = Logger.getLogger("SaveManager");

    /**
     * Construct a new SaveManager instance <br>
     * Save Manager prefers saving to MongoDB if available but will save to File otherwise
     */
    private SaveManager() {
        logger.log(Level.INFO, "Creating LoginValidator.");
        SaveAdapter temp;
        try {
            temp = new MongoSaveAdapter();
            logger.log(Level.INFO, "Using MongoDB for saving");
        } catch (MongoClientException ex) {
            temp = new FileSaveAdapter();
            logger.log(Level.INFO, "Using File for saving.");
        }
        this.adapter = temp;
    }

    /**
     * Get the SaveManager instance
     * @return the instance
     */
    public static synchronized SaveManager getManager() {
        if (instance == null) {
            instance = new SaveManager();
        }
        return instance;
    }

    /**
     * Save the game using the adapter
     * @param username the username
     * @param save the save
     */
    public void saveGame(String username, Save save) {
        try {
            adapter.saveGame(username, save);
        } catch (MongoClientException ex) {
            logger.log(Level.WARNING, "MongoDB disconnected, using the FileAdapter");
            adapter = new FileSaveAdapter();
            adapter.saveGame(username, save);
        }
    }

    /**
     * Load the game using the adapter
     * @param username the username
     * @return the save file or null
     */
    public Save loadGame(String username) {
        try {
            return adapter.loadGame(username);
        } catch (MongoClientException ex) {
            logger.log(Level.WARNING, "MongoDB disconnected, using the FileAdapter");
            adapter = new FileSaveAdapter();
            return adapter.loadGame(username);
        }
    }
}
