package com.kingcourier.kingcourier.domain;

import com.kingcourier.kingcourier.Utility;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
}
