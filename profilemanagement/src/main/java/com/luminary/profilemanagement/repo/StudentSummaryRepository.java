package com.luminary.profilemanagement.repo;

import com.luminary.profilemanagement.model.StudentSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentSummaryRepository extends JpaRepository<StudentSummary, Long> {
    StudentSummary findByUserId(Long userId);
}
