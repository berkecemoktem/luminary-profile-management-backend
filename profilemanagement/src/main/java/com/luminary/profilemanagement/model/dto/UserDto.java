package com.luminary.profilemanagement.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private String username;
    private String email;
    private String password; // Optional, depending on use case

}
