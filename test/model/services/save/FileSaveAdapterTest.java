package model.services.save;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

class FileSaveAdapterTest {

    static String testUsername = "test_637678950";

    static Logger logger = Logger.getLogger("FileSaveAdapterTest");

    /**
     * Cleanup temporary files
     */
    @AfterAll
    static void cleanUp() {
        var file = new File("saves/" + "save_" + testUsername + ".ser" );
        if (file.exists()) {
            file.deleteOnExit();
        } else {
            logger.log(Level.WARNING, "Temporary file not found, has the implementation changed?");
        }
    }

    @Test
    void saveGame() {
        FileSaveAdapter adapter = new FileSaveAdapter();
        adapter.saveGame(testUsername, null);
    }

    @Test
    void loadGame() {
    }
}