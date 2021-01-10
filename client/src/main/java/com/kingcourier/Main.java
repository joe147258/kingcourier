package com.kingcourier;

public class Main {

    public static User currentUser;

    public static void main(String[] args) {

        System.out.println("\n" +
                " ,\n" +
                "/,`\\\n" +
                "` | \\____\\\\\n" +
                " _(      ) \\\n" +
                " \\-\\~~~_|\\  \\\n" +
                "    \\ `   \\  `\n" +
                "    `     `" +
                "\n----- Welcome to KingCourier Messenger -----");
        currentUser = ConfigManager.initConfig();
        System.out.println("Sending Authentication request...");
        Authenticator.authenticateUser();
    }
}
