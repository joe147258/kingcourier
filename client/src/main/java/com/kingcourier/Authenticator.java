package com.kingcourier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingcourier.configuration.JsonManager;
import com.kingcourier.utilities.DateUtilities;
import com.kingcourier.utilities.RequestUtilities;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Authenticator {

    public static Boolean getAuthToken() {
        try {
            HttpPost post = new HttpPost("http://localhost:8080/request-auth-token/"); // todo send a signature
            var params = new ArrayList<NameValuePair>();
            String timeStamp = DateUtilities.generateDateAsString(0);
            Signature sig = Signature.getInstance("SHA1withRSA");
            sig.initSign(Main.currentUser.getPrivateKeyObject());
            sig.update(timeStamp.getBytes());
            byte[] signatureAsByte = sig.sign();
            params.add(new BasicNameValuePair("username", Main.currentUser.getUsername()));
            params.add(new BasicNameValuePair("timeStamp", timeStamp));
            params.add(new BasicNameValuePair("signature", Base64.getEncoder().encodeToString(signatureAsByte)));
            HttpResponse response =  RequestUtilities.sendPostRequestAndReturnHttpResponse(post, params);
            String responseString = RequestUtilities.getResponseString(response);
            // Throw exception if response isn't 200
            if (response.getStatusLine().getStatusCode() != RequestUtilities.RESPONSE_OK) {
                throw new Exception(responseString);
            } 

            Map<String, String> map = new ObjectMapper().readValue(responseString, Map.class); //TODO: perhaps a class instead?
            byte[] encryptedAESKey= Base64.getDecoder().decode(map.get("aesKey"));
            byte[] encryptedIvspec= Base64.getDecoder().decode(map.get("ivspec"));
            
            Cipher decipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            decipher.init(Cipher.DECRYPT_MODE, Main.currentUser.getPrivateKeyObject());
            byte[] decryptedAesKeyBytes = decipher.doFinal(encryptedAESKey);
            byte[] decryptedIvspecBytes = decipher.doFinal(encryptedIvspec);

            SecretKey AESKey = new SecretKeySpec(decryptedAesKeyBytes, 0, decryptedAesKeyBytes.length, "AES");
            IvParameterSpec ivspec = new IvParameterSpec(decryptedIvspecBytes);

            byte[] decryptedToken = decryptAES(AESKey, ivspec, Base64.getDecoder().decode(map.get("token")));
            String base64TokenString = Base64.getEncoder().encodeToString(decryptedToken);
            Main.currentUser.setAuthToken(base64TokenString);
            JsonManager.addTokenToUserFile(base64TokenString);
            System.out.println("Auth token recieved.");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static byte[] decryptAES(SecretKey key, IvParameterSpec ivspec, byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, ivspec);
        return cipher.doFinal(encrypted);
    }
}
