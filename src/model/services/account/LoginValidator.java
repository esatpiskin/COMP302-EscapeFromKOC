package model.services.account;


import com.mongodb.MongoClientException;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * LoginValidator is responsible for validating credentials
 */
public class LoginValidator {

    /**
     * Enumeration for propagating errors <br>
     * TODO: maybe add a String field to propagate errors more precisely? <br>
     * TODO: add an invalid username or password format
     */
    public enum Result {
        SUCCESS,
        INVALID_CREDENTIALS,
        ACCOUNT_EXISTS,
        UNKNOWN_ERROR,
    }

    private static LoginValidator instance = null;
    
    private final LoginValidatorAdapter adapter;

    private final Logger logger = Logger.getLogger("LoginValidator");

    /**
     * Creates a new LoginValidator object by setting the adapter
     * If a mongodb connection can be established, writes to the
     */
    private LoginValidator() {
        logger.log(Level.INFO, "Creating LoginValidator.");
        LoginValidatorAdapter temp;
        try {
            temp = new MongoLoginValidator();
            logger.log(Level.INFO, "Using MongoDB for login validation");
        } catch (MongoClientException ex) {
            temp = new FileLoginValidator();
            logger.log(Level.INFO, "Using File for login validation.");
        }
        this.adapter = temp;
    }

    /**
     * Get Singleton instance of the LoginValidator
     * @return the instance
     */
    public static synchronized LoginValidator getInstance() {
        if (instance == null) {
            instance = new LoginValidator();
        }
        return instance;
    }


    /**
     * Validate credentials
     * @param username username
     * @param password password
     * @return result
     */
    public Result loginValidate(String username, char[] password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            var charset = Charset.defaultCharset();
            digest.update(charset.encode(CharBuffer.wrap(password)));
            return adapter.loginValidate(username, digest.digest());
        } catch (NoSuchAlgorithmException ex) {
            logger.log(Level.SEVERE, "SHA-256 not implemented.");
            throw new RuntimeException("SHA-256 not implemented");
        }
    }

    /**
     * Register new credentials
     * TODO: add minimum credential checks, see Issue #117
     * @param username username
     * @param password password
     * @return result
     */
    public Result registerAccount(String username, char[] password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            var charset = Charset.defaultCharset();
            digest.update(charset.encode(CharBuffer.wrap(password)));
            return adapter.registerAccount(username, digest.digest());
        } catch (NoSuchAlgorithmException ex) {
            logger.log(Level.SEVERE, "SHA-256 not implemented.");
            throw new RuntimeException("SHA-256 not implemented");
        }
    }
}
