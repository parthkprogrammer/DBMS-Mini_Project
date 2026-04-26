package com.sms.model;

public class Course {
    private int id;
    private String courseCode;
    private String courseName;
    private int teacherId;
    private int credits;

    public Course() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
}
