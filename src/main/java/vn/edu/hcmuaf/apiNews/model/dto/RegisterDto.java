package vn.edu.hcmuaf.apiNews.model.dto;


import lombok.*;

@Data
@NonNull
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterDto {
    private String fullName;
    private String password;
    private String email;
}
