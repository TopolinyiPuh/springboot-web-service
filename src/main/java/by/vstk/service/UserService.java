package by.vstk.service;

import by.vstk.model.User;

import java.util.List;

public interface UserService {
    User findUserById(Long id);

    List<User> allUsers();

    boolean saveUser(User user);

    boolean deleteUser(Long id);

    List<User> usergtList(Long idMin);

    void updatePassword(User user);
}
