package com.kingcourier;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonManager {

    private static final String USERNAME_CONST= "username";
    private static final String PUBLIC_KEY_CONST= "publickey";
    private static final String PRIVATE_KEY_CONST= "privatekey";

    public static User createUserFile(String username, String publicKey, String privateKey) {
        try {
            User user = new User(username, publicKey, privateKey);
            var userTemplate = new HashMap<String, Object>();
            userTemplate.put(USERNAME_CONST, username);
            userTemplate.put(PUBLIC_KEY_CONST, publicKey);
            userTemplate.put(PRIVATE_KEY_CONST, privateKey);

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(Paths.get(ConfigManager.USER_FILE_PATH).toFile(), userTemplate);
            return user;
        } catch (IOException e) {
            System.out.println("Failed to create userfile.");
            return null;
        }
    }
}
