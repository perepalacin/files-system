package controllers;

import com.sun.net.httpserver.HttpServer;

import entities.UserDao;
import entities.dto.UserDto;
import services.AuthService;
import services.BodyParser;

import java.io.OutputStream;

public class AuthController {

    private final AuthService authService = new AuthService();

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
                UserDto user = BodyParser.parseRequestBody(exchange, UserDto.class);
                UserDao newUser = new UserDao(user.getEmail(), user.getPassword());
                authService.singUpUser(newUser);
                String response = "User registered successfully" + user.getEmail() + user.getPassword() + user.getUsername();
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));

        server.createContext("/api/v1/auth/log-out", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                //Send the logut page
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));
    }
}
