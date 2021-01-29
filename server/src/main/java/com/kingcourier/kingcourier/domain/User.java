package com.kingcourier.kingcourier.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;


@Entity
public class User {

    public User() {

    }

    public User(int id, String username, String publicKey) {
        this.id = id;
        this.username = username;
        this.publicKey = publicKey;
    }

    @Id
    private int id = -1;

    private String username;

    @Column(length=2048)
    private String publicKey;
    
    private String AuthToken;

    private Date authTokenExpires;
    
    public PublicKey getPublicKeyObject() throws Exception {
        byte[] byteKey = Base64.getDecoder().decode(getPublicKey().getBytes());
        X509EncodedKeySpec X590publicKey = new X509EncodedKeySpec(byteKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(X590publicKey);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAuthToken() {
        return AuthToken;
    }

    public void setAuthToken(String authToken) {
        AuthToken = authToken;
    }

    public Date getAuthTokenExpires() {
        return authTokenExpires;
    }

    public void setAuthTokenExpires(Date authTokenExpires) {
        this.authTokenExpires = authTokenExpires;
    }
}
