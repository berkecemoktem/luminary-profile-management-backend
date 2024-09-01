package com.luminary.profilemanagement.service;

import com.luminary.profilemanagement.model.StudentSummary;
import com.luminary.profilemanagement.model.dto.StudentSummaryDto;
import com.luminary.profilemanagement.repo.StudentSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentSummaryService {

    @Autowired
    private StudentSummaryRepository studentSummaryRepository;

    // Method to get student summary by user ID and return DTO
    public StudentSummaryDto getStudentSummary(Long userId) {
        StudentSummary studentSummary = studentSummaryRepository.findByUserId(userId);
        if (studentSummary == null) {
            throw new RuntimeException("StudentSummary not found for user ID: " + userId);
        }
        return new StudentSummaryDto(
                studentSummary.getFullName(),
                studentSummary.getProfilePictureUrl(),
                studentSummary.getHighSchool(),
                studentSummary.getMostChallengedCourse(),
                studentSummary.getTotalQuestionsUploaded(),
                studentSummary.getAverageScore(),
                studentSummary.getSuccessScore(),
                studentSummary.getBadges(),
                studentSummary.getGoal()
        );
    }

    // Method to update student goal
    public void updateStudentGoal(Long userId, String newGoal) {
        StudentSummary studentSummary = studentSummaryRepository.findByUserId(userId);
        if (studentSummary != null) {
            studentSummary.setGoal(newGoal);
            studentSummaryRepository.save(studentSummary);
        } else {
            throw new RuntimeException("StudentSummary not found for user ID: " + userId);
        }
    }
}
