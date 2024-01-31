package model.services.save;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.MongoClientException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import model.services.save.object.Save;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * MongoSaveAdapter saves into MongoDB in human-readable(ish) format
 */
public class MongoSaveAdapter implements SaveAdapter {

    private static final String connectionString = "mongodb://localhost:27017/?serverSelectionTimeoutMS=1500";
    private static final String databaseName = "escapeFromKoc";
    private static final String saveCollectionsName = "saves";

    private final CodecProvider pojoCodecProvider;
    private final CodecRegistry pojoCodecRegistry;

    private final Logger logger = Logger.getLogger("MongoSaveAdapter");

    MongoSaveAdapter() throws MongoClientException {
        pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
        createCollections();
    }

    /**
     * Save to the database using username as the key
     * @param username the username
     * @param save the save
     */
    @Override
    public void saveGame(String username, Save save) {
        logger.log(Level.INFO, "Trying to save save data of " + username);
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName).withCodecRegistry(pojoCodecRegistry);
            var saves = database.getCollection(saveCollectionsName, Save.class);
            Bson query = eq("playerName", username);
            var result = saves.find(query).first();
            if (result != null) {
                logger.log(Level.INFO, "Overwriting save data.");
                saves.deleteOne(query);
            }
            saves.insertOne(save);
            logger.log(Level.INFO, "Save successful.");
        } catch (MongoTimeoutException ex) {
            throw ex; // propagate
        } catch (MongoClientException ex) {
            logger.log(Level.SEVERE, "error: " + ex.getMessage());
        }
    }

    /**
     * Get the Save associated with the username from the database
     * @param username the username
     * @return the Save object or null
     */
    @Override
    public Save loadGame(String username) {
        logger.log(Level.INFO, "Trying to load save data of " + username);
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName).withCodecRegistry(pojoCodecRegistry);
            var saves = database.getCollection(saveCollectionsName, Save.class);
            Bson query = eq("playerName", username);
            var result = saves.find(query).first();
            if (result != null) {
                logger.log(Level.INFO, "Retrieving save file.");
                return result;
            }
            logger.log(Level.INFO, "No save file found.");
        } catch (MongoTimeoutException ex) {
            throw ex; // propagate
        } catch (MongoClientException ex) {
            logger.log(Level.SEVERE, "error: " + ex.getMessage());
        }
        return null;
    }

    /**
     * Lazy way of syncing everything up with everyone's local mongo instances
     */
    private void createCollections() {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName).withCodecRegistry(pojoCodecRegistry);
            if (!database.listCollectionNames().into(new ArrayList<>()).contains(saveCollectionsName)) {
                database.createCollection(saveCollectionsName);
            }
        }
    }
}
