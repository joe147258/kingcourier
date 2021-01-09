package com.kingcourier;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;



public final class UserInput {

    public static final Scanner input = new Scanner(System.in);

    private static final String PREFIX = ":";
    private static final String REGISTER = ":re";

    public static void checkUserInput(String input) {
        if(!input.startsWith(PREFIX)) {
            System.out.println("Command not found.");
            return;
        }

        switch(input) {
            case REGISTER:
                registerUser();
                break;
            default:
                System.out.println("Command not found.");
        }
    }

    private static Boolean registerUser() {
        System.out.println("Please enter a username:");
        String usernameContender = input.nextLine();
        try {
            //TODO: Get this URL from a app settings json file.
            HttpGet get = new HttpGet("http://localhost:8080/register/check-username/" + usernameContender);
            String usernameResult = RequestUtilities.sendGetRequestAndReturnResponse(get);
            if(usernameResult.equals("true")) {
                System.out.println("Username available! Generating key pair...");
                // Generates a key pair and gets them as a string Base64 encoded. Both are saved to userfile.json.
                // Public key is also sent to server for authentication purposes (no password).
                KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
                kpg.initialize(2048);
                KeyPair keyPair = kpg.generateKeyPair();
                byte[] bytePublicKey = keyPair.getPublic().getEncoded();
                byte[] bytePrivateKey = keyPair.getPrivate().getEncoded();
                String stringPublicKey = Base64.getEncoder().encodeToString(bytePublicKey);
                //TODO: Save to JSON file.
                String stringPrivateKey = Base64.getEncoder().encodeToString(bytePrivateKey);

                // Sign up account
                HttpPost post = new HttpPost("http://localhost:8080/register/");
                var params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", usernameContender));
                params.add(new BasicNameValuePair("publicKey", stringPublicKey));
                String signUpResult = RequestUtilities.sendPostRequestAndReturnResponse(post, params);
                System.out.println(signUpResult);
            } else {
                //TODO: try another username - could loop back with a while loop?
            }
        } catch(NoSuchAlgorithmException | IOException e) {
            System.out.print("An error has occurred when creating key pair: \n" + e.getMessage());
        }

        return true;
    }
}
