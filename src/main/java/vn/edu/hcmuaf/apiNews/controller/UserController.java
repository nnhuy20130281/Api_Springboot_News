package vn.edu.hcmuaf.apiNews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.apiNews.entity.News;
import vn.edu.hcmuaf.apiNews.entity.User;
import vn.edu.hcmuaf.apiNews.model.dto.UpdateUser;
import vn.edu.hcmuaf.apiNews.model.dto.UserDto;
import vn.edu.hcmuaf.apiNews.service.NewsService;
import vn.edu.hcmuaf.apiNews.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    // get all user
    @GetMapping
    public List<UserDto> getAllUser() {
        return userService.getAllUsers();
    }

    // get all user lock
    @GetMapping("/lock")
    public List<UserDto> getAllUserLock() {
        return userService.getAllUsersLock();
    }

    // get all user admin
    @GetMapping("/admin")
    public List<UserDto> getAllAdmin() {
        return userService.getAllAdmins();
    }

    // get all user admin lock
    @GetMapping("/admin/lock")
    public List<UserDto> getAllAdminLock() {
        return userService.getAllAdminLocks();
    }

    // get user by id
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id) {
        UserDto user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    // create user
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody User user) {
        UserDto createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // update user
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable long id, @RequestBody UpdateUser user) {
        UserDto updatedUser = userService.updateUser(id, user);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
    }

    // delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // lock user
    @PutMapping("/lock/{id}")
    public ResponseEntity<Void> lockUser(@PathVariable long id) {
        userService.lockUser(id);
        return ResponseEntity.noContent().build();
    }
}
