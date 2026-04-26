package com.sms.dao;

import com.sms.model.Course;
import java.util.List;
import java.util.Optional;

public interface CourseDAO extends GenericDAO<Course, Integer> {
    Optional<Course> findByCourseCode(String courseCode);
    List<Course> findByTeacherId(int teacherId);
}
