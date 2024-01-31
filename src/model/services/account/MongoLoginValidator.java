package model.services.account;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.MongoClientException;
import model.services.mongod.Account;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;

import java.util.ArrayList;

public class MongoLoginValidator implements LoginValidatorAdapter {

    private static final String connectionString = "mongodb://localhost:27017/?serverSelectionTimeoutMS=1500";
    private static final String databaseName = "escapeFromKoc";
    private static final String accountCollectionsName = "accounts";

    private final CodecProvider pojoCodecProvider;
    private final CodecRegistry pojoCodecRegistry;

    /**
     * Create a new MongoLoginValidator instance. This constructor also creates the necessary account collection
     * @throws MongoClientException if connection to the database fails
     */
    public MongoLoginValidator() throws MongoClientException {
        pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
        createCollections();
    }

    /**
     * Validate an account by connecting to MongoDB
     * @param username account to validate
     * @param passwordHash password hash
     * @return Result of the transaction
     */
    @Override
    public LoginValidator.Result loginValidate(String username, byte[] passwordHash) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName).withCodecRegistry(pojoCodecRegistry);
            var accounts = database.getCollection(accountCollectionsName, Account.class);
            Bson query = and(eq("username", username), eq("passwordHash", passwordHash));
            var used = accounts.find(query).first();
            if (used != null) {
                return LoginValidator.Result.SUCCESS;
            } else {
                return LoginValidator.Result.INVALID_CREDENTIALS;
            }
        } catch (MongoClientException ex) {
            return LoginValidator.Result.UNKNOWN_ERROR;
        }
    }

    /**
     * Create an account by connecting to MongoDB
     * @param username account to create
     * @param passwordHash password Hash
     * @return Result of the transaction
     */
    @Override
    public LoginValidator.Result registerAccount(String username, byte[] passwordHash) {
        var account = new Account();
        account.setUsername(username);
        account.setPasswordHash(passwordHash);
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName).withCodecRegistry(pojoCodecRegistry);
            var accounts = database.getCollection(accountCollectionsName, Account.class);
            var used = accounts.find(eq("username", username)).first();
            if (used == null) {
                var result = accounts.insertOne(account);
                if (result.wasAcknowledged() && result.getInsertedId() != null) {
                    return LoginValidator.Result.SUCCESS;
                } else {
                    return LoginValidator.Result.UNKNOWN_ERROR;
                }
            } else {
                return LoginValidator.Result.ACCOUNT_EXISTS;
            }
        } catch (MongoClientException ex) {
            return LoginValidator.Result.UNKNOWN_ERROR;
        }
    }

    /**
     * Lazy way of syncing everything up with everyone's local mongo instances
     */
    private void createCollections() {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName).withCodecRegistry(pojoCodecRegistry);
            if (!database.listCollectionNames().into(new ArrayList<>()).contains(accountCollectionsName)) {
                database.createCollection(accountCollectionsName);
            }
        }
    }
}
