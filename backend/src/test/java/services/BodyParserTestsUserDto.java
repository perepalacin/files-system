package services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import exceptions.MissingFieldsException;
import org.junit.Test;
import entities.dto.UserDto;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BodyParserTestsUserDto {

    @Test
    public void testParseUserDto() {
        String json = "{\"email\":\"test@example.com\", \"password\":\"12345\"}";
        UserDto result = BodyParser.parse(json, UserDto.class);
        Gson gson = new Gson();
        System.out.println("Parsed Object: " + gson.toJson(result));
        assertEquals("test@example.com", result.getEmail());
        assertEquals("12345", result.getPassword());
    }

    @Test (expected = MissingFieldsException.class)
    public void testValidateRequiredEmailUserDto() {
        BodyParser.validateRequiredFields(new UserDto(null, "12345"), UserDto.class);
    }

    @Test (expected = MissingFieldsException.class)
    public void testValidateRequiredPasswordUserDto() {
        BodyParser.validateRequiredFields(new UserDto("test@email.com", null), UserDto.class);
    }

    @Test
    public void testValidateRequiredFieldsUserDto() {
        BodyParser.validateRequiredFields(new UserDto("test@email.com", "1234"), UserDto.class);
    }

    @Test
    public void testValidParseRequestBodyUserDto() throws IOException {
        HttpExchange exchange = mock(HttpExchange.class);
        String jsonInput = "{\"email\":\"test@example.com\",\"password\":\"123456\"}";
        ByteArrayInputStream bis = new ByteArrayInputStream(jsonInput.getBytes());

        when(exchange.getRequestBody()).thenReturn(bis);

        try {
            UserDto user = BodyParser.parseRequestBody(exchange, UserDto.class);
            assert user.getEmail().equals("test@example.com");
            assert user.getPassword().equals("123456");
        } catch (IOException e){
            throw new IOException(e.getMessage());
        }
        verify(exchange).getRequestBody();
    }

    @Test (expected = IOException.class)
    public void testInvalidParseRequestBodyUserDto() throws IOException {
        HttpExchange exchange = mock(HttpExchange.class);
        String jsonInput = "{\"username\":\"test@example.com\",\"password\":\"123456\"}";
        ByteArrayInputStream bis = new ByteArrayInputStream(jsonInput.getBytes());

        when(exchange.getRequestBody()).thenReturn(bis);

        try {
            UserDto user = BodyParser.parseRequestBody(exchange, UserDto.class);
            assert user.getEmail().equals("test@example.com");
            assert user.getPassword().equals("123456");
        } catch (IOException e){
            throw new IOException(e.getMessage());
        }
        verify(exchange).getRequestBody();
    }
}
