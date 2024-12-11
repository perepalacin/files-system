package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.sun.net.httpserver.HttpExchange;

import annotations.Required;
import exceptions.MissingFieldsException;

public class BodyParser {

    private static final Gson gson = new Gson();

    public static <T> T parse(String json, Class<T> pojoClass) {
        return gson.fromJson(json, pojoClass);
    }

    public static <T> T parseRequestBody(HttpExchange exchange, Class<T> type) throws IOException, JsonParseException {
        InputStream requestBody = exchange.getRequestBody();
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody, "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        T obj = parse(sb.toString(), type);
        validateRequiredFields(obj, type);
        return obj;
    }

    public static <T> void validateRequiredFields(T obj, Class<T> type) throws MissingFieldsException {
        for (Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(Required.class)) {
                field.setAccessible(true);
                try {
                    if (field.get(obj) == null) {
                        throw new MissingFieldsException("Missing required field: " + field.getName());
                    }
                } catch (IllegalAccessException e) {
                    throw new MissingFieldsException("Missing required field: " + field.getName());
                }
            }
        }
    }
    
}
