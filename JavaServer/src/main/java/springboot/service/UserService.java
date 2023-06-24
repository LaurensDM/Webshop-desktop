package springboot.service;

import org.json.simple.JSONObject;
import springboot.domein.User;
import java.util.List;

public interface UserService {
    User saveUser(User user);

    String login(JSONObject user) throws IllegalAccessException;

    String register(JSONObject user);

    User createUserAndAddToCompany(JSONObject jsonUser, String token) throws IllegalAccessException;

    List<User> getAllEmployees(String token);

    User getUserById(String id);

    User updateUser(String id, JSONObject user, String token) throws IllegalAccessException;

    void deleteUser(String id);

    User getUserByToken(String token);

    User getUserByEmail(String email);
}
