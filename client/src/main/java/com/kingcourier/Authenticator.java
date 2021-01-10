package com.kingcourier;

import org.apache.http.client.methods.HttpGet;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Authenticator {

    public static Boolean authenticateUser() {
        try {
            HttpGet get = new HttpGet("http://localhost:8080/auth-token/" + Main.currentUser.getUsername());
            String encryptedUsername = RequestUtilities.sendGetRequestAndReturnResponse(get);
            try {
                byte[] byteKey =
                        Base64.getDecoder().decode(Main.currentUser.getPrivateKey().getBytes(StandardCharsets.UTF_8));
                KeySpec privKeySpec = new PKCS8EncodedKeySpec(byteKey);
                KeyFactory kf = KeyFactory.getInstance("RSA");
                PrivateKey privKey = kf.generatePrivate(privKeySpec);
                
                Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
                cipher.init(Cipher.DECRYPT_MODE, privKey);
                String temp = new String(cipher.doFinal(encryptedUsername.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                System.out.println(temp);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
