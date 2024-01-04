package vn.edu.hcmuaf.apiNews.controller;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.apiNews.entity.User;
import vn.edu.hcmuaf.apiNews.model.dto.ChangePassword;
import vn.edu.hcmuaf.apiNews.model.dto.LoginDto;
import vn.edu.hcmuaf.apiNews.model.dto.RegisterDto;
import vn.edu.hcmuaf.apiNews.model.dto.ResetPassword;
import vn.edu.hcmuaf.apiNews.service.AuthService;
import vn.edu.hcmuaf.apiNews.service.EmailService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Transactional
public class AuthController {

    @Autowired
    private AuthService authService;


    // login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    // register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
        return ResponseEntity.ok(authService.registers(registerDto));
    }

    // validate email
    @PostMapping("/validate-email")
    public ResponseEntity<?> validateEmail(@RequestBody RegisterDto registerDto) {
        return ResponseEntity.ok(authService.isValidEmail(registerDto));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ResetPassword resetPassword) {
        return ResponseEntity.ok(authService.forgotPassword(resetPassword.getEmail()));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPassword resetPassword) {
        return ResponseEntity.ok(authService.resetPassword(resetPassword));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassword changePassword) {
        return ResponseEntity.ok(authService.changePassword(changePassword));
    }

}
