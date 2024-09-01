package com.luminary.profilemanagement.rest;

import com.luminary.profilemanagement.model.Profile;
import com.luminary.profilemanagement.model.dto.ProfileDto;
import com.luminary.profilemanagement.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/{userId}")
    public ResponseEntity<Profile> getProfileByUserId(@PathVariable Long userId) {
        Profile profile = profileService.getProfileByUserId(userId);
        if (profile != null) {
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Long userId, @RequestBody ProfileDto profileDto) {
        Profile profile = new Profile();
        profile.setFullName(profileDto.getFullName());
        profile.setLanguage(profileDto.getLanguage());
        profile.setDarkMode(profileDto.isDarkMode());
        profile.setHighSchool(profileDto.getHighSchool());

        try {
            Profile updatedProfile = profileService.updateProfile(userId, profile);
            return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
