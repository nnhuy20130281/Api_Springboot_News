package vn.edu.hcmuaf.apiNews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.apiNews.entity.News;
import vn.edu.hcmuaf.apiNews.entity.User;
import vn.edu.hcmuaf.apiNews.model.dto.NewsDto;
import vn.edu.hcmuaf.apiNews.model.dto.RegisterDto;
import vn.edu.hcmuaf.apiNews.model.dto.UpdateUser;
import vn.edu.hcmuaf.apiNews.model.dto.UserDto;
import vn.edu.hcmuaf.apiNews.service.AuthService;
import vn.edu.hcmuaf.apiNews.service.NewsService;
import vn.edu.hcmuaf.apiNews.service.UserService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    // get all user
    @GetMapping
    public List<UserDto> getAllUser() {
        return userService.getAllUsers();
    }

    // get all user active
    @GetMapping("/active")
    public List<UserDto> getAllUserActive() {
        return userService.getAllUsersActive();
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
    public ResponseEntity<?> createUser(@RequestBody UpdateUser updateUser) {
        return ResponseEntity.ok(authService.adminRegister(updateUser));
    }

    // update user
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable long id, @RequestBody UpdateUser user) {
        String updatedUser = userService.updateUser(id, user);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
    }

    // update user profile
    @PutMapping("/profile/{id}")
    public ResponseEntity<String> updateUserProfile(@PathVariable long id, @RequestBody UpdateUser user) {
        String updatedUser = userService.updateUserProfile(id, user);
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
    public ResponseEntity<String> lockUser(@PathVariable long id) {
        userService.lockUser(id);
        return ResponseEntity.ok("200");
    }

    // update password
    @PutMapping("/update-password/{id}")
    public ResponseEntity<String> updatePassword(@PathVariable long id) {
        String updatedPassword = authService.updatePassword(id);
        return new ResponseEntity<>(updatedPassword, HttpStatus.OK);
    }

    // get bookmark
    @GetMapping("/bookmark/{id}")
    public ResponseEntity<Set<NewsDto>> getBookmark(@PathVariable long id) {
        Set<NewsDto> news = userService.getBookmark(id);
        return news != null ? ResponseEntity.ok(news) : ResponseEntity.notFound().build();
    }

    // add bookmark
    @PutMapping("/bookmark/{idUser}/{idNews}")
    public ResponseEntity<String> addBookmark(@PathVariable long idUser, @PathVariable long idNews) {
        String addBookmark = userService.addBookmark(idUser, idNews);
        return ResponseEntity.ok(addBookmark);
    }

    // delete bookmark
    @DeleteMapping("/bookmark/{idUser}/{idNews}")
    public ResponseEntity<String> deleteBookmark(@PathVariable long idUser, @PathVariable long idNews) {
        String deleteBookmark = userService.deleteBookmark(idUser, idNews);
        return ResponseEntity.ok(deleteBookmark);
    }

    // delete all bookmark
    @DeleteMapping("/bookmark/{id}")
    public ResponseEntity<String> deleteAllBookmark(@PathVariable long id) {
        userService.deleteAllBookmark(id);
        return ResponseEntity.ok("200");
    }
}
