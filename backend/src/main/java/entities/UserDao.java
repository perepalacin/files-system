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
        this.username = email;
    }

}
