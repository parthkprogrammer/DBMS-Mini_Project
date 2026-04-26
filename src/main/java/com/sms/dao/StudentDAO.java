package com.sms.dao;

import com.sms.model.Student;
import java.util.Optional;

public interface StudentDAO extends GenericDAO<Student, Integer> {
    Optional<Student> findByUserId(int userId);
    Optional<Student> findByEmail(String email);
}
