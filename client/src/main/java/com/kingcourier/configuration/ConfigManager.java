package com.kingcourier.configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingcourier.Registration;
import com.kingcourier.User;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public final class ConfigManager {

    public static final String DIRECTORY_PATH = System.getProperty("user.dir");
    public static final String USER_FILE_PATH = DIRECTORY_PATH + "\\userfile.json";
    public static final String APP_SETTINGS_PATH = DIRECTORY_PATH + "\\appsettings.json";

    public static User initConfig() {
        if(!directoryExists()) {
            File newDir = new File(DIRECTORY_PATH);
            newDir.mkdirs();
        }

        if(!new File(APP_SETTINGS_PATH).exists()) {
            //create new appsettings
        }

        if(!new File(USER_FILE_PATH).exists()) {
            System.out.println("userfile.json cannot be found. You must now register an account." +
                    "A new userfile.json will be generated. If you lose this file, you will have to register again." +
                    "If you have a userfile.json please add it to: " + DIRECTORY_PATH + " and relaunch." +
                    " This file should be kept private.");
            return Registration.registerUser();
        } else {
            ObjectMapper mapper = new ObjectMapper();
            User user = null;
            try {
                HashMap<String, String> userTemplate = mapper.readValue(new File(USER_FILE_PATH), HashMap.class);
                user = new User(userTemplate.get("username"), userTemplate.get("publickey"), userTemplate.get("privatekey"), userTemplate.get("authtoken"));
            } catch (IOException e) {
                System.out.println("Error reading userfile:\n" + e.getMessage());
            }
            return user;
        }
    }

    private static Boolean directoryExists() {
        File f = new File(DIRECTORY_PATH);
        if (f.exists() && f.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }
}
