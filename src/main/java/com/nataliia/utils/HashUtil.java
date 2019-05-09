package com.nataliia.utils;

import org.apache.log4j.Logger;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


public class HashUtil {
    private static final Logger logger = Logger.getLogger(HashUtil.class);

    public static String getSHA512SecurePassword(String passwordToHash, String salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e){
            logger.error("Can't find algorithm", e);
        }
        return generatedPassword;
    }

    public static String getRandomSalt(){
        byte[] array = new byte[6];
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

    public static void main(String[] args) {
        System.out.println(getSHA512SecurePassword("admin", "tyuiop"));
    }


}


