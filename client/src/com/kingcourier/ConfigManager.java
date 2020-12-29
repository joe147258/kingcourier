package com.kingcourier;
import java.io.*;
import java.util.Scanner;

public final class ConfigManager {

    private static final String DIRECTORY_PATH = "C:\\kingcourier";
    private static final String USER_FILE_PATH = DIRECTORY_PATH + "\\userfile.json";
    private static final String APP_SETTINGS_PATH = DIRECTORY_PATH + "\\appsettings.json";

    public static void initConfig() {
        if(directoryExists()) {
            //TODO: Load user file into memory
        } else {
            File newDir = new File(DIRECTORY_PATH);
            newDir.mkdirs();
        }
        if(!new File(USER_FILE_PATH).exists()) {
            System.out.println("Userfile cannot be found. Type :lo to login or :re to register.");
            UserInput.checkUserInput(UserInput.input.nextLine());
        } else {
            //check if it is compatable
        }
        if(!new File(APP_SETTINGS_PATH).exists()) {
            //create new appsettings
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
