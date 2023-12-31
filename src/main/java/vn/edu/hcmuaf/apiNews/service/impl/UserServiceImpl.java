package vn.edu.hcmuaf.apiNews.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.apiNews.entity.News;
import vn.edu.hcmuaf.apiNews.entity.User;
import vn.edu.hcmuaf.apiNews.model.dto.NewsDto;
import vn.edu.hcmuaf.apiNews.model.dto.UpdateUser;
import vn.edu.hcmuaf.apiNews.model.dto.UserDto;
import vn.edu.hcmuaf.apiNews.model.mapper.NewsMapper;
import vn.edu.hcmuaf.apiNews.model.mapper.UserMapper;
import vn.edu.hcmuaf.apiNews.repository.NewsRepository;
import vn.edu.hcmuaf.apiNews.repository.UserRepository;
import vn.edu.hcmuaf.apiNews.service.EmailService;
import vn.edu.hcmuaf.apiNews.service.UserService;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NewsRepository newsRepository;


    @Override
    public List<UserDto> getAllUsers() {
        return UserMapper.toUserDto(userRepository.findAll());
    }

    @Override
    public List<UserDto> getAllUsersActive() {
        return UserMapper.toUserDto(userRepository.getAllUserByStatusTrueAndIsAdminFalse());
    }

    @Override
    public List<UserDto> getAllUsersLock() {

        return UserMapper.toUserDto(userRepository.getAllByStatusFalseAndIsAdminFalse());
    }

    @Override
    public List<UserDto> getAllAdmins() {
        return UserMapper.toUserDto(userRepository.getAllUserByStatusTrueAndIsAdminTrue());
    }

    @Override
    public List<UserDto> getAllAdminLocks() {
        return UserMapper.toUserDto(userRepository.getAllByStatusFalseAndIsAdminTrue());
    }

    @Override
    public UserDto getUserById(long id) {
        return UserMapper.toUserDto(userRepository.findById(id).orElse(null));
    }

    @Override
    public String createUser(UpdateUser updateUser) {
        User user = new User();
        if (userRepository.existsByEmail(user.getEmail())) {
            return null;
        }
        user.setFullName(updateUser.getFullName());
        user.setEmail(updateUser.getEmail());
        user.setAdmin(updateUser.isAdmin());
        user.setStatus(true);
        return "Create success";
    }

    @Override
    public String updateUser(long id, UpdateUser updateUser) {
        User user = userRepository.findById(id).get();
        if (userRepository.existsByEmail(updateUser.getEmail()) && !user.getEmail().equals(updateUser.getEmail())) {
            return null;
        }

        user.setFullName(updateUser.getFullName());
        user.setEmail(updateUser.getEmail());
        user.setAdmin(updateUser.isAdmin());
        user.setStatus(updateUser.isStatus());
        userRepository.save(user);
        return "Update success";
    }

    @Override
    public String updateUserProfile(long id, UpdateUser updateUser) {
        User user = userRepository.findById(id).get();
        if (userRepository.existsByEmail(updateUser.getEmail()) && !user.getEmail().equals(updateUser.getEmail())) {
            return null;
        }
        user.setFullName(updateUser.getFullName());
        userRepository.save(user);
        return "Update success";
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void lockUser(long id) {
        Optional<User> user = userRepository.findById(id);
        user.get().setStatus(!user.get().isStatus());
        userRepository.save(user.get());
    }

    @Override
    public Set<NewsDto> getBookmark(long id) {
        return NewsMapper.toNewsDto(userRepository.findById(id).get().getListBookmark());
    }

    @Override
    public String addBookmark(long idUser, long idNews) {
        Optional<User> user = userRepository.findById(idUser);
        Optional<News> news = newsRepository.findById(idNews);
        if (user.get().getListBookmark().contains(news.get())){
            return "News already bookmark";
        }
        user.get().getListBookmark().add(news.get());
        userRepository.save(user.get());
        return "Add bookmark success";
    }

    @Override
    public String deleteBookmark(long idUser, long idNews) {
        Optional<User> user = userRepository.findById(idUser);
        Optional<News> news = newsRepository.findById(idNews);
        if (!user.get().getListBookmark().contains(news.get())){
            return "News not bookmark";
        }
        user.get().getListBookmark().remove(news.get());
        userRepository.save(user.get());
        return "Delete bookmark success";
    }

    @Override
    public void deleteAllBookmark(long idUser) {
        Optional<User> user = userRepository.findById(idUser);
        user.get().getListBookmark().clear();
        userRepository.save(user.get());
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(getRoles(user))));
    }

    private String getRoles(User user) {
        return user.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER";
    }

}
