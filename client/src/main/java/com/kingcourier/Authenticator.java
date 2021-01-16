package com.kingcourier;

import org.apache.http.client.methods.HttpGet;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class Authenticator {

    public static Boolean authenticateUser() {
        try {
            HttpGet get = new HttpGet("http://localhost:8080/auth-token/" + Main.currentUser.getUsername());
            byte[] encryptedUsername = RequestUtilities.sendGetRequestAndReturnRawResponse(get);
            Cipher decipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            decipher.init(Cipher.DECRYPT_MODE, Main.currentUser.getPrivateKeyObject());
            String test = new String(decipher.doFinal(encryptedUsername));
            System.out.println(test);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
