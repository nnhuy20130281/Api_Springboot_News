package vn.edu.hcmuaf.apiNews.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.apiNews.entity.News;
import vn.edu.hcmuaf.apiNews.entity.User;
import vn.edu.hcmuaf.apiNews.model.dto.UpdateUser;
import vn.edu.hcmuaf.apiNews.model.dto.UserDto;
import vn.edu.hcmuaf.apiNews.model.mapper.UserMapper;
import vn.edu.hcmuaf.apiNews.repository.UserRepository;
import vn.edu.hcmuaf.apiNews.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
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
    public UserDto createUser(User user) {
        return UserMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(long id, UpdateUser updateUser) {
        User user = userRepository.findById(id).get();
        user.setFullName(updateUser.getFullName());
        user.setEmail(updateUser.getEmail());
        return UserMapper.toUserDto(userRepository.save(user));
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
