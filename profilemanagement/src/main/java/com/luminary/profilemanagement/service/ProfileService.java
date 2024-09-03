package com.luminary.profilemanagement.service;

import com.luminary.profilemanagement.model.Profile;
import com.luminary.profilemanagement.repo.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public Profile getProfileByUserId(Long userId) {
        return profileRepository.findByUserId(userId);
    }

    public Profile updateProfile(Long userId, Profile newProfile) {
        Profile existingProfile = profileRepository.findByUserId(userId);
        if (existingProfile != null) {
            existingProfile.setFullName(newProfile.getFullName());
            existingProfile.setLanguage(newProfile.getLanguage());
            existingProfile.setDarkMode(newProfile.isDarkMode());
            existingProfile.setHighSchool(newProfile.getHighSchool());
            return profileRepository.save(existingProfile);
        } else {
            throw new RuntimeException("Profile not found");
        }
    }

    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }
}
