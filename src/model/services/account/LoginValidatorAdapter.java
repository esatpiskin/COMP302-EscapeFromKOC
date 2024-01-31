package model.services.account;

/**
 * Interface for password validators <br>
 * <br>
 * methods take usernames and password hashes <br>
 * <br>
 * These methods should not concern themselves with the validity of username or passwordHashes
 * and simply forward them to whatever backend they are using
 *
 */
public interface LoginValidatorAdapter {

    /**
     * Validate a login given the username and passwordHash. <br>
     * On success, returns SUCCESS <br>
     * On failure, returns INVALID_CREDENTIALS <br>
     * If an error occurs in the backend, returns UNKNOWN_ERROR <br>
     * <br>
     * @param username username
     * @param passwordHash password hash
     * @return result of the transaction
     */
    LoginValidator.Result loginValidate(String username, byte[] passwordHash);

    /**
     * register a new account with the given username and passwordHash <br>
     * <br>
     * On success, returns SUCCESS <br>
     * On failure, returns ACCOUNT_EXISTS <br>
     * <br>
     * If an error occurs in the backend, returns UNKNOWN_ERROR <br>
     * <br>
     * @param username username
     * @param passwordHash password hash
     * @return result of the transaction
     */
    LoginValidator.Result registerAccount(String username, byte[] passwordHash);
}
