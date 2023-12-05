package vn.edu.hcmuaf.apiNews.service;
import vn.edu.hcmuaf.apiNews.entity.News;
import vn.edu.hcmuaf.apiNews.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(long id);

    User createUser(User user);

    User updateUser(long id, User user);

    void deleteUser(long id);

}
