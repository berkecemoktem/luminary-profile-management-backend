package com.luminary.profilemanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginMessage {
    private String message;
    private boolean success;
    private User user;
}
