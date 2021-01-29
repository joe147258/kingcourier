package com.kingcourier.kingcourier.controller;

import java.security.SecureRandom;
import java.security.Signature;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingcourier.kingcourier.AuthToken;
import com.kingcourier.kingcourier.domain.User;
import com.kingcourier.kingcourier.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthenticationController {

    @Autowired
    UserRepository userRepo;

    @PostMapping("/request-auth-token/")
    public ResponseEntity<Object> authenticateUser(@RequestParam String username, @RequestParam String timeStamp, @RequestParam String signature) {
        User user = userRepo.findByUsername(username);
        if(user == null) return new ResponseEntity<Object>("ERROR: Username not found.", HttpStatus.NOT_FOUND);
        try {
            Signature signatureObject = Signature.getInstance("SHA1withRSA");
            signatureObject.initVerify(user.getPublicKeyObject());
            signatureObject.update(timeStamp.getBytes());
            byte[] decodedSignature = Base64.getDecoder().decode(signature);
            if(!signatureObject.verify(decodedSignature)) {
                return new ResponseEntity<Object>("ERROR: Signature not verified.", HttpStatus.FORBIDDEN);
            }

            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, user.getPublicKeyObject());
            //generate aes stuff
            byte[] iv = new byte[128/8]; 
            SecureRandom sr = new SecureRandom();
            sr.nextBytes(iv);
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecretKey secretKey = kgen.generateKey();
            byte[] encryptedAESKey = cipher.doFinal(secretKey.getEncoded());
            byte[] encryptedIV = cipher.doFinal(ivspec.getIV());
            var authTokenMap = AuthToken.generateAuthTokenAsString(); 
            String unencryptedAuthToken = new ObjectMapper().writeValueAsString(authTokenMap);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            Date date = formatter.parse((String)authTokenMap.get("expirary"));
            user.setAuthTokenExpires(date);
            user.setAuthToken(Base64.getEncoder().encodeToString(unencryptedAuthToken.getBytes()));
            byte[] encryptedAuthToken = AuthToken.encryptAES(secretKey, ivspec, unencryptedAuthToken);
            var response = new HashMap<String, byte[]>();
            response.put("aesKey", encryptedAESKey);
            response.put("ivspec", encryptedIV);
            response.put("token", encryptedAuthToken);

            return new ResponseEntity<Object>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Object>("An Error Occured", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/check-token-valid")
    public ResponseEntity<Object> checkTokenValid(String base64EncryptedUsername, String Base64EncryptedAuthToken, @RequestParam String signature) {
        //TODO next
        return new ResponseEntity<Object>("An Error Occured", HttpStatus.INTERNAL_SERVER_ERROR);
    } 
}
