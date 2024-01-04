package vn.edu.hcmuaf.apiNews.service.impl;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.apiNews.entity.User;
import vn.edu.hcmuaf.apiNews.model.dto.LoginDto;
import vn.edu.hcmuaf.apiNews.model.dto.RegisterDto;
import vn.edu.hcmuaf.apiNews.model.mapper.UserMapper;
import vn.edu.hcmuaf.apiNews.repository.UserRepository;
import vn.edu.hcmuaf.apiNews.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> login(LoginDto loginDto) {
        if (userRepository.existsByEmail(loginDto.getEmail())) {
            User user = userRepository.findByEmail(loginDto.getEmail());
            if (user.isStatus()) {

                LoginDto response = new LoginDto();
                response.setId(user.getId());
                response.setEmail(user.getEmail());
                response.setFullName(user.getFullName());
                response.setAdmin(user.isAdmin());
                response.setPassword(loginDto.getPassword());
                if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Password incorrect", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Account is locked", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Email not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public String changePassword(String username, String password) {
        return null;
    }

    @Override
    public ResponseEntity<?> registers(RegisterDto registerDto) {
        String result = "";
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        } else {
            User user = new User();
            user.setEmail(registerDto.getEmail());
            user.setFullName(registerDto.getFullName());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            user.setStatus(true);
            user.setAdmin(false);
            userRepository.save(user);
            return new ResponseEntity<>(UserMapper.toUserDto(userRepository.findByEmail(registerDto.getEmail())), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> logout() {
        // Xóa thông tin xác thực khỏi SecurityContext
        SecurityContextHolder.clearContext();
        String result = SecurityContextHolder.getContext().toString();


        // Trả về một phản hồi cho người dùng
        return ResponseEntity.ok("Logged out successfully - " + result);
    }
}
