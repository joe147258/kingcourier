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
}
