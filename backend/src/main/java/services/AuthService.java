package services;

import entities.UserDao;
import repositories.UsersRepository;

public class AuthService {

    private final UsersRepository usersRepository = new UsersRepository();
    //Todo connect to the db
    public boolean singUpUser(UserDao user) {
        //First check if the username already exists
        //if it does throw an error
        //if it doesnt, save it to the db and return the details
        usersRepository.insertUser(user.getEmail(), user.getPassword());
        return true;
    }
}
