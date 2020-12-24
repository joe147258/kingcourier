package com.kingcourier;
import java.io.*;

public final class ConfigManager {

    private static final String DIRECTORY_PATH = "C:\\kingcourier";
    private static final String USER_FILE_PATH = DIRECTORY_PATH + "\\userconfig.json";
    private static final String APP_SETTINGS_PATH = DIRECTORY_PATH + "\\appsettings.json";

    public static void initConfig() {
            if(directoryExists()) {
                // Load config / user file into memory..?
                return;
            } else {
                //check for user files and appsettings

            }

    }

    private static Boolean directoryExists()
    {
        File f = new File(DIRECTORY_PATH);
        if (f.exists() && f.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }
}
