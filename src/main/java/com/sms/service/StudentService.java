package com.sms.service;

import com.sms.dao.StudentDAO;
import com.sms.dao.impl.StudentDAOImpl;
import com.sms.model.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class StudentService {
    private final StudentDAO studentDAO;

    public StudentService() {
        this.studentDAO = new StudentDAOImpl();
    }

    public void registerStudent(Student student) throws Exception {
        if (student.getEmail() != null && !student.getEmail().isEmpty()) {
            if (studentDAO.findByEmail(student.getEmail()).isPresent()) {
                throw new Exception("Email already in use.");
            }
        }
        if (student.getEnrollmentDate() == null) {
            student.setEnrollmentDate(LocalDate.now());
        }
        studentDAO.save(student);
    }

    public void updateStudent(Student student) {
        studentDAO.update(student);
    }

    public void deleteStudent(int studentId) {
        studentDAO.delete(studentId);
    }

    public List<Student> getAllStudents() {
        return studentDAO.findAll();
    }

    public Optional<Student> getStudentById(int id) {
        return studentDAO.findById(id);
    }

    public Optional<Student> getStudentByUserId(int userId) {
        return studentDAO.findByUserId(userId);
    }
}
