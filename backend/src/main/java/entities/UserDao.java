package entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDao {
    private int id;
    private String username;
    private String email;
    private String password;


    public UserDao(String email, String password) {
        this.email = email;
        this.password = password;
        this.username = generateUsernameFromEmail(email);
    }

    private String generateUsernameFromEmail (String email) {
        String[] parts = email.split("\\.");
        if (parts.length > 1) {
            return parts[0].substring(0, 1).toUpperCase() + ". " + parts[1].substring(0, 1).toUpperCase() + parts[1].substring(1);
        }
        return "Anonymous";
    }

}
