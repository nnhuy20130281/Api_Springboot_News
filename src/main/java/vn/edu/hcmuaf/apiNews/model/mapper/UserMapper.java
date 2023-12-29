package vn.edu.hcmuaf.apiNews.model.mapper;


import vn.edu.hcmuaf.apiNews.entity.User;
import vn.edu.hcmuaf.apiNews.model.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserDto toUserDto(User user){
        UserDto tmp = new UserDto();
        tmp.setId(user.getId());
        tmp.setFullName(user.getFullName());
        tmp.setEmail(user.getEmail());
        tmp.setStatus(user.isStatus());
        tmp.setAdmin(user.isAdmin());

        return tmp;
    }

    public static List<UserDto> toUserDto(List<User> users){
        return users.stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }
}
