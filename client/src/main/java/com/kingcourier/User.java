package com.kingcourier;

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
