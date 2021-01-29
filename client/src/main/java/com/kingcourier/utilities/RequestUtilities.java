package com.kingcourier.utilities;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class RequestUtilities {

    public final static int RESPONSE_OK = 200;
    public final static int RESPONSE_CREATED = 201; 

    private final static HttpClient httpClient = HttpClients.createDefault();

    public static HttpResponse sendGetRequestAndReturnHttpResponse(HttpGet get) throws IOException {
        return httpClient.execute(get);
    }

    public static HttpResponse sendPostRequestAndReturnHttpResponse(HttpPost post, List<NameValuePair> paramList) throws IOException {
        try {
            post.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.out.print("An error has occurred: \n" + e.getMessage());
        }

        return httpClient.execute(post);
    }



    public static String getResponseString(HttpResponse response) {
        String responseString;
        try(InputStream stream = response.getEntity().getContent()) {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = stream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            responseString = result.toString("UTF-8");
        } catch(IOException e) {
            responseString = "ERROR: " + e.getMessage();
            System.out.print("An error has occurred: \n" + e.getMessage());
        }

        return responseString;
    }

    public static byte[] getResponseBytes(HttpResponse response) throws IOException {
        return response.getEntity().getContent().readAllBytes();
    }
}
