package com.kingcourier.kingcourier;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthToken {

    private final static String AUTH_CODE_KEY = "authCode";
    private final static String DATE_KEY = "date";
    private final static String EXPIRARY_KEY = "expirary";

    public static HashMap<String, Object> generateAuthTokenAsString() throws JsonProcessingException {
        var authToken = new HashMap<String, Object>();
        authToken.put(AUTH_CODE_KEY, UUID.randomUUID().toString()); // need to be encrypted w/ aes?
        authToken.put(DATE_KEY, generateDateAsString(0));
        authToken.put(EXPIRARY_KEY, generateDateAsString(6));
        return authToken;
    }

	public static byte[] encryptAES(SecretKey key, IvParameterSpec ivspec, String unencrypted) throws Exception {
    	Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    	cipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
	    return cipher.doFinal(unencrypted.getBytes("UTF-8"));
    }
    
    private static String generateDateAsString(int futureTime) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, futureTime);
        date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    

}
