package com.luminary.profilemanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student_summaries")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String fullName;
    private String profilePictureUrl;
    private String highSchool;
    private String mostChallengedCourse;
    private int totalQuestionsUploaded;
    private double averageScore; // can be de-scoped later
    private int successScore;
    private String badges;
    private String goal;
}
