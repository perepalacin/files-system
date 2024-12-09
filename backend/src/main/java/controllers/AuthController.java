package controllers;

import com.sun.net.httpserver.HttpServer;

import entities.UserDao;
import services.BodyParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class AuthController {

    public AuthController (HttpServer server) {
        server.createContext("/api/v1/auth/sign-in", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                //send them the post web page
            } else if ("POST".equals(exchange.getRequestMethod())) {
                //process the sign in data
            } else {
                exchange.sendResponseHeaders(403, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));

        server.createContext("/api/v1/auth/sign-up", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                //Send the page
            } else if ("POST".equals(exchange.getRequestMethod())) {
                try {
                    UserDao user = BodyParser.parseRequestBody(exchange, UserDao.class);
                    String response = "User registered successfully" + user.getEmail() + user.getPassword();
                    exchange.sendResponseHeaders(200, response.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    exchange.sendResponseHeaders(500, -1); // Internal Server Error
                }
            } else {
                exchange.sendResponseHeaders(403, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));

        server.createContext("/api/v1/auth/log-out", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                //Send the logut page
            } else {
                exchange.sendResponseHeaders(403, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));
    }
}
