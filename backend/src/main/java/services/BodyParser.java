package services;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BodyParser {

    private static final Gson gson = new Gson();

    public static <T> T parse(String json, Class<T> pojoClass) {
        return gson.fromJson(json, pojoClass);
    }

    public static <T> T parseRequestBody(HttpExchange exchange, Class<T> type) throws IOException {
        InputStream requestBody = exchange.getRequestBody();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody, "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        try {
            return parse(sb.toString(), type);
        } catch (JsonParseException e) {
            exchange.sendResponseHeaders(400, -1); // Bad Request
            throw new IOException("Request body format error", e);
        } catch (Exception e) {
            exchange.sendResponseHeaders(500, -1); // Internal Server Error
            throw new IOException("Server error processing request", e);
        }
    }
    
}
