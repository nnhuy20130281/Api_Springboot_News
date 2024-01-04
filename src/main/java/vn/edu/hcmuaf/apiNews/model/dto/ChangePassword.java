package vn.edu.hcmuaf.apiNews.model.dto;

import lombok.*;

@Data
@NonNull
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePassword {
    private String email;
    private String password;
    private String newPassword;
}
