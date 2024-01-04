package vn.edu.hcmuaf.apiNews.service;

import org.springframework.http.ResponseEntity;
import vn.edu.hcmuaf.apiNews.model.dto.*;

public interface AuthService {
    ResponseEntity<?> login(LoginDto loginDto);

    String changePassword(String username, String password);

    ResponseEntity<?> registers (RegisterDto registerDto);

    ResponseEntity<?> isValidEmail(RegisterDto registerDto);

    ResponseEntity<?> adminRegister(UpdateUser updateUser);


    ResponseEntity<?> forgotPassword(String email);

    ResponseEntity<?> resetPassword(ResetPassword resetPassword);

    ResponseEntity<?> changePassword(ChangePassword changePassword);
}
