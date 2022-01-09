package com.example.cnpmbe.common;


import com.example.cnpmbe.common.enums.ExceptionMessages;
import com.example.cnpmbe.common.enums.ResultMessages;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
@Setter
@NoArgsConstructor
public class APIResponseBuilder {
    private static Properties properties = new Properties();

    private static void loadProperties() {
        try {
            String fileName = "message/messages.properties";
            ClassLoader classLoader = APIResponseBuilder.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(fileName);
            System.out.println(inputStream);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static APIResponse buildResponse(ResultMessages resultMessages, Object result) {
        loadProperties();
        String message = properties.getProperty(resultMessages.getMessageCode());
        return new APIResponse(resultMessages.getStatus(), resultMessages.getMessageCode(), message, result);
    }

    public static APIResponse buildResponse(ResultMessages resultMessages) {
        loadProperties();
        String message = properties.getProperty(resultMessages.getMessageCode());
        return new APIResponse(resultMessages.getStatus(), resultMessages.getMessageCode(), message);
    }

    public static APIResponse buildExceptionResponse(ExceptionMessages exceptionMessages, Object result) {
        loadProperties();
        String message = properties.getProperty(exceptionMessages.getMessageCode());
        return new APIResponse(exceptionMessages.getStatus(), exceptionMessages.getMessageCode(), message, result);
    }

    public static APIResponse buildExceptionResponse(ExceptionMessages exceptionMessages) {
        loadProperties();
        String message = properties.getProperty(exceptionMessages.getMessageCode());
        return new APIResponse(exceptionMessages.getStatus(), exceptionMessages.getMessageCode(), message);
    }
}
