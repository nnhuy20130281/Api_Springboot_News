package vn.edu.hcmuaf.apiNews.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;
    private String fullName;
    private String email;
    private boolean status;
    private boolean isAdmin;
}
