package entities;

import com.google.gson.Gson;
import org.junit.Test;
import services.BodyParser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UserDto {

    @Test
    public void testValidUsername () {
        UserDao user = new UserDao("michelle.obama.test@estudiantat.upc.edu", "12345");
        assertEquals("M. Obama", user.getUsername());
    }

    @Test
    public void testValidUsername2 () {
        UserDao user = new UserDao("peter.kavinsky.test@estudiantat.upc.edu", "12345");
        assertEquals("P. Kavinsky", user.getUsername());
    }

    @Test
    public void testInvalidUsername () {
        UserDao user = new UserDao("peterkavinsky@estudiantat.upc.edu", "12345");
        assertNotEquals("P. Kavinsky", user.getUsername());
    }
}
