package com.example.assignment1.entity;

import lombok.Data;
import java.time.LocalDateTime;
@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private LocalDateTime accountCreated;
    private LocalDateTime accountUpdated;

    public static UserDto getUserDto(UserInfo user) {
        UserDto dto=new UserDto();
        dto.setId(user.getId());
        dto.setAccountCreated(user.getAccountCreated());
        dto.setAccountUpdated(user.getAccountUpdated());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUsername(user.getUsername());
        return dto;
    }
}
