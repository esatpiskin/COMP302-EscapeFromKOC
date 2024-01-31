package model.services.account;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

class FileLoginValidator implements LoginValidatorAdapter {

    public FileLoginValidator() {

    }

    @Override
    public LoginValidator.Result loginValidate(String username, byte[] passwordHash) {
        // Read the login data from the file and store it in a HashMap
        HashMap<String, String> loginData = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("registration.txt"));
            String line1 = reader.readLine();
            String line2 = reader.readLine();
            while (line1 != null && line2 != null) {
                loginData.put(line1, line2);
                line1 = reader.readLine();
                line2 = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            return LoginValidator.Result.UNKNOWN_ERROR;
        }

        // Check if the provided credentials are valid by looking them up in the HashMap
        if (loginData.containsKey(username) && loginData.get(username).equals(Arrays.toString(passwordHash))) {
            return LoginValidator.Result.SUCCESS;
        } else {
            return LoginValidator.Result.INVALID_CREDENTIALS;
        }
    }


    @Override
    public LoginValidator.Result registerAccount(String username, byte[] passwordHash) {
        HashMap<String, String> registerData = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("registration.txt"));
            String line1 = reader.readLine();
            String line2 = reader.readLine();
            while (line1 != null && line2 != null) {
                registerData.put(line1, line2);
                line1 = reader.readLine();
                line2 = reader.readLine();
            }
            reader.close();
        } catch (Exception ex) {
            return LoginValidator.Result.UNKNOWN_ERROR;
        }
        // Check if the provided credentials are valid by looking them up in the HashMap
        if (registerData.containsKey(username)) {
            return LoginValidator.Result.ACCOUNT_EXISTS;
        }
        else {
            // Append the username and password to the "registration.txt" file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("registration.txt", true))) {
                writer.write(username);
                writer.newLine();
                writer.write(Arrays.toString(passwordHash));
                writer.newLine();
            } catch (IOException ex) {
                return LoginValidator.Result.UNKNOWN_ERROR;
            }
        }

        return LoginValidator.Result.SUCCESS;
    }
}
