package com.kingcourier;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class RequestUtilities {

    private final static HttpClient httpClient = HttpClients.createDefault();


    public static String sendGetRequestAndReturnStringResponse(HttpGet get) throws IOException {
        String response;
        return readResponse(httpClient.execute(get));
    }
    public static byte[] sendGetRequestAndReturnRawResponse(HttpGet get) throws IOException {
        byte[] encryptedBytes = httpClient.execute(get).getEntity().getContent().readAllBytes();
        return encryptedBytes;
    }

    public static String sendPostRequestAndReturnResponse
            (HttpPost post, List<NameValuePair> paramList) throws IOException {
        String response;
        try {
            post.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            response = "ERROR: " + e.getMessage();
            System.out.print("An error has occurred: \n" + e.getMessage());
            return response;
        }

        return readResponse(httpClient.execute(post));
    }

    private static String readResponse(HttpResponse execute) {
        String response;
        try(InputStream stream = execute.getEntity().getContent()) {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = stream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            response = result.toString("UTF-8");
        } catch(IOException e) {
            response = "ERROR: " + e.getMessage();
            System.out.print("An error has occurred: \n" + e.getMessage());
        }

        return response;
    }
}
