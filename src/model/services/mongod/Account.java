package model.services.mongod;

/**
 * MongoDB POJO for accounts, contains username and a password digest
 */
public class Account {
    String username;

    byte[] passwordHash;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public String toString() {
        return "Account[\n" +
                "username='" + username + "'\n" +
                ']';
    }
}
