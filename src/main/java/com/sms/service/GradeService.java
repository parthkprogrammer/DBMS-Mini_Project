package com.sms.service;

import com.sms.dao.GradeDAO;
import com.sms.dao.impl.GradeDAOImpl;
import com.sms.model.Grade;

import java.time.LocalDate;
import java.util.Optional;

public class GradeService {
    private final GradeDAO gradeDAO;

    public GradeService() {
        this.gradeDAO = new GradeDAOImpl();
    }

    public void assignGrade(int enrollmentId, double gradeValue, Integer attendance, String remarks) {
        Optional<Grade> existingOpt = gradeDAO.findByEnrollmentId(enrollmentId);
        Grade grade;
        if (existingOpt.isPresent()) {
            grade = existingOpt.get();
        } else {
            grade = new Grade();
            grade.setEnrollmentId(enrollmentId);
        }
        
        grade.setGradeValue(gradeValue);
        grade.setGradeLetter(calculateGradeLetter(gradeValue));
        grade.setAttendance(attendance);
        grade.setRemarks(remarks);
        grade.setGradedDate(LocalDate.now());
        
        if (grade.getId() > 0) {
            gradeDAO.update(grade);
        } else {
            gradeDAO.save(grade);
        }
    }

    public Optional<Grade> getGradeForEnrollment(int enrollmentId) {
        return gradeDAO.findByEnrollmentId(enrollmentId);
    }

    private String calculateGradeLetter(double gradeValue) {
        if (gradeValue >= 90) return "A";
        if (gradeValue >= 80) return "B";
        if (gradeValue >= 70) return "C";
        if (gradeValue >= 60) return "D";
        return "F";
    }
}
