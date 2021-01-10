package com.kingcourier;

import java.io.IOException;
import java.nio.file.Paths;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonManager {

    public static User createUserFile(String username, String publicKey, String privateKey) {
        try {
            User user = new User(username, publicKey, privateKey);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(Paths.get(ConfigManager.USER_FILE_PATH).toFile(), user);
            return user;
        } catch (IOException e) {
            System.out.println("Failed to create userfile.");
            return null;
        }
    }
}
