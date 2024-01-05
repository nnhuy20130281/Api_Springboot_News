package vn.edu.hcmuaf.apiNews.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.apiNews.entity.User;
import vn.edu.hcmuaf.apiNews.model.dto.*;
import vn.edu.hcmuaf.apiNews.model.mapper.UserMapper;
import vn.edu.hcmuaf.apiNews.repository.UserRepository;
import vn.edu.hcmuaf.apiNews.service.AuthService;
import vn.edu.hcmuaf.apiNews.service.EmailService;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private Map<String, String> otpMap = new HashMap<>();


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
    public ResponseEntity<?> registers(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }
        String otp = generateOTP();
        otpMap.put(registerDto.getEmail(), otp);
        System.out.println(otpMap);

        scheduleOTPCleanup(registerDto.getEmail(), 3);
        System.out.println(otpMap);

        emailService.sendResetPasswordEmail(registerDto.getEmail(), otp, "OTP for registration");

        return new ResponseEntity<>("OTP sent to email", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> isValidEmail(RegisterDto registerDto) {
        if (otpMap.isEmpty() ||!isOTPValid(registerDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP has expired.");
        }

        if ( !otpMap.get(registerDto.getEmail()).equals(registerDto.getOtp())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP.");
        }


        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setFullName(registerDto.getFullName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setStatus(true);
        user.setAdmin(false);
        userRepository.save(user);


        otpMap.remove(registerDto.getEmail());
        return ResponseEntity.ok("Registration successful.");
    }


    @Override
    public ResponseEntity<?> adminRegister(UpdateUser updateUser) {
        if (userRepository.existsByEmail(updateUser.getEmail())) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        } else {
            User user = new User();
            user.setEmail(updateUser.getEmail());
            user.setFullName(updateUser.getFullName());
            user.setPassword(passwordEncoder.encode(updateUser.getPassword()));
            user.setStatus(updateUser.isStatus());
            user.setAdmin(updateUser.isAdmin());
            userRepository.save(user);
            return new ResponseEntity<>(UserMapper.toUserDto(userRepository.findByEmail(updateUser.getEmail())), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> forgotPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>("Email not found", HttpStatus.NOT_FOUND);
        }

        String otp = generateOTP();
        otpMap.put(email, otp);
        System.out.println(otpMap);

        scheduleOTPCleanup(email, 5);
        System.out.println(otpMap);

        emailService.sendResetPasswordEmail(email, otp, "OTP for password reset");

        return new ResponseEntity<>("OTP sent to email", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> resetPassword(ResetPassword resetPassword) {
        if (otpMap.isEmpty() || !isOTPValid(resetPassword.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP has expired.");
        }

        if (!otpMap.get(resetPassword.getEmail()).equals(resetPassword.getOtp())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP.");
        }


        User user = userRepository.findByEmail(resetPassword.getEmail());
        if (user != null) {
            user.setPassword(passwordEncoder.encode(resetPassword.getPassword()));
            userRepository.save(user);
        }

        otpMap.remove(resetPassword.getEmail());
        return ResponseEntity.ok("Password reset successfully.");
    }

    @Override
    public ResponseEntity<?> changePassword(ChangePassword changePassword) {
        User user = userRepository.findByEmail(changePassword.getEmail());
        if (user == null) {
            return new ResponseEntity<>("Email not found", HttpStatus.NOT_FOUND);
        }
        if (passwordEncoder.matches(changePassword.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
            userRepository.save(user);
            return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Old password incorrect", HttpStatus.BAD_REQUEST);
        }
    }


    private String generateOTP() {
        return String.format("%06d", new Random().nextInt(10000));
    }

    private void scheduleOTPCleanup(String email, int minutes) {
        new Thread(() -> {
            try {
                TimeUnit.MINUTES.sleep(minutes);
                otpMap.remove(email);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private boolean isOTPValid(String email) {
        return otpMap.containsKey(email);
    }

}
