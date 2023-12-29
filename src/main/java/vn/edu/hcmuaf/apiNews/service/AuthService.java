package vn.edu.hcmuaf.apiNews.service;

import org.springframework.http.ResponseEntity;
import vn.edu.hcmuaf.apiNews.model.dto.LoginDto;
import vn.edu.hcmuaf.apiNews.model.dto.RegisterDto;

public interface AuthService {
    ResponseEntity<?> login(LoginDto loginDto);

    String changePassword(String username, String password);

    ResponseEntity<?> registers (RegisterDto registerDto);
}
