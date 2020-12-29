package com.kingcourier;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URI;
import java.util.Scanner;


public final class UserInput {

    public static final Scanner input = new Scanner(System.in);

    private static final String PREFIX = ":";
    private static final String REGISTER = ":re";
    private static final String LOGIN = ":lo";

    public static void checkUserInput(String input) {
        if(!input.startsWith(PREFIX)) {
            return;
        }

        switch(input) {
            case REGISTER:
                registerUser();
                break;
            case LOGIN:
                // ..
                break;
            default:
                System.out.println("Command not found.");
        }
    }

    private static Boolean registerUser() {
        System.out.println("Please enter a username:");
        // create a client
/*        var client = HttpClient.newHttpClient();

        // create a request
        var request = HttpRequest.newBuilder(
                URI.create("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY"))
                .header("accept", "application/json")
                .build();

        // use the client to send the request
        var response = client.send(request, new JsonBodyHandler<>(APOD.class));

        // the response:
        System.out.println(response.body().get().title);*/

        return true;
    }
}
