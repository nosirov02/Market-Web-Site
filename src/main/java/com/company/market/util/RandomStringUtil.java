package com.company.market.util;

import java.util.Random;

public class RandomStringUtil {
    static String data_for_random_string = "abcdefghijkmnopqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ123456789";

    public static String generateRandomString(int len) {
        if (len <= 0) {
            throw new IllegalArgumentException();
        }
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < len; i++) {
            int n = random.nextInt(data_for_random_string.length());
            stringBuilder.append(data_for_random_string.charAt(n));
        }
        return stringBuilder.toString();
    }

    public static String generateRandomNumber(int len) {
        if (len <= 0) {
            throw new IllegalArgumentException();
        }
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int n = random.nextInt(10);
            stringBuilder.append(n);
        }
        return stringBuilder.toString();
    }
}
