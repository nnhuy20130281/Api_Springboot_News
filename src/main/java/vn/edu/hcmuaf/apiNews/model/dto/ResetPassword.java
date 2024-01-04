package vn.edu.hcmuaf.apiNews.model.dto;

import lombok.*;

@Data
@NonNull
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPassword {
    private String email;
    private String otp;
    private String password;
}
