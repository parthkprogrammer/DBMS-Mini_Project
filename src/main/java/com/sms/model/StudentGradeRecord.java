package com.sms.model;

public class StudentGradeRecord {
    private int enrollmentId;
    private String studentName;
    private String courseCode;
    private String courseName;
    private Double gradeValue;
    private String gradeLetter;
    private Integer attendance;

    public StudentGradeRecord(int enrollmentId, String studentName, String courseCode, String courseName, Double gradeValue, String gradeLetter, Integer attendance) {
        this.enrollmentId = enrollmentId;
        this.studentName = studentName;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.gradeValue = gradeValue;
        this.gradeLetter = gradeLetter;
        this.attendance = attendance;
    }

    public int getEnrollmentId() { return enrollmentId; }
    public String getStudentName() { return studentName; }
    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public Double getGradeValue() { return gradeValue; }
    public String getGradeLetter() { return gradeLetter; }
    public Integer getAttendance() { return attendance; }
}
