package com.luminary.profilemanagement.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RequestPasswordResetDto {
    @Email
    @NotEmpty
    private String email;
}
