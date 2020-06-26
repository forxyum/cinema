package hu.alkfejl.dao;

import hu.alkfejl.model.User;

import java.util.List;

public interface UserDAO {

    List<User> listAllUsers();
    List<String> listAllUsernames();
    void register(User user);
    User login(String username, String password);
}
