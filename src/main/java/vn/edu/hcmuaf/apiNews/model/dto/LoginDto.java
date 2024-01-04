package vn.edu.hcmuaf.apiNews.model.dto;

import lombok.*;

@Data
@NonNull
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {
    private long id;
    private String email;
    private String password;
    private String fullName;
    private boolean isAdmin;
}
