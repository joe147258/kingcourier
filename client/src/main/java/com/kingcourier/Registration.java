package com.kingcourier;

import com.kingcourier.configuration.JsonManager;
import com.kingcourier.utilities.RequestUtilities;

import org.apache.http.HttpResponse;
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
                int responseStatus = RequestUtilities.sendGetRequestAndReturnHttpResponse(get).getStatusLine().getStatusCode();
                if(responseStatus != RequestUtilities.RESPONSE_OK)  System.out.println("That username is in use.");

                System.out.println("Username available! Generating key pair...");
                // Generates a key pair and gets them as a string Base64 encoded. Both are saved to userfile.json.
                // Public key is also sent to server for authentication purposes (no password).
                KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
                kpg.initialize(2048);
                KeyPair keyPair = kpg.generateKeyPair();
                byte[] bytePublicKey = keyPair.getPublic().getEncoded();
                byte[] bytePrivateKey = keyPair.getPrivate().getEncoded();
                String stringPublicKey = Base64.getEncoder().encodeToString(bytePublicKey);
                String stringPrivateKey = Base64.getEncoder().encodeToString(bytePrivateKey);

                // Sign up account
                HttpPost post = new HttpPost("http://localhost:8080/register/");
                var params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", usernameContender));
                params.add(new BasicNameValuePair("publicKey", stringPublicKey));
                
                HttpResponse signUpResult = RequestUtilities.sendPostRequestAndReturnHttpResponse(post, params);

                if(signUpResult.getStatusLine().getStatusCode() == RequestUtilities.RESPONSE_CREATED) {
                    return JsonManager.createUserFile(usernameContender, stringPublicKey, stringPrivateKey);
                } else {
                    System.out.println("Something went wrong during registratation!");
                    return null;
                }
            } catch(NoSuchAlgorithmException | IOException e) {
                System.out.println("An error has occurred: \n" + e.getMessage());
            }
        }
    }

}
