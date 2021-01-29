package com.kingcourier;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class User {

    public User() {

    }

    public User(String username, String publicKey, String privateKey) {
        this.username = username;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.authToken = "EMPTY";
    }

    public User(String username, String publicKey, String privateKey, String authToken) {
        this.username = username;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.authToken = authToken;
    }

    private String username;

    private String publicKey;

    private String privateKey;

    private String authToken;

    public PublicKey getPublicKeyObject() throws Exception {
        byte[] byteKey = Base64.getDecoder().decode(getPublicKey().getBytes());
        X509EncodedKeySpec X590publicKey = new X509EncodedKeySpec(byteKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(X590publicKey);
    }

    public PrivateKey getPrivateKeyObject() throws Exception {
        byte[] byteKey = Base64.getDecoder().decode(getPrivateKey().getBytes());
        KeySpec privateKeySpec = new PKCS8EncodedKeySpec(byteKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(privateKeySpec);
    }

    public String checkTokenValid() {
        //TODO Next
        return "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
