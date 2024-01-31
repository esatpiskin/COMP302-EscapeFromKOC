package model.services.save;

import model.services.save.object.Save;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileSaveAdapter implements SaveAdapter {

    Logger logger = Logger.getLogger("FileSaveAdapter");

    private final String root = "saves/";

    public FileSaveAdapter() {
        logger.log(Level.INFO, "Setting up...");
        File saveDir = new File(root);
        if (saveDir.exists()) {
            logger.log(Level.INFO, "Save directory already exists.");
        } else {
            logger.log(Level.INFO, "Creating new directory.");
            if (!saveDir.mkdir())  {
                logger.log(Level.SEVERE, "Unable to create directory.");
            }
            logger.log(Level.INFO, "Directory successfully created.");
        }
    }

    @Override
    public void saveGame(String username, Save save) {
        logger.log(Level.INFO, "Attempting to save game...");
        String name = root + "save_" + username + ".ser";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(name))) {
            out.writeObject(save);
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.log(Level.SEVERE, "An IO error has occurred.");
        }
        logger.log(Level.INFO, "Finished saving game.");
    }

    @Override
    public Save loadGame(String username) {
        logger.log(Level.INFO, "Attempting to load game...");
        String name = root + "save_" + username + ".ser";
        Save save;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(name))) {
            save = (Save) in.readObject();
            logger.log(Level.INFO, "Finished loading game.");
            return save;
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.log(Level.SEVERE, "An IO error has occurred.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "Unable to map class.");
        }
        return null;
    }
}
