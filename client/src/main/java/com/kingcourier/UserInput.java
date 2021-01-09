package com.kingcourier;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;



public final class UserInput {

    public static final Scanner input = new Scanner(System.in);

    private static final String PREFIX = ":";

    public static void checkUserInput(String input) {
        if(!input.startsWith(PREFIX)) {
            System.out.println("Command not found.");
            return;
        }

        switch(input) {
            default:
                System.out.println("Command not found.");
        }
    }
}
