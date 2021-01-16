package com.kingcourier;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class User {

    public User() {

    }

    public User(String username, String publicKey, String privateKey) {
        this.username = username;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    private String username;

    private String publicKey;

    private String privateKey;

    public PrivateKey getPrivateKeyObject() throws Exception {
        byte[] byteKey = Base64.getDecoder().decode(getPrivateKey().getBytes());
        KeySpec privateKeySpec = new PKCS8EncodedKeySpec(byteKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(privateKeySpec);
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
}
