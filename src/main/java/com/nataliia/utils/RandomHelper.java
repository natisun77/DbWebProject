package com.nataliia.utils;

import java.util.Random;

public class RandomHelper {

    public static String getRandomCode(){
        Random random = new Random();
        String randomCode = String.valueOf(random.nextInt(9999-1000+1)+1000);
        return randomCode;
    }
}
