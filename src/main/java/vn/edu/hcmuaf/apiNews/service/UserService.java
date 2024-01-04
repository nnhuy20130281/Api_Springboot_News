package vn.edu.hcmuaf.apiNews.service;
import org.springframework.security.core.userdetails.UserDetailsService;
import vn.edu.hcmuaf.apiNews.entity.News;
import vn.edu.hcmuaf.apiNews.entity.User;
import vn.edu.hcmuaf.apiNews.model.dto.UpdateUser;
import vn.edu.hcmuaf.apiNews.model.dto.UserDto;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    List<UserDto> getAllUsers();

    // get all user status = true
    List<UserDto> getAllUsersActive();

    // get all user status = false
    List<UserDto> getAllUsersLock();

    List<UserDto> getAllAdmins();

    List<UserDto> getAllAdminLocks();

    UserDto getUserById(long id);

    UserDto createUser(User user);

    UserDto updateUser(long id, UpdateUser updateUser);

    void deleteUser(long id);

    void lockUser(long id);

    Set<News> getBookmark(long id);

    void addBookmark(long idUser, long idNews);



}
