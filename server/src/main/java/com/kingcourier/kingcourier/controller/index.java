package com.kingcourier.kingcourier.controller;

import com.kingcourier.kingcourier.domain.User;
import com.kingcourier.kingcourier.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@RestController
public class index {

    @Autowired
    UserRepository userRepo;

    @GetMapping("/")
    public String helloWorld() {

        return "Hello World";
    }

    @GetMapping("/auth-token/{username}")
    public String authenticateUser(@PathVariable String username) {
        User user = userRepo.findByUsername(username);
        try {
            byte[] byteKey = Base64.getDecoder().decode(user.getPublicKey().getBytes(StandardCharsets.UTF_8));
            X509EncodedKeySpec X590publicKey = new X509EncodedKeySpec(byteKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey pubKey = kf.generatePublic(X590publicKey);
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return new String(cipher.doFinal(username.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            //TODO: Logging
            e.printStackTrace();
        }
        //TODO: common library codes here.
        return "User doesn't exist";
    }
}
