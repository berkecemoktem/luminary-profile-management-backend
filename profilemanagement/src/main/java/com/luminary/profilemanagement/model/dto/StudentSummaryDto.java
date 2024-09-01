package com.luminary.profilemanagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentSummaryDto {
    private String fullName;
    private String profilePictureUrl;
    private String highSchool;
    private String mostChallengedCourse;
    private int totalQuestionsUploaded;
    private double averageScore;
    private int successScore;
    private String badges;
    private String goal;
}