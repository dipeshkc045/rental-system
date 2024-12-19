package com.bookrental.utils;

import java.security.SecureRandom;
import java.util.Random;

public class Utils {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-";
    private static final int CODE_LENGTH = 10;

    public static String generateTransactionCode() {
        Random random = new SecureRandom();
        StringBuilder codeBuilder = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            codeBuilder.append(CHARACTERS.charAt(index));
        }
        return codeBuilder.toString();
    }
}
