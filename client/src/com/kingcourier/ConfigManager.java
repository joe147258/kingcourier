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
                //create user file and app settings.
                File newDir = new File(DIRECTORY_PATH);
                newDir.mkdirs();
                if(!new File(APP_SETTINGS_PATH).exists()) {
                    System.out.println(String.format("App settings cannot be found. Import one by moving an " +
                            "appsettings.json into {0}. Type :r to reload after an import" +
                            "or :n to generate a new one.", DIRECTORY_PATH));
                }
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
