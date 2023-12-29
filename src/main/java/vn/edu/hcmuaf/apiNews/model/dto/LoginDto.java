package vn.edu.hcmuaf.apiNews.model.dto;

import lombok.*;

@Data
@NonNull
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {
    private String email;
    private String password;
}
