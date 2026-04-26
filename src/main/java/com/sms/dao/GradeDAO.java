package com.sms.dao;

import com.sms.model.Grade;
import java.util.Optional;

public interface GradeDAO extends GenericDAO<Grade, Integer> {
    Optional<Grade> findByEnrollmentId(int enrollmentId);
}
