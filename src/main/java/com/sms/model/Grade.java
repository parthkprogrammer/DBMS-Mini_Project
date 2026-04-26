package com.sms.model;

import java.time.LocalDate;

public class Grade {
    private int id;
    private int enrollmentId;
    private Double gradeValue;
    private String gradeLetter;
    private Integer attendance;
    private String remarks;
    private LocalDate gradedDate;

    public Grade() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(int enrollmentId) { this.enrollmentId = enrollmentId; }

    public Double getGradeValue() { return gradeValue; }
    public void setGradeValue(Double gradeValue) { this.gradeValue = gradeValue; }

    public String getGradeLetter() { return gradeLetter; }
    public void setGradeLetter(String gradeLetter) { this.gradeLetter = gradeLetter; }

    public Integer getAttendance() { return attendance; }
    public void setAttendance(Integer attendance) { this.attendance = attendance; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public LocalDate getGradedDate() { return gradedDate; }
    public void setGradedDate(LocalDate gradedDate) { this.gradedDate = gradedDate; }
}
