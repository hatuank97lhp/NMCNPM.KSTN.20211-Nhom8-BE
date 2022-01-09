package com.example.cnpmbe.common;

import java.util.UUID;

public class StringUtils {

    public static String generateRandomToken() {
        return UUID.randomUUID().toString();
    }

}

