package com.restdeveloper.app.ws.shared;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class Utils {

    private final Random RANDOM = new SecureRandom();

    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwqyz";

    private final String NUMBERS = "0123456789";

    public String generateUserId(int length){
        return generateRandomString(length);
    }
    //TODO Make this Generate a number
    public String generatePollId(int length){
       return generateRandomNumberSting(length);
    }

    private String generateRandomNumberSting(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(NUMBERS.charAt(RANDOM.nextInt(NUMBERS.length())));
        }
        return new String(returnValue);
    }

    private String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }
}
