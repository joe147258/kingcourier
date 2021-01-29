package com.kingcourier.configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingcourier.User;


public class JsonManager {

    public static User createUserFile(String username, String publicKey, String privateKey) throws IOException {
        User user = new User(username, publicKey, privateKey);
        var userTemplate = new HashMap<String, String>();
        userTemplate.put("username", user.getUsername());
        userTemplate.put("publickey", user.getPublicKey());
        userTemplate.put("privatekey", user.getPrivateKey());
        userTemplate.put("authtoken", "EMPTY");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(Paths.get(ConfigManager.USER_FILE_PATH).toFile(), userTemplate);
        return user;
    }

    public static void addTokenToUserFile(String authToken) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        var userTemplate = new HashMap<String, String>();
        userTemplate = mapper.readValue(new File(ConfigManager.USER_FILE_PATH), HashMap.class); // user template class?
        userTemplate.put("authtoken", authToken);
        mapper.writeValue(Paths.get(ConfigManager.USER_FILE_PATH).toFile(), userTemplate);
    }
}
