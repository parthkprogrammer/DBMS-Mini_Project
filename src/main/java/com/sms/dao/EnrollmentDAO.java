package com.sms.dao;

import com.sms.model.Enrollment;
import java.util.List;
import java.util.Optional;

public interface EnrollmentDAO extends GenericDAO<Enrollment, Integer> {
    List<Enrollment> findByStudentId(int studentId);
    List<Enrollment> findByCourseId(int courseId);
    Optional<Enrollment> findByStudentAndCourse(int studentId, int courseId);
}
