package com.luminary.profilemanagement.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDto {

    private String fullName;
    private String language; // e.g., 'en' for English, 'tr' for Turkish
    private boolean darkMode;
    private String highSchool;
}
