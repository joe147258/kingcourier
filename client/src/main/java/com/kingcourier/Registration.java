package com.kingcourier;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

public class Registration {

    public static User registerUser() {
        while(true) {
            System.out.println("Please enter a new username:");
            String usernameContender = UserInput.input.nextLine();
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
                    if(signUpResult.equals("Success")) {
                        return JsonManager.createUserFile(usernameContender, stringPublicKey, stringPrivateKey);
                    } else {
                        System.out.println("Something went wrong!");
                        return null;
                    }
                } else {
                    System.out.println("That username is in use.");
                }
            } catch(NoSuchAlgorithmException | IOException e) {
                System.out.println("An error has occurred: \n" + e.getMessage());
            }
        }
    }

}
