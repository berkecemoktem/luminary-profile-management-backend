package com.luminary.profilemanagement.rest;

import com.luminary.profilemanagement.model.dto.StudentSummaryDto;
import com.luminary.profilemanagement.service.StudentSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student-summary")
public class StudentSummaryController {

    @Autowired
    private StudentSummaryService studentSummaryService;

    @GetMapping("/{userId}")
    public StudentSummaryDto getStudentSummary(@PathVariable Long userId) {
        return studentSummaryService.getStudentSummary(userId);
    }

    @PutMapping("/{userId}/goal")
    public void updateStudentGoal(@PathVariable Long userId, @RequestBody String goal) {
        studentSummaryService.updateStudentGoal(userId, goal);
    }
}
