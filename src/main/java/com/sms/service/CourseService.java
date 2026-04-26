package com.sms.service;

import com.sms.dao.CourseDAO;
import com.sms.dao.EnrollmentDAO;
import com.sms.dao.impl.CourseDAOImpl;
import com.sms.dao.impl.EnrollmentDAOImpl;
import com.sms.model.Course;
import com.sms.model.Enrollment;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CourseService {
    private final CourseDAO courseDAO;
    private final EnrollmentDAO enrollmentDAO;

    public CourseService() {
        this.courseDAO = new CourseDAOImpl();
        this.enrollmentDAO = new EnrollmentDAOImpl();
    }

    public void createCourse(Course course) throws Exception {
        if (courseDAO.findByCourseCode(course.getCourseCode()).isPresent()) {
            throw new Exception("Course code already exists.");
        }
        courseDAO.save(course);
    }

    public List<Course> getAllCourses() {
        return courseDAO.findAll();
    }

    public List<Course> getCoursesByTeacher(int teacherId) {
        return courseDAO.findByTeacherId(teacherId);
    }

    public void enrollStudent(int studentId, int courseId) throws Exception {
        if (enrollmentDAO.findByStudentAndCourse(studentId, courseId).isPresent()) {
            throw new Exception("Student is already enrolled in this course.");
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(studentId);
        enrollment.setCourseId(courseId);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollmentDAO.save(enrollment);
    }

    public List<Enrollment> getStudentEnrollments(int studentId) {
        return enrollmentDAO.findByStudentId(studentId);
    }
    
    public List<Enrollment> getCourseEnrollments(int courseId) {
        return enrollmentDAO.findByCourseId(courseId);
    }
}
